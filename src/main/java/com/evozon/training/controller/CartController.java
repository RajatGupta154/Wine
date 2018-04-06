package com.evozon.training.controller;

import com.evozon.training.facade.CartFacade;
import com.evozon.training.model.Cart;
import com.evozon.training.model.CartDTO;
import com.evozon.training.service.CartService;
import com.evozon.training.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Controller
public class CartController extends UserController {

    @Autowired
    CartFacade cartFacade;

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @RequestMapping(value = "/addToCart", method = RequestMethod.POST)
    public String addToCart(
            @RequestParam(value="quantity", defaultValue = "1" ) Integer quantity,
            @RequestParam( value="productId") Integer productId,
            HttpSession session){

        logger.info(String.valueOf(productId));
        logger.info(String.valueOf(quantity));

        Integer cartId = (Integer) session.getAttribute(CART_SESSION_ATTRIBUTE_NAME);
        cartFacade.addToCart(cartId, productId, quantity);

        return "redirect:shop/1";
    }

    @RequestMapping(value = "/cart-page", method = RequestMethod.GET)
    public String cartPage(HttpSession session, Model model) {
        Integer idCart = (Integer) session.getAttribute(CART_SESSION_ATTRIBUTE_NAME);
        CartDTO cart = cartFacade.getCartById(idCart);
        model.addAttribute("cart", cart);
        return "cart-page";
    }

    @RequestMapping(params = "updateId", value = "/cart-page/update", method = RequestMethod.POST)
    public String cartPageAmount(@RequestParam("amount") Integer amount, @RequestParam("entryId") Integer entryId){
        cartFacade.setAmount(amount, entryId);
        return  "redirect:/cart-page";
    }

    @RequestMapping(params = "deleteId", value = "/cart-page/delete", method = RequestMethod.POST)
    public String deleteEntry(@RequestParam("deleteId") Integer id){
        cartFacade.deleteEntry(id);
        return "redirect:/cart-page";
    }
}
