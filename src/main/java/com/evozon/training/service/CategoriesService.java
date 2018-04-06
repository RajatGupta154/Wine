package com.evozon.training.service;

import com.evozon.training.persistence.ProductPersistence;
import com.evozon.training.interfaces.CategoriesManagerInterface;
import com.evozon.training.model.Category;
import com.evozon.training.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class CategoriesService implements CategoriesManagerInterface {

    @Autowired
    private ProductPersistence productPersistence;

    public List<Category> getAllTheCategories() {
        List<Category> categories = productPersistence.getCategories();
        return categories;
    }

    public HashMap<String, Integer> getAllTheCategoriesMap() {
        HashMap<String, Integer> categoryQuantityMap = new HashMap<String, Integer>();
        List<Category> categories = productPersistence.getCategories();
        Integer productsNumber;

        for (Category category : categories) {
            productsNumber = getTheNumberOfProducts(category);
            categoryQuantityMap.put(category.getName(), productsNumber);
        }

        return categoryQuantityMap;
    }

    public Integer getTheNumberOfProducts(Category category) {
        Integer productsNumber = 0;
        List<Product> products = productPersistence.getAllProducts();
        for (Product product : products) {
            if (category.getID().equals(product.getCategoryID())) {
                productsNumber++;
            }
        }
        return productsNumber;
    }

    public String getCategory(String productName) {
        String categoryName = null;
        List<Product> products = productPersistence.getAllProducts();
        List<Category> categories = productPersistence.getCategories();

        for (Product p : products) {
            if (p.getName().equals(productName)) {
                for (Category c : categories) {
                    if (c.getID() == p.getCategoryID())
                        categoryName = c.getName();
                }
            }
        }

        return categoryName;
    }

    public Boolean addCategory(Category category) {
        Boolean succesfulOp = false;

        if (productPersistence.addCategory(category))
            succesfulOp = true;

        return succesfulOp;
    }

}