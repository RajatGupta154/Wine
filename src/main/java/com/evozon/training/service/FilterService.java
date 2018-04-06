package com.evozon.training.service;

import com.evozon.training.model.Category;
import com.evozon.training.model.CheckBox;
import com.evozon.training.model.FilterOptions;
import com.evozon.training.model.Product;
import com.evozon.training.persistence.ProductPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FilterService {

    @Autowired
    private ProductPersistence productPersistence;
    @Autowired
    private CategoriesService categoriesService;

    public List<Product> applyFilters(FilterOptions filterOptions, Integer start, Integer max, String args) {
        String category = "null";
        Integer year = 0;
        String type = "null";

        if(!(filterOptions.getCategory() == null))
            category = filterOptions.getCategory();
        if(!(filterOptions.getYear() == null))
            year = Integer.parseInt(filterOptions.getYear());
        if(!(filterOptions.getType() == null))
            type = filterOptions.getType();

        List<Product> products = productPersistence.getFilteredProducts(category, year, type, start, max, args);

        return products;
    }

    public List<String> getYears() {
        List<Product> products = productPersistence.getAllProducts();
        List<String> years = new ArrayList<String>();

        for (Product p : products) {
            String year = String.valueOf(p.getYearOfProduction());
            if (!years.contains(year))
                years.add(year);
        }
        return years;
    }

    public List<Category> getCategories() {
        return categoriesService.getAllTheCategories();
    }

    public List<String> getTypes() {
        List<Product> products = productPersistence.getAllProducts();
        List<String> types = new ArrayList<String>();

        for (Product p : products) {
            String year = String.valueOf(p.getWineType());
            if (!types.contains(year))
                types.add(year);
        }
        return types;
    }

    public List<CheckBox> getCategoriesFilteredProducts(List<Product> filteredProducts, FilterOptions filterOptions, List<Category> allCategories) {
        List<CheckBox> categories = new ArrayList<>();
        for (Product product : filteredProducts) {
            Integer categoryId = product.getCategoryID();
            String categoryString = null;
            boolean alreadyExists = false;

            for (Category category : allCategories) {
                if (category.getID() == categoryId) {
                    categoryString = category.getName();
                    break;
                }
            }

            for (CheckBox categoryCheckBox : categories) {
                if (categoryCheckBox.getName().equals(categoryString)) {
                    alreadyExists = true;
                }
            }
            if (!alreadyExists) {
                if (filterOptions.getCategory() != null && filterOptions.getCategory().equals(categoryString))
                    categories.add(new CheckBox(categoryString, true));
                else
                    categories.add(new CheckBox(categoryString, false));
            }
        }
        return categories;
    }

    public List<CheckBox> getYearsFilteredProducts(List<Product> filteredProducts, FilterOptions filterOptions) {
        List<CheckBox> years = new ArrayList<CheckBox>();

        for (Product product : filteredProducts) {
            Integer year = product.getYearOfProduction();
            boolean alreadyExists = false;

            for (CheckBox yearCheckBox : years) {
                if (yearCheckBox.getName().equals(String.valueOf(year))) {
                    alreadyExists = true;
                }
            }
            if (!alreadyExists) {
                if (filterOptions.getYear() != null && filterOptions.getYear().equals(String.valueOf(year)))
                    years.add(new CheckBox(String.valueOf(year), true));
                else
                    years.add(new CheckBox(String.valueOf(year), false));
            }
        }
        return years;
    }

    public List<CheckBox> getTypesFilteredProducts(List<Product> filteredProducts, FilterOptions filterOptions) {
        List<CheckBox> types = new ArrayList<CheckBox>();

        for (Product product : filteredProducts) {
            String type = product.getWineType();
            boolean alreadyExists = false;

            for (CheckBox typeCheckBox : types) {
                if (typeCheckBox.getName().equals(String.valueOf(type))) {
                    alreadyExists = true;
                }
            }
            if (!alreadyExists) {
                if (filterOptions.getType() != null && filterOptions.getType().equals(type))
                    types.add(new CheckBox(type, true));
                else
                    types.add(new CheckBox(type, false));
            }

        }
        return types;
    }

    public Map<String, Integer> getNumberOfProductsPerCategory(List<Product> filteredProducts, List<Category> allCategories){
        Map<String, Integer> numberOfProductsPerCategory = new HashMap<String, Integer>();
        String categoryString = null;

        for(Product product: filteredProducts){
            for (Category category : allCategories) {
                if (category.getID() == product.getCategoryID()) {
                    categoryString = category.getName();
                    break;
                }
            }
            if(numberOfProductsPerCategory.get(categoryString) != null){
                Integer initialVal = numberOfProductsPerCategory.get(categoryString);
                numberOfProductsPerCategory.put(categoryString, initialVal+1);
            } else {
                numberOfProductsPerCategory.put(categoryString, 1);
            }
        }
        return numberOfProductsPerCategory;
    }


    public Map<String, Integer> getNumberOfProductsPerYear(List<Product> filteredProducts){
        Map<String, Integer> numberOfProductsPerYear = new HashMap<String, Integer>();

        for(Product product: filteredProducts){
            if(numberOfProductsPerYear.get(String.valueOf(product.getYearOfProduction())) != null){
                Integer initialVal = numberOfProductsPerYear.get(String.valueOf(product.getYearOfProduction()));
                numberOfProductsPerYear.put(String.valueOf(product.getYearOfProduction()), initialVal+1);
            } else {
                numberOfProductsPerYear.put(String.valueOf(product.getYearOfProduction()), 1);
            }
        }
        return numberOfProductsPerYear;
    }

    public Map<String, Integer> getNumberOfProductsPerType(List<Product> filteredProducts){
        Map<String, Integer> numberOfProductsPerType = new HashMap<String, Integer>();

        for(Product product: filteredProducts){
            if(numberOfProductsPerType.get(product.getWineType()) != null){
                Integer initialVal = numberOfProductsPerType.get(product.getWineType());
                numberOfProductsPerType.put(product.getWineType(), initialVal+1);
            } else {
                numberOfProductsPerType.put(product.getWineType(), 1);
            }
        }
        return numberOfProductsPerType;
    }

}
