package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/admindashboard")
    public String adminDashboard() {
        return "admindashboard";
    }

    @GetMapping("/userdashboard")
    public String userDashboard() {
        return "userdashboard";
    }
}
