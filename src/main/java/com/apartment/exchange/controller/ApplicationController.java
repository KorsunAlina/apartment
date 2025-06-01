package com.apartment.exchange.controller;

import com.apartment.exchange.model.Application;
import com.apartment.exchange.service.ApplicationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService service;

    public ApplicationController(ApplicationService service) {
        this.service = service;
    }

    @GetMapping
    public String viewAll(Model model) {
        model.addAttribute("applications", service.getAllApplications());
        return "list";
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("application", new Application());
        return "form";
    }

    @PostMapping
    public String submitApplication(@ModelAttribute Application application, Model model) {
        Application matched = service.findAndRemoveMatchingApplication(application);

        if (matched != null) {
            // Якщо знайдено відповідну заявку – передаємо її на відображення
            model.addAttribute("matchedApplication", matched);
            return "matchedCard"; // назва шаблону для відображення знайденої заявки
        } else {
            // Якщо не знайдено – додаємо нову заявку
            service.addApplication(application);
            return "redirect:/applications";
        }
    }
}
