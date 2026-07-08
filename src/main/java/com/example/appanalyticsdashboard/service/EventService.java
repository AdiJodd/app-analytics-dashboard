package com.example.appanalyticsdashboard.service;

import com.example.appanalyticsdashboard.repository.EventRepository;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public long countEvents() {
        return eventRepository.count();
    }
}