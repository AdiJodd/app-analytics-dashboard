package com.example.appanalyticsdashboard.controller;

import com.example.appanalyticsdashboard.model.Event;
import com.example.appanalyticsdashboard.repository.EventRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiControlller {

    private final EventRepository eventRepository;

    public ApiControlller(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @GetMapping("/api/events")
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @GetMapping("/api/events/count")
    public long getEventCount() {
        return eventRepository.count();
    }
}