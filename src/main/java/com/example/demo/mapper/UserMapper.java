package com.example.demo.mapper;

import com.example.demo.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component("userMapper")
@Mapper
public interface UserMapper {
    User getByName (@Param("username") String username);
    void insert(@Param("User") User user);
}
