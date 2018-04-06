package com.evozon.training.rest;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RestPersistence {

    @Autowired
    private SessionFactory sessionFactory;

    public List<Review> getReviews(Integer productId) {
        List<Review> reviews;
        Session session = sessionFactory.openSession();
        reviews = session.createQuery("from Review where productId = (:id)").setParameter("id", productId).list();
        session.close();

        return reviews;
    }


    public void addReview(Review review) {
        Session session = sessionFactory.openSession();
        session.save(review);
        session.close();
    }

    public void updateReview(Review review) {
        Session session = sessionFactory.openSession();
        session.saveOrUpdate(review);
        session.flush();
        session.close();
    }

    public void deleteReview(Integer reviewId) {
        Session session = sessionFactory.openSession();
        Review review = new Review();
        review.setId(reviewId);
        session.createQuery("delete from Review where id =(:id)").setParameter("id", reviewId).executeUpdate();
        session.flush();
        session.close();
    }
}
