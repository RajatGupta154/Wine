package com.evozon.training.controller;

import com.evozon.training.model.Category;
import com.evozon.training.model.CheckBox;
import com.evozon.training.model.FilterOptions;
import com.evozon.training.model.Product;
import com.evozon.training.rest.RestService;
import com.evozon.training.rest.Review;
import com.evozon.training.service.CategoriesService;
import com.evozon.training.service.FilterService;
import com.evozon.training.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ClientController extends UserController {
    @Autowired
    ProductService productService;
    @Autowired
    CategoriesService categoriesService;
    @Autowired
    FilterService filterService;
    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    public String displayProductDetails(Model model, @PathVariable Integer id) {
        Product wine = productService.getProduct(id);
        AbstractMap.SimpleEntry<Double ,List<Review>> reviewsPair  = productService.getReviews(id);
        model.addAttribute("reviewsList", reviewsPair.getValue());
        model.addAttribute("average", reviewsPair.getKey());
        model.addAttribute("wine", wine);

        return "product-details";
    }

    /// ADD REQUEST PARAM AND CHECK IF THEY ARE DEFAULT => SET ALL CHECKBOXES UNCHECKED // IF NOT => UPDATE THE NEW CHECKBOXES
    /*@RequestMapping(value = "/shop/{page}", method = RequestMethod.GET)
    public String cViewProducts(HttpServletRequest request, Model model, @PathVariable Integer page,
                                @RequestParam(value = "argSort", required = false, defaultValue = "idasc") String argSort) {
        List<CheckBox> categories = new ArrayList<CheckBox>();
        List<CheckBox> years = new ArrayList<CheckBox>();
        List<CheckBox> types = new ArrayList<CheckBox>();

        for (Category c : filterService.getCategories())
            categories.add(new CheckBox(c.getName(), false));

        for (String year : filterService.getYears())
            years.add(new CheckBox(year, false));

        for (String type : filterService.getTypes())
            types.add(new CheckBox(type, false));

        Integer startIndex = (page - 1) * 9;
        List<Product> productList = new ArrayList<Product>();
        productList = productService.sortProducts(argSort, startIndex, 10);  //get with +1 to see if  there will be a next page

        boolean nextArrow = false;
        boolean noPageNb = false;
        if (productList.size() > 9) {
            nextArrow = true;
            productList.remove(9);
        }
        if (page == 1 & productList.size() < 9) {
            logger.info("size=" + productList.size());
            noPageNb = true;
        }

        model.addAttribute("noPageNb", noPageNb);
        model.addAttribute("nextArrow", nextArrow);
        model.addAttribute("argSort", argSort);
        model.addAttribute("currentPage", page);
        model.addAttribute("wineList", productList);
        model.addAttribute("categories", categories);
        model.addAttribute("years", years);
        model.addAttribute("types", types);
        model.addAttribute("number", productList.size());

        return "shop";
    }*/

    @RequestMapping(value = "/shop/{page}", method = RequestMethod.GET)
    public String cViewProducts(HttpServletRequest request, Model model, @PathVariable Integer page,
                                @RequestParam(value = "argSort", required = false, defaultValue = "idasc") String argSort,
                                @RequestParam(value = "category", required = false, defaultValue = "null") String category,
                                @RequestParam(value = "year", required = false, defaultValue = "0") String year,
                                @RequestParam(value = "type", required = false, defaultValue = "null") String type) {

        List<Product> productList=new ArrayList<Product>();
        Integer startIndex = (page - 1) * 9;

        List<CheckBox> categories = new ArrayList<CheckBox>();
        List<CheckBox> years = new ArrayList<CheckBox>();
        List<CheckBox> types = new ArrayList<CheckBox>();

        Map<String, Integer> numberOfProductsPerType;
        Map<String, Integer> numberOfProductsPerYear;
        Map<String, Integer> numberOfProductsPerCategory;

        if (category.equals("null") & year.equals("0") & type.equals("null")){   // put the checkboxes cleared

            for (Category c : filterService.getCategories())
                categories.add(new CheckBox(c.getName(), false));

            for (String y : filterService.getYears())
                years.add(new CheckBox(y, false));

            for (String t : filterService.getTypes())
                types.add(new CheckBox(t, false));

            productList= productService.sortProducts(argSort, startIndex, 10);  //get with +1 to see if  there will be a next page
        }

        else {  //any checkbox was checked
            FilterOptions filterOptions=new FilterOptions();
            if (!category.equals("none"))
                filterOptions.setCategory(category);
            if (!year.equals("none"))
                filterOptions.setYear(year);
            if (!type.equals("none"))
                filterOptions.setType(type);

            productList = filterService.applyFilters(filterOptions, startIndex, 10, argSort);
            List<Category> allCategories = categoriesService.getAllTheCategories();

            categories = filterService.getCategoriesFilteredProducts(productList, filterOptions, allCategories);
            years = filterService.getYearsFilteredProducts(productList, filterOptions);
            types = filterService.getTypesFilteredProducts(productList, filterOptions);

            numberOfProductsPerType = filterService.getNumberOfProductsPerType(productList);
            numberOfProductsPerYear = filterService.getNumberOfProductsPerYear(productList);
            numberOfProductsPerCategory = filterService.getNumberOfProductsPerCategory(productList, allCategories);

            model.addAttribute("numberOfProductsPerYear", numberOfProductsPerYear);
            model.addAttribute("numberOfProductsPerType", numberOfProductsPerType);
            model.addAttribute("numberOfProductsPerCategory", numberOfProductsPerCategory);
        }



        boolean nextArrow = false;
        boolean noPageNb = false;
        if (productList.size() > 9) {
            nextArrow = true;
            productList.remove(9);
        }
        if (page == 1 & productList.size() < 9) {
            logger.info("size=" + productList.size());
            noPageNb = true;
        }

        model.addAttribute("category", category);
        model.addAttribute("type", type);
        model.addAttribute("year", year);

        model.addAttribute("noPageNb", noPageNb);
        model.addAttribute("nextArrow", nextArrow);
        model.addAttribute("argSort", argSort);
        model.addAttribute("currentPage", page);
        model.addAttribute("wineList", productList);
        model.addAttribute("categories", categories);
        model.addAttribute("years", years);
        model.addAttribute("types", types);
        model.addAttribute("number", productList.size());

        return "shop";
    }

//    @RequestMapping(value = "/shop/{page}", method = RequestMethod.POST)
//    public String cViewProductsPost(Model model, @PathVariable Integer page, @ModelAttribute FilterOptions filterOptions) {
//
//        Integer startIndex = (page - 1) * 9;
//        List<Product> filteredProducts = filterService.applyFilters(filterOptions, startIndex, 9);
//
//        List<Category> allCategories = categoriesService.getAllTheCategories();
//
//        List<CheckBox> categories = filterService.getCategoriesFilteredProducts(filteredProducts, filterOptions, allCategories);
//        List<CheckBox> years = filterService.getYearsFilteredProducts(filteredProducts, filterOptions);
//        List<CheckBox> types = filterService.getTypesFilteredProducts(filteredProducts, filterOptions);
//
//        Map<String, Integer> numberOfProductsPerType = filterService.getNumberOfProductsPerType(filteredProducts);
//        Map<String, Integer> numberOfProductsPerYear = filterService.getNumberOfProductsPerYear(filteredProducts);
//        Map<String, Integer> numberOfProductsPerCategory = filterService.getNumberOfProductsPerCategory(filteredProducts, allCategories);
//
//        Integer numberOfProducts = filteredProducts.size();
//
//
//        model.addAttribute("currentPage", page);
//        model.addAttribute("wineList", filteredProducts);
//        model.addAttribute("categories", categories);
//        model.addAttribute("years", years);
//        model.addAttribute("types", types);
//        model.addAttribute("number", numberOfProducts);
//        model.addAttribute("numberOfProductsPerYear", numberOfProductsPerYear);
//        model.addAttribute("numberOfProductsPerType", numberOfProductsPerType);
//        model.addAttribute("numberOfProductsPerCategory", numberOfProductsPerCategory);
//        model.addAttribute("filterOptions", filterOptions);
//
//        return "shop";
//    }

}
