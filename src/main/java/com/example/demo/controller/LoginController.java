package com.example.demo.controller;

import com.example.demo.model.Response;
import com.example.demo.model.User;
import com.example.demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(value = "http://localhost:8181/")
@RestController
public class LoginController {
    @Autowired
    private Response response;
    @Autowired
    private LoginService service;

    @RequestMapping("login")
    public Response Login(User user) {

        if(service.verify(user)) {
            response.setStatus(200);
            response.setMsg("登录成功");
        } else {
            response.setStatus(304);
            response.setMsg("账号或密码错误");
        }
        return response;
    }

    @RequestMapping("register")
    public Response Register(User user) {
        if (service.register(user)) {
            response.setStatus(200);
            response.setMsg("注册成功");
        } else {
            response.setStatus(304);
            response.setMsg("账号已存在");
        }
        return response;
    }
}
