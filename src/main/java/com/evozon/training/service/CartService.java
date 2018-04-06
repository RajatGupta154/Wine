package com.evozon.training.service;

import com.evozon.training.model.Cart;
import com.evozon.training.model.CartDTO;
import com.evozon.training.model.CartEntry;
import com.evozon.training.model.Product;
import com.evozon.training.persistence.CartPersistence;
import com.evozon.training.persistence.ProductPersistence;
import com.evozon.training.service.convertors.DTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.smartcardio.CardTerminal;
import java.util.List;
import java.util.Map;

@Service
public class CartService {

    @Autowired
    CartPersistence cartPersistence;
    @Autowired
    private ProductPersistence productPersistence;

    @Transactional
    public void addToCart(Integer cartId,Integer productId,Integer quantity){
        Cart cart=cartPersistence.getCart(cartId);
        Product product=productPersistence.getProduct(productId);
        List<CartEntry> cartEntryList = cart.getListOfEntries();

        int quantityWantToBuy=quantity;
        boolean alreadyInCart=false;
        int indexOfProductInEntryList=0;

        for (CartEntry cartEntry:cartEntryList) {
            if(cartEntry.getProduct().getWineId()==product.getWineId()){
                quantityWantToBuy+=cartEntry.getQuantity();
                alreadyInCart=true;
                indexOfProductInEntryList=cartEntryList.indexOf(cartEntry);
            }
        }
        int stock=0;
        if(product.getStock()!=null)stock=product.getStock();
        if(stock<quantityWantToBuy) {
            quantityWantToBuy=stock;
        }

        CartEntry cartEntry;
        if(alreadyInCart==false) {
           cartEntry = new CartEntry();
           cartEntry.setCart(cart);
           cartEntry.setProduct(product);
           cartEntry.setQuantity(quantityWantToBuy);
           cartEntryList.add(cartEntry);
           cart.setListOfEntries(cartEntryList);
        }
        else{
           cartEntryList.get(indexOfProductInEntryList).setQuantity(quantityWantToBuy);
        }
        updateTotalPrice(cart);
        cartPersistence.updateCart(cart);
    }
    @Transactional
    public Cart getCart(int cartId){
       return cartPersistence.getCart(cartId);
    }

    @Transactional
    public void update(Cart cart){
        cartPersistence.updateCart(cart);
    }

    public void updateTotalPrice(Cart cart){
        Double totalCost=0.0;
        List<CartEntry> cartEntryList=cart.getListOfEntries();
        for (CartEntry cartEntry : cartEntryList){
            totalCost+=cartEntry.getProduct().getPrice()*cartEntry.getQuantity();
        }
        totalCost = Math.round(totalCost * 100.0) / 100.0;
        cart.setTotalCost(totalCost);
    }

    @Transactional
    public Cart getCartById(Integer idCart){
        return cartPersistence.getCart(idCart);
    }

    public int getCartIDFromCookie(HttpServletRequest request){
        Integer cartId=0;
        Cookie cookie[]=request.getCookies();
        for (Cookie cookieIterator:cookie
                ) {
            if(cookieIterator.getName().equals("winesShopCartId")){
                cartId=Integer.parseInt(cookieIterator.getValue());
                break;
            }
        }
       return  cartId;
    }
    @Transactional
    public int createNewCart() {
        Cart newCart = new Cart();
        Integer cartId = cartPersistence.addCart(newCart);
        return cartId;
    }

    @Transactional
    public void setAmount(Integer amount, Integer entryId){
        CartEntry cartEntry = cartPersistence.getCartEntry(entryId);
        Integer stock = cartEntry.getProduct().getStock();
        if(stock==null) stock=0;
        if(stock < amount)
            amount = stock;
        if(amount <= 0)
            cartPersistence.deleteEntry(entryId);
        else
            cartPersistence.setAmount(amount, entryId);
    }
    @Transactional
    public void deleteEntry(Integer id){
        cartPersistence.deleteEntry(id);
    }

    @Transactional
    public Integer getNrProductsFromCart(Integer cartId) {
        List<CartEntry> cartEntries = cartPersistence.getAllEntries(cartId);
        Integer nrProductsFromCart = 0;
        for (CartEntry entry : cartEntries) {
            nrProductsFromCart += entry.getQuantity();
        }

        return nrProductsFromCart;
    }
}
