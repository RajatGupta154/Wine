package com.evozon.training.model.validators;

import com.evozon.training.model.users.User;
import com.evozon.training.persistence.UserPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class EmailValidator {

    @Autowired
    private UserPersistence userPersistence;


    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";

    public boolean isValid(String email){

        return (validateEmail(email));
    }

    private boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean emailExist(String email) {
        User user = userPersistence.findByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }

    public boolean exists(String email){
        return emailExist(email);
    }

}