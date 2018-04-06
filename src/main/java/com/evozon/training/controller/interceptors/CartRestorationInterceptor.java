package com.evozon.training.controller.interceptors;

import com.evozon.training.service.CartService;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class CartRestorationInterceptor extends HandlerInterceptorAdapter {

    private CartService cartService;


    private static final String CART_COOKIE_NAME = "cartId";
    private static final String CART_SESSION_ATTRIBUTE_NAME = "sessionCartId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Cookie cartCookie = null;

        Cookie cookies[] = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(CART_COOKIE_NAME)) {
                    cartCookie = cookie;
                    break;
                }
            }
        }

        Integer cookieValue = null;
        if (cartCookie != null) {
            cookieValue = Integer.parseInt(cartCookie.getValue());
        }

        HttpSession session = request.getSession();
        Integer sessionCartId = (Integer) session.getAttribute(CART_SESSION_ATTRIBUTE_NAME);

        if(cookieValue == null){
            if (sessionCartId == null) {
                sessionCartId = cartService.createNewCart();
            }
        } else {
            if (sessionCartId == null) {
                sessionCartId = cookieValue;
            }
        }

        session.setAttribute(CART_SESSION_ATTRIBUTE_NAME, sessionCartId);

        cartCookie = new Cookie(CART_COOKIE_NAME, sessionCartId.toString());
        response.addCookie(cartCookie);

        return true;
    }

    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }
}
