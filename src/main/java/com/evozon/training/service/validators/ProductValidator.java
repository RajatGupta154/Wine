package com.evozon.training.service.validators;

import com.evozon.training.model.ProductDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Calendar;

@Component
public class ProductValidator implements Validator {

    public static final String IS_REQUIRED = " is required!";

    public void validate(Object object, Errors errors) {

        ProductDTO product = (ProductDTO) object;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.empty.error", "Name" + IS_REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "brand", "brand.empty.error", "Brand" + IS_REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "wineType", "type.empty.error", "Type" + IS_REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "countryOfOrigin", "country.empty.error", "Country of origin" + IS_REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "price.empty.error", "Price" + IS_REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "volume", "volume.empty.error", "Volume" + IS_REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "yearOfProduction", "year.empty.error", "Year of production" + IS_REQUIRED);

        Double price = product.getPrice();
        if (price != null && price <= 0) {
            errors.rejectValue("price", "price.incorrect.error", new Object[]{"args"}, "Price must be greater than 0!");
        }

        Integer stock = product.getStock();
        if (stock != null && stock < 0) {
            errors.rejectValue("stock", "stock.incorrect.error", new Object[]{"args"}, "Stock must be greater or equal with 0!");
        }

        Double volume = product.getVolume();
        if (volume != null && volume < 0) {
            errors.rejectValue("volume", "volume.incorrect.error", new Object[]{"args"}, "Volume must be greater than 0!");
        }

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        Integer yearOfProduction = product.getYearOfProduction();
        if ((yearOfProduction != null) && (yearOfProduction < 0 || yearOfProduction > currentYear)) {
            errors.rejectValue("yearOfProduction", "year.incorrect.error", new Object[]{"args"}, "Year of production must be lower than the currrent year and greater than 0!");
        }
    }

    public boolean supports(Class<?> aClass) {
        return ProductDTO.class.equals(aClass);
    }
}
