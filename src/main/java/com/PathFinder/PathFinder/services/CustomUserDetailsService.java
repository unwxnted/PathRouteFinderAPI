package com.PathFinder.PathFinder.services;

import com.PathFinder.PathFinder.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.*;
import com.PathFinder.PathFinder.security.UserPrincipal;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getByName(username);

        if(user == null) throw new UsernameNotFoundException("User not found");

        return UserPrincipal.create(user);
    }

}
