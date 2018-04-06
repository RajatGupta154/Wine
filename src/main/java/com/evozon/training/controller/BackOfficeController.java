package com.evozon.training.controller;

import com.evozon.training.model.Category;
import com.evozon.training.model.Product;
import com.evozon.training.model.ProductDTO;
import com.evozon.training.model.users.UserDto;
import com.evozon.training.model.users.UserFormValidator;
import com.evozon.training.service.CategoriesService;
import com.evozon.training.service.FileSystemStorageService;
import com.evozon.training.service.ProductService;
import com.evozon.training.service.UserService;
import com.evozon.training.service.convertors.DTOConverter;
import com.evozon.training.service.validators.ProductValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Controller
public class BackOfficeController extends UserController {

    private static final Logger logger = LoggerFactory.getLogger(BackOfficeController.class);
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoriesService categoriesService;
    @Autowired
    private FileSystemStorageService fileStorage;
    @Autowired
    private ProductValidator productValidator;
    @Autowired
    private DTOConverter converter;
    @Autowired
    private UserFormValidator userFormValidator;

    @InitBinder(value = "productDTO")
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(productValidator);
    }


    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(Locale locale, Model model) {
        logger.info("Home page. The client locale is {}", locale);
        String authenticatedUserName = userService.getAuthenticatedUser().getName();
        model.addAttribute("userName", authenticatedUserName);
        return "home";
    }

    @RequestMapping(value = "/addProduct", method = RequestMethod.GET)
    public String showAddView(Locale locale, Model model) {
        List<Category> categories = categoriesService.getAllTheCategories();
        model.addAttribute("categories", categories);
        logger.info("Add product. The client locale is {}", locale);
        model.addAttribute("productDTO", new ProductDTO());

        String authenticatedUserName = userService.getAuthenticatedUser().getName();
        model.addAttribute("userName", authenticatedUserName);

        return "add-product";
    }

    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    public String addProduct(Locale locale, Model model,
                             @ModelAttribute("productDTO") @Validated ProductDTO productDTO,
                             BindingResult bindingResult,
                             @RequestParam String category,
                             @RequestParam(value = "picture", required = false) MultipartFile file
    ) {
        if (bindingResult.hasErrors()) {
            logger.info("Input validation failed!");
            List<Category> categories = categoriesService.getAllTheCategories();
            model.addAttribute("categories", categories);

            String authenticatedUserName = userService.getAuthenticatedUser().getName();
            model.addAttribute("userName", authenticatedUserName);

            return "add-product";
        } else {
            List<Category> categories = categoriesService.getAllTheCategories();
            for (Category c : categories) {
                if (c.getName().equals(category))
                    productDTO.setCategoryID(c.getID());
            }
            Product product = converter.convertDTOToProduct(productDTO);
            boolean wasAdded = true;
            try {
                String fileName = fileStorage.store(file);
                product.setPicture(fileName);
            } catch (IOException e) {
                model.addAttribute("pictureException", "The picture wasn't added to db! The default one is assigned");
                wasAdded = false;
            }
            if (!wasAdded) {
                try {
                    String fileName = fileStorage.storeDefault();
                    product.setPicture(fileName);
                } catch (IOException exp) {
                    model.addAttribute("pictureExceptionDefault", "The default picture could not be added also!");
                }
            }
            productService.addProduct(product);
            logger.info("Product added. The client locale is {}", locale);

            return "redirect:wines";
        }
    }

    @RequestMapping(value = "/wines", method = RequestMethod.GET)
    public String getProducts(Locale locale, Model model,HttpServletRequest request) {
        logger.info("List of wines. The client locale is {}", locale);
        model.addAttribute("wineList", productService.getAllProducts());
        String authenticatedUserName = userService.getAuthenticatedUser().getName();

        model.addAttribute("userName", authenticatedUserName);
        return "wines";
    }

    @RequestMapping(value = "/importProducts", method = RequestMethod.POST)
    public String importProducts(@RequestParam("file") MultipartFile file, Model model) {
        int nb = 0;
        try {
            ByteArrayInputStream stream = new ByteArrayInputStream(file.getBytes());
            String myString = org.apache.commons.io.IOUtils.toString(stream, "UTF-8");
            nb = productService.manageImports(myString);
        } catch (IOException e) {
            logger.error("GET BYTES ERROR", e);
        }
        model.addAttribute("nbOfProducts", nb);
        return "import-message";
    }

    @RequestMapping(value = "/wine-edit/{id}", method = RequestMethod.GET)
    public String productEdit(Model model, @PathVariable Integer id) {
        logger.info("You are finally in edit mode!");
        ProductDTO productDTO = converter.convertProductToDTO(productService.getProduct(id));
        model.addAttribute("productDTO", productDTO);

        List<Category>categories = categoriesService.getAllTheCategories();
        model.addAttribute("categories", categories);

        return "wine-edit";
    }

    @RequestMapping(value = "/wine-edit", method = RequestMethod.POST)
    public String productHome(@ModelAttribute("productDTO") @Validated ProductDTO productDTO,
                              BindingResult bindingResult,
                              Model model,
                              @RequestParam(value = "picture", required = false) MultipartFile file) {
        if (bindingResult.hasErrors()) {
            logger.info("Input validation failed!");
            return "wine-edit";
        } else {
            Product product = converter.convertDTOToProduct(productDTO);
            Optional<String> oldProductPicture = Optional.ofNullable(productService.getProductPicture(productDTO.getWineId()));
            if (oldProductPicture.isPresent()) {
                product.setPicture(oldProductPicture.get());
            }
            try {
                String fileName = fileStorage.store(file);
                product.setPicture(fileName);
            } catch (IOException e) {
                model.addAttribute("pictureException", "The picture wasn't added to db!");
            }
            productService.updateProduct(product);
            logger.info("You edit the dates successfully!");
            return "redirect:wines";
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteProduct(@RequestParam("deleteId") String id) {
        logger.info(id);
        productService.deleteProduct(id);
        return "redirect:wines";
    }

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public String categories(Model model){
        HashMap<String, Integer> categoryQuantityMap = categoriesService.getAllTheCategoriesMap();
        model.addAttribute("Categories", categoryQuantityMap);

        String authenticatedUserName = userService.getAuthenticatedUser().getName();
        model.addAttribute("userName", authenticatedUserName);
        return "categories";
    }

    @RequestMapping(value = "/categories", method = RequestMethod.POST)
    public String categories(Model model, @RequestParam String categoryName) {
        HashMap<String, Integer> categoryQuantityMap;
        Category category = new Category();

        category.setName(categoryName);

        if (categoriesService.addCategory(category))
            model.addAttribute("success", Boolean.TRUE);
        else
            model.addAttribute("success", Boolean.FALSE);

        categoryQuantityMap = categoriesService.getAllTheCategoriesMap();
        model.addAttribute("Categories", categoryQuantityMap);

        String authenticatedUserName = userService.getAuthenticatedUser().getName();
        model.addAttribute("userName", authenticatedUserName);
        return "categories";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login-form";
    }


    @RequestMapping(value = "/deleteAll", method = RequestMethod.POST)
    public String deleteAll(@RequestParam(value = "getAll", defaultValue = "null") String id) {
        if (!"null".equals(id)) {
            productService.deleteAll(id);
        }
        return "redirect:wines";
    }

    @RequestMapping(value = "/exportAllSuccess", method = RequestMethod.GET)
    @ResponseBody
    public FileSystemResource exportProductsSuccess(HttpServletRequest request) throws URISyntaxException {
        String ids=(String)request.getSession().getAttribute("ids");
        HttpServletResponse response=(HttpServletResponse) request.getSession().getAttribute("response");
        File file=productService.exportProducts(ids);
        response.setContentLength((int)file.getName().length());
        response.setContentType("application/force-download");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
        return new FileSystemResource(file);
    }
    @RequestMapping(value = "/exportAll", method = RequestMethod.POST )
    public String exportProducts(@RequestParam(value = "getAll",defaultValue = "null") String ids, HttpServletRequest request,
                                 HttpServletResponse response ) {
        if (ids.equals("null")){
            return "redirect:wines";
        }
        else{
            request.getSession().setAttribute("ids",ids);
            request.getSession().setAttribute("response",response);
            return "redirect:exportAllSuccess";
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Model model) {
        logger.info("You arrived into the register formular");
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);

        return "registration";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerUserAccount(Model model, @ModelAttribute("user") UserDto userDto, BindingResult bindingResult) {

        logger.info("Now it's time for registration validation");

        userFormValidator.validate(userDto, bindingResult);
        if (!bindingResult.hasErrors()) {
            userService.registerNewUserAccount(userDto);
            return "redirect:wines";
        } else {
            model.addAttribute("user", userDto);
            return "registration";
        }
    }

    @RequestMapping(value="/register/{id}", method = RequestMethod.GET)
    public String userRegister(Model model, @PathVariable Integer id){

        logger.info("You can now confirm your registration");
        userService.activatingUserAccount(id);
        model.addAttribute("success",Boolean.TRUE);

        return "redirect:/login";
    }

}
