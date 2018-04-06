package com.evozon.training.service;

import com.evozon.training.Util.SendEmail;
import com.evozon.training.interfaces.IUserService;
import com.evozon.training.model.users.User;
import com.evozon.training.model.users.UserDto;
import com.evozon.training.persistence.UserPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserPersistence userPersistence;

    @Override
    public void registerNewUserAccount(UserDto accountDto) {
        User user = new User();
        user.setName(accountDto.getName());
        user.setPassword(accountDto.getPassword());
        user.setEmail(accountDto.getEmail());
        user.setPhoneNumber(Long.parseLong(accountDto.getPhoneNumber()));
        userPersistence.add(user);
        String token = UUID.randomUUID().toString();
        SendEmail.sendMethod(user.getEmail(), user.getId(),token);
    }


    @Override
    public boolean login(User user) {
        List<User> users = userPersistence.getAll();
        boolean found = false;

        for (User existingUser : users) {
            if (existingUser.getEmail().equals(user.getEmail()) && existingUser.getPassword().equals(user.getPassword()) && existingUser.getActive()) {
                found = true;
                break;
            }
        }

        return found;
    }

    @Override
    public void addUser(User user){
        userPersistence.add(user);
    }

    @Override
    public void activatingUserAccount(Integer id) {
        User user = userPersistence.getUser(id);
        if (user != null)
            user.setActive(true);
            userPersistence.update(user);
    }


    public List<User> getAll() {
        return userPersistence.getAll();
    }

    public User getUserByEmail(String email) {
        List<User> users = getAll();
        User foundUser = null;
        for (User user: users) {
            if (user.getEmail().equals(email)) {
                foundUser = user;
            }
        }

        return foundUser;
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getPrincipal().toString();
        return getUserByEmail(email);
    }
}
