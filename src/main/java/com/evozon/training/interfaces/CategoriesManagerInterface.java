package com.evozon.training.interfaces;

import com.evozon.training.model.Category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface CategoriesManagerInterface{

    List<Category> getAllTheCategories();
    HashMap<String, Integer> getAllTheCategoriesMap();
    //Integer getTheNumberOfProducts(Category category);
    String getCategory(String productName);
    Boolean addCategory(Category category);

}