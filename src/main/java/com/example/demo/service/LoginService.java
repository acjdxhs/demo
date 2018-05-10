package com.example.demo.service;

import com.example.demo.model.User;

public interface LoginService {
    boolean verify(User user);
    boolean register(User user);
}
