package com.evozon.training.persistence;

import com.evozon.training.model.Product;
import com.evozon.training.model.Category;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class ProductPersistence {
    @Autowired
    private SessionFactory sessionFactory;
    private static final Logger logger = LoggerFactory.getLogger(ProductPersistence.class);

    public List<Product> getAllProducts() {
        List<Product> products;
        Session session = sessionFactory.openSession();
        products = session.createQuery("from Product").list();
        session.close();
        return products;
    }

    public void addProduct(Product product) {
        Session session = this.sessionFactory.openSession();
        session.save(product);
        session.close();
    }

    public Product getProduct(Integer id) {
        Session session = sessionFactory.openSession();
        Product product = (Product) session.get(Product.class, id);
        session.close();
        return product;
    }

    public void update(Product product){
        Session session = sessionFactory.openSession();
//        Product updatedProduct = (Product) session.merge(product);
        session.saveOrUpdate(product);
        session.flush();
        session.close();
    }

    public void deleteProduct(Product product){
        logger.info("Removing product with id " + product.getWineId());
        Session session = sessionFactory.openSession();
        session.delete(product);
        session.flush();
        session.close();
    }

    public String getProductPicture(int productId){
        Session session = sessionFactory.openSession();
        String productPicture = (String)session.createQuery("select p.picture from Product p where p.wineId = :id")
                .setParameter("id", productId).uniqueResult();
        session.close();
        return productPicture;
    }

    public List<Category> getCategories() {
        List<Category> categories;
        Session session = sessionFactory.openSession();
        categories = session.createQuery("from Category").list();
        session.close();
        return categories;
    }

    public Boolean addCategory(Category category) {
        Session session = sessionFactory.openSession();
        session.save(category);
        session.close();
        return true;
    }

    public List<Product> sortProducts(String sortingCriteria,Integer startIndex,Integer maxNb) {
        List<Product> products;
        Session session = sessionFactory.openSession();
        String query=new String("from Product");
        if(sortingCriteria.startsWith("name"))
            if(sortingCriteria.contains("desc"))
                query+=" ORDER BY name DESC";
            else
                query+=" ORDER BY name ASC";
        if(sortingCriteria.startsWith("price"))
            if(sortingCriteria.contains("desc"))
                query+=" ORDER BY price DESC";
            else
                query+=" ORDER BY price ASC";
        Query q = session.createQuery(query);
        q.setFirstResult(startIndex);
        q.setMaxResults(maxNb);
        products=q.list();
        session.close();
        return products;
    }

    private String sortFilter(String sortingCriteria,String query){
        if(sortingCriteria.startsWith("name"))
            if(sortingCriteria.contains("desc"))
                query+=" ORDER BY name DESC";
            else
                query+=" ORDER BY name ASC";
        if(sortingCriteria.startsWith("price"))
            if(sortingCriteria.contains("desc"))
                query+=" ORDER BY price DESC";
            else
                query+=" ORDER BY price ASC";
         return  query;
    }
    public void deleteAll(String products){
        Session session = sessionFactory.openSession();
        String deleteQuerry = "DELETE FROM Product wine WHERE wine.wineId in (:productsIds)";
        List<Integer> ids = new ArrayList<Integer>();
        for(String currentString : Arrays.asList(products.split("\\s*,\\s*")))
            ids.add(Integer.valueOf(currentString));
        session.createQuery(deleteQuerry).setParameterList("productsIds", ids).executeUpdate();
    }

    public List<Product> getFilteredProducts(String category,Integer year,String type,Integer startIndex,Integer maxNb, String args){
        List<Product> productList=new ArrayList<Product>();
        int categoryId=1;
        Session session=sessionFactory.openSession();
        String query=new String("from Category");
        List<Category> categories = session.createQuery(query).list();
        for (Category c:categories){
            if (c.getName().equals(category)){
                categoryId=c.getID();
            }
        }
        Query q=null;
        query="from Product";
        if (!category.equals("null")){
            if (!(year==0)){
                if (!type.equals("null")){
                    query+=" where category = :categoryParam and yearOfProduction = :yearParam and type = :typeParam";
                    query=sortFilter(args,query);
                    q = session.createQuery(query).setParameter("categoryParam",categoryId).setParameter("yearParam",year).setParameter("typeParam",type);
                }
                else {
                    query+=" where category = :categoryParam and yearOfProduction = :yearParam";
                    query=sortFilter(args,query);
                    q=session.createQuery(query).setParameter("categoryParam",categoryId).setParameter("yearParam",year);

                }
            }
            else {
                if (!type.equals("null")){
                    query+=" where category = :categoryParam  and type = :typeParam";
                    query=sortFilter(args,query);
                    q = session.createQuery(query).setParameter("categoryParam",categoryId).setParameter("typeParam",type);
                }
                else{
                    query+=" where category = :categoryParam";
                    query=sortFilter(args,query);
                    q=session.createQuery(query).setParameter("categoryParam",categoryId);
                }
            }
        }
        else {
            if (!(year==0)){
                if (!type.equals("null")){
                    query+=" where yearOfProduction = :yearParam and type = :typeParam";
                    query=sortFilter(args,query);
                    q=session.createQuery(query).setParameter("yearParam",year).setParameter("typeParam",type);
                }
                else {
                    query+=" where yearOfProduction = :yearParam";
                    query=sortFilter(args,query);
                    q=session.createQuery(query).setParameter("yearParam",year);
                }
            }
            else {
                if (!type.equals("null")){
                    query+=" where type = :typeParam";
                    query=sortFilter(args,query);
                    q=session.createQuery(query).setParameter("typeParam",type);
                }
                else {
                    q=session.createQuery(query);
                }
            }
        }

        q.setFirstResult(startIndex);
        q.setMaxResults(maxNb);
        productList=q.list();

        return productList;
    }
}
