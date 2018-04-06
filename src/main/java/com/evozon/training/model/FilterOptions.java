package com.evozon.training.model;

import java.util.ArrayList;
import java.util.List;

public class FilterOptions {

    private String category;
    private String year;
    private String type;

    public FilterOptions(){
        }


    public String getCategory(){
        return this.category;
    }

    public String getYear(){
        return this.year;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
