package com.evozon.training.model;

import java.util.List;

public class CartDTO {

    private final static Integer TVA_RATE = 19;
    private Integer cartId;

    private Double totalCost;

    private Double TVA;

    public Double getTVA() {
        return TVA;
    }

    public void setTVA() {
        this.TVA = (TVA_RATE / 100.0) * totalCost;
        this.TVA = Math.round(this.TVA * 100.0) / 100.0;
    }

    private List<CartEntryDTO> listOfEntries;

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public List<CartEntryDTO> getListOfEntries() {
        return listOfEntries;
    }

    public void setListOfEntries(List<CartEntryDTO> listOfEntries) {
        this.listOfEntries = listOfEntries;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }
}
