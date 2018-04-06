package com.evozon.training.model.users;

import com.evozon.training.model.validators.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserFormValidator implements Validator {

    @Autowired
    EmailValidator emailValidator;


    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        UserDto user = (UserDto) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.userForm.name","Name is required!");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.userForm.email","Email is required!");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.userForm.password","Password is required!");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber", "NotEmpty.userForm.phoneNumber","Phone Number is required!");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "matchingPassword", "NotEmpty.userForm.matchingPassword","Mtching password is required!");

        if(!emailValidator.isValid(user.getEmail()))
            errors.rejectValue("email","Pattern.userForm.email","Invalid Email format!");

        if(emailValidator.exists(user.getEmail()))
            errors.rejectValue("email","Email.Exists.Error","Email already exists!");

        if(!user.getPhoneNumber().matches("^[0-9]\\d*$"))
            errors.rejectValue("phoneNumber","Pattern.useForm.phoneNumber","Invalid Phone Number format!");

        if (!user.getPassword().equals(user.getMatchingPassword())) {
            errors.rejectValue("matchingPassword", "Diff.userform.confirmPassword","Passwords do not match, please retype!");
        }

    }

}