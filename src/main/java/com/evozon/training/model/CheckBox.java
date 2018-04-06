package com.evozon.training.model;

public class CheckBox {

    private String name;
    private Boolean state;

    public CheckBox(String name, Boolean state){
        this.name = name;
        this.state = state;
    }

    public String getName(){
        return this.name;
    }

    public Boolean getState(){
        return this.state;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setState(Boolean state){
        this.state = state;
    }

}
