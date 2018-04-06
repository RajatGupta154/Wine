package com.evozon.training.model.users;


public class UserDto{

//    @NotEmpty
//    @NotNull
    private String name;

//    @NotEmpty

//    @NotNull
    private String password;

//    @NotEmpty
//    @NotNull
    private String email;

//    @NotEmpty
//    @NotNull
    private String phoneNumber;

//    @NotEmpty
//    @NotNull
    private String matchingPassword;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }
}
