package com.evozon.training.controller;

import com.evozon.training.facade.CartFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;

public class UserController {

    protected static final String CART_SESSION_ATTRIBUTE_NAME = "sessionCartId";

    @Autowired
    CartFacade cartFacade;

    @ModelAttribute("nrProductsFromCart")
    public Integer getNrProductsFromCart(HttpSession session) {
        Integer cartId = (Integer) session.getAttribute(CART_SESSION_ATTRIBUTE_NAME);
        Integer nrProductsFromCart = cartFacade.getNrProductsFromCart(cartId);

        return nrProductsFromCart;
    }
}
