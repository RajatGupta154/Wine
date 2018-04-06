package com.evozon.training.persistence;

import com.evozon.training.model.Cart;
import com.evozon.training.model.CartEntry;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class CartPersistence {

    @Autowired
    private SessionFactory sessionFactory;
    private static final Logger logger = LoggerFactory.getLogger(ProductPersistence.class);

    public void persistCart(Cart cart){

    }

    public List<CartEntry> getAllEntries(Integer cartId){
        List<CartEntry> allEntries;
        Session session = sessionFactory.getCurrentSession();
        allEntries = session.createQuery("from CartEntry where cartId=" + cartId).list();
        return  allEntries;
    }

    public Cart getCart(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Cart cart = (Cart) session.get(Cart.class, id);
        return cart;
    }

    public Integer addCart(Cart cart) {
        Session session = sessionFactory.getCurrentSession();
        session.save(cart);
        return cart.getCartId();
    }

    public void updateCart(Cart cart){
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(cart);
    }

    public int lastAddedCartId(){
        List<Cart> allEntries;
        Session session = sessionFactory.getCurrentSession();
        allEntries = session.createQuery("from Cart").list();
        return  allEntries.get(allEntries.size()-1).getCartId();
    }

    public void setAmount(Integer amount, Integer entryId){
        Session session = sessionFactory.getCurrentSession();
        String stringQuery = "UPDATE CartEntry SET quantity = :amount WHERE entryId = :id";
        Query query = session.createQuery(stringQuery);
        query.setParameter("amount", amount);
        query.setParameter("id", entryId);
        query.executeUpdate();
    }

    public void deleteEntry(Integer entryId){
        Session session = sessionFactory.getCurrentSession();
        String stringQuery = "DELETE FROM CartEntry WHERE entryId = :id";
        Query query = session.createQuery(stringQuery);
        query.setParameter("id", entryId);
        query.executeUpdate();
    }

    public CartEntry getCartEntry(Integer entryId){
        Session session = sessionFactory.getCurrentSession();
        CartEntry cartEntry = (CartEntry) session.get(CartEntry.class, entryId);
        return cartEntry;
    }

}
