package com.hello.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String getDashBoard() {
        return "layouts/dashboard/spadashboard";
    }

}
