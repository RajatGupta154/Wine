package com.evozon.training.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.Column;

@Entity
@Table(name = "wine")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wineid")
    private int wineId;
    @Column
    private String name;
    @Column
    private String brand;
    @Column
    private Double price;
    @Column
    private Integer stock;
    @Column(name = "type")
    private String wineType;
    @Column
    private String countryOfOrigin;
    @Column
    private Integer yearOfProduction;
    @Column
    private Double volume;
    @Column
    private String description;
    @Column
    private String recommendations;
    @Column
    private String picture;
    @Column(name = "category")
    private Integer categoryID;

    public Product() {
    }

    public Product(String name, String brand, double price, int stock, String wineType, String countryOfOrigin, int yearOfProduction, double volume, String description, String recommendations, String categoryID) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.stock = stock;
        this.wineType = wineType;
        this.countryOfOrigin = countryOfOrigin;
        this.yearOfProduction = yearOfProduction;
        this.volume = volume;
        this.description = description;
        this.recommendations = recommendations;
    }

    public int getWineId() {
        return wineId;
    }

    public void setWineId(int wineId) {
        this.wineId = wineId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getWineType() {
        return wineType;
    }

    public void setWineType(String wineType) {
        this.wineType = wineType;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public Integer getYearOfProduction() {
        return yearOfProduction;
    }

    public void setYearOfProduction(Integer yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Integer getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }

    @Override
    public String toString() {
        return "Product{" +
                "wineId=" + wineId +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", wineType='" + wineType + '\'' +
                ", countryOfOrigin='" + countryOfOrigin + '\'' +
                ", yearOfProduction=" + yearOfProduction +
                ", volume=" + volume +
                ", description='" + description + '\'' +
                ", recommendations='" + recommendations + '\'' +
                '}';
    }
}