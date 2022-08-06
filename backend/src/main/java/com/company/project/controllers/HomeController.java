package com.company.project.controllers;

import com.company.project.entity.Greeting;
import com.company.project.repository.GreetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

    @Autowired
    private GreetingRepository repository;

    @GetMapping("/{id}")
    public String showHome(@PathVariable int id, Model model) {
        Greeting dockerGreeting = repository.findById(id).orElse(new Greeting("Hello from Kubernetes"));
        model = model.addAttribute("name", dockerGreeting.getName());
        return "home";
    }

}
