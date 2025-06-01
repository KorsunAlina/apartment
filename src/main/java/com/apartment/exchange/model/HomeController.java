package com.apartment.exchange.model;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String redirectToApplications() {
        return "redirect:/applications";
    }
}
