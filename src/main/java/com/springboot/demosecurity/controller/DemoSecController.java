package com.springboot.demosecurity.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoSecController {

    @GetMapping("/")
    public String showlandingpage(){

        return "landing";
    }

    @GetMapping("/employees")
    public String showHome(){

        return "home";
    }


    // add a request mapping for /leaders

    @GetMapping("/leaders")
    public String showLeaders(){


        return "leaders";
    }

    // add a request mapping for /systems
    @GetMapping("/systems")
    public String showAdmins(){


        return "systems";
    }
}
