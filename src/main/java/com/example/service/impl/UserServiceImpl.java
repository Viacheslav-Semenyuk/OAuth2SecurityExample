package com.example.service.impl;

import com.example.constant.Constants;
import com.example.entity.User;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;

import static java.lang.String.format;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(format(Constants.ENTITY_NOT_FOUND_EXCEPTION_MSG, username)));
    }

    @Override
    public void save(User user) {
        if (userRepository.existsByUsername(user.getUsername())){
            throw new EntityNotFoundException(format(Constants.USER_BY_EMAIL_ALREADY_EXISTS_EXCEPTION_MSG, user.getUsername()));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void updatePassword(User newUser) {
       User user = findByUsername(newUser.getUsername());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userRepository.save(user);

    }

    @Override
    public void delete(String email) {
        userRepository.delete(findByUsername(email));
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = findByUsername(username);
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                new ArrayList<>());
    }

}
