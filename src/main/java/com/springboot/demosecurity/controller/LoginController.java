package com.springboot.demosecurity.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/showLoginPage")
    public String showLoginPage(){
        return "fancy-login";
    }


    // add request mapping for  /access-denied
    @GetMapping("/access-denied")
    public String showDeniedPage(){
        return "access-denied";
    }


}
