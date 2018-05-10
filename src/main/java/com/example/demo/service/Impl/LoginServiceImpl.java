package com.example.demo.service.Impl;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserMapper mapper;

    @Override
    public boolean verify(User user) {
        User user1 = mapper.getByName(user.getUsername());
        return user1 != null && user1.getUsername().equals(user.getUsername())
                && user1.getPassword().equals(user.getPassword());
    }

    @Override
    public boolean register(User user) {
        User user1 = mapper.getByName(user.getUsername());
        if (user.getUsername().isEmpty() || user1 != null) {
            return false;
        } else {
            mapper.insert(user);
            return true;
        }
    }
}
