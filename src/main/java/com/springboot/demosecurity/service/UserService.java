package com.springboot.demosecurity.service;

import com.springboot.demosecurity.entity.NewUser;
import com.springboot.demosecurity.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    public User findByUserName(String userName);

    void save(NewUser webUser);
}
