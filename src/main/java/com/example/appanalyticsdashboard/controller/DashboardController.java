package com.example.appanalyticsdashboard.controller;

import com.example.appanalyticsdashboard.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private EventService eventService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("eventCount", eventService.countEvents());
        return "home"; // resolves to templates/home.html
    }
}
