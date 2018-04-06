package com.evozon.training.interfaces;

import com.evozon.training.model.users.User;
import com.evozon.training.model.users.UserDto;

public interface IUserService {

    void registerNewUserAccount(UserDto accountDto);

    boolean login(User user);

    void addUser(User user);

    void activatingUserAccount(Integer id);
}