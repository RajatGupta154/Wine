package com.evozon.training.persistence;

import com.evozon.training.model.users.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserPersistence {
    private static final Logger logger = LoggerFactory.getLogger(UserPersistence.class);
    @Autowired
    private SessionFactory sessionFactory;

    public List<User> getAll() {
        List<User> users;
        Session session = sessionFactory.openSession();
        users = session.createQuery("from User").list();
        session.close();
        return users;
    }

    public void add(User user) {
        Session session = this.sessionFactory.openSession();
        session.save(user);
        session.close();
    }

    public void update(User user){
        Session session = sessionFactory.openSession();
        session.saveOrUpdate(user);
        session.flush();
        session.close();
    }
    public User findByEmail(String email){
        Session session = sessionFactory.openSession();
        String hql = "FROM User u WHERE u.email = '" + email + "'";
        Query query = session.createQuery(hql);
        List results = query.list();
        session.close();
        if (!results.isEmpty())
            return (User) results.get(0);
        return null;
    }


    public void delete(User user) {
        logger.info("Removing user with id " + user.getId());
        Session session = sessionFactory.openSession();
        session.delete(user);
        session.flush();
        session.close();
    }

    public User getUser(Integer id) {
        Session session = sessionFactory.openSession();
        User user = (User) session.get(User.class, id);
        session.close();
        return user;
    }
}
