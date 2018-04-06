package com.evozon.training.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.Column;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer categoryID;
    @Column
    private String name;

    public Category(){

    }

    public Category(Integer id, String name){
        this.categoryID = id;
        this.name = name;
    }

    public Integer getID(){
        return this.categoryID;
    }

    public void setCategoryID(Integer id){
        this.categoryID = id;
    }

     public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

}
