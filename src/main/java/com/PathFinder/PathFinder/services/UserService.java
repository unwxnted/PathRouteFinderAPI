package com.PathFinder.PathFinder.services;

import com.PathFinder.PathFinder.models.User;
import com.PathFinder.PathFinder.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getByName(String name){
        List<User> result = userRepository.findByName(name);
        return result.getFirst();
    }

    public boolean registerUser(User user){
        try{
            if(!userRepository.findById(user.getId()).isEmpty()) return false;
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return true;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    public boolean loginUser(User user){
        try{
            return passwordEncoder.matches(user.getPassword(), this.getByName(user.getName()).getPassword());
        }catch (Exception e){
            return false;
        }
    }
}
