package com.springboot.demosecurity.dao;

import com.springboot.demosecurity.entity.User;

public interface UserDao {

    User findByUserName(String userName);

    void save(User theUser);
    
}
