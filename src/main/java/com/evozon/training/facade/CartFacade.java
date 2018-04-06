package com.evozon.training.facade;


import com.evozon.training.model.Cart;
import com.evozon.training.model.CartDTO;
import com.evozon.training.model.CartEntry;
import com.evozon.training.service.CartService;
import com.evozon.training.service.convertors.DTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class CartFacade {

    @Autowired
    CartService cartService;

    @Autowired
    DTOConverter converter;

    public void addToCart(Integer cartId, Integer productId, Integer quantitiy){
        cartService.addToCart(cartId, productId, quantitiy);
    }

    public Cart getCart(int cartId){
        return cartService.getCart(cartId);
    }

    public void updateTotalPrice(Cart cart){
        cartService.updateTotalPrice(cart);
    }

    public Integer getNrProductsFromCart(Integer cartId){
        return cartService.getNrProductsFromCart(cartId);
    }

    @Transactional
    public CartDTO getCartById(Integer idCart){
        Cart cart = cartService.getCartById(idCart);
        cartService.updateTotalPrice(cart);
        cartService.update(cart);
        CartDTO cartDTO = converter.convertCartToCartDTO(cart);
        return cartDTO;
    }

    public void setAmount(Integer amount, Integer entryId){
        cartService.setAmount(amount, entryId);
    }

    public void deleteEntry(Integer id){
        cartService.deleteEntry(id);
    }
}
