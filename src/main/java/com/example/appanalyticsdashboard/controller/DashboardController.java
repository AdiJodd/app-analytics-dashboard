package com.example.appanalyticsdashboard.controller;

import org.springframework.web.bind.annotation.ResponseBody;


import com.example.appanalyticsdashboard.service.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DashboardController {

    private final EventService eventService;

    public DashboardController(EventService eventService) {
        this.eventService = eventService;
    }
    @GetMapping("/")
    public String home() {
        return "home";
    }


}