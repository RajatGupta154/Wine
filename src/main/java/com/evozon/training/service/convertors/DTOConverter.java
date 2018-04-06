package com.evozon.training.service.convertors;

import com.evozon.training.model.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DTOConverter {

    public Product convertDTOToProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setBrand(productDTO.getBrand());
        product.setPrice(productDTO.getPrice());
        if (productDTO.getStock() == null) {
            product.setStock(0);
        } else {
            product.setStock(productDTO.getStock());
        }
        product.setWineType(productDTO.getWineType());
        product.setCountryOfOrigin(productDTO.getCountryOfOrigin());
        product.setYearOfProduction(productDTO.getYearOfProduction());
        product.setVolume(productDTO.getVolume());
        product.setDescription(productDTO.getDescription());
        product.setRecommendations(productDTO.getRecommendations());
        product.setWineId(productDTO.getWineId());
        product.setCategoryID(productDTO.getCategoryID());

        return product;
    }

    public ProductDTO convertProductToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(product.getName());
        productDTO.setWineId(product.getWineId());
        productDTO.setBrand(product.getBrand());
        productDTO.setPrice(product.getPrice());
        productDTO.setStock(product.getStock());
        productDTO.setWineType(product.getWineType());
        productDTO.setCountryOfOrigin(product.getCountryOfOrigin());
        productDTO.setVolume(product.getVolume());
        productDTO.setRecommendations(product.getRecommendations());
        productDTO.setYearOfProduction(product.getYearOfProduction());
        productDTO.setDescription(product.getDescription());
        productDTO.setCategoryID(product.getCategoryID());

        return productDTO;
    }

    public CartDTO convertCartToCartDTO(Cart cart){
        CartDTO cartDTO = new CartDTO();
        List<CartEntry> listOfEntries = cart.getListOfEntries();
        List<CartEntryDTO> listOfCartEntryDTO = new ArrayList<>();
        for(CartEntry cartEntry : listOfEntries){
            CartEntryDTO cartTemp = new CartEntryDTO();
            cartTemp.setCart(cartEntry.getCart());
            cartTemp.setEntryId(cartEntry.getEntryId());
            cartTemp.setProduct(cartEntry.getProduct());
            cartTemp.setQuantity(cartEntry.getQuantity());
            listOfCartEntryDTO.add(cartTemp);
        }
        cartDTO.setListOfEntries(listOfCartEntryDTO);
        cartDTO.setTotalCost(cart.getTotalCost());
        cartDTO.setCartId(cart.getCartId());
        cartDTO.setTVA();

        return cartDTO;
    }
}
