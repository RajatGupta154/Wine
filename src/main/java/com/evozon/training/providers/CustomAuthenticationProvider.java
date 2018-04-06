package com.evozon.training.providers;

import com.evozon.training.model.users.User;
import com.evozon.training.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();

        Object credentials = authentication.getCredentials();
        String password = credentials.toString();

        if (email == null || password == null) {
            throw new BadCredentialsException("Invalid username/password");
        }

        User user = userService.getUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username/password");
        }

        if (! password.equals(user.getPassword())) {
            throw new BadCredentialsException("Invalid username/password");
        }

        if (!user.getActive()) {
            throw new BadCredentialsException("Inactive account!");
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        Authentication auth = new UsernamePasswordAuthenticationToken(email, password, grantedAuthorities);

        return auth;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
