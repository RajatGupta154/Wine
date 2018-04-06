package com.evozon.training.model;

public enum WineType {
    RED("red"), ROSE("rose"), SPARKLING("sparkling"), WHITE("white");

    private String wineTypeValue;

    WineType(String value) {
        this.wineTypeValue = value;
    }

    public String getWineTypeValue() {
        return this.wineTypeValue;
    }
}