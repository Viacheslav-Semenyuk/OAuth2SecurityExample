package com.example.service;

import com.example.entity.User;


public interface UserService {

    User findByUsername(String email);

    void save(User user);

    void updatePassword(User newUser);

    void delete(String email);
}
