package com.example.appanalyticsdashboard.controller;

import com.example.appanalyticsdashboard.model.*;
import com.example.appanalyticsdashboard.repository.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ApiControlller {

    private final EventRepository eventRepository;
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final ScreenRepository screenRepository;

    public ApiControlller(EventRepository eventRepository,
                          SessionRepository sessionRepository,
                          UserRepository userRepository,
                          ScreenRepository screenRepository) {
        this.eventRepository = eventRepository;
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
        this.screenRepository = screenRepository;
    }

    // ---------- Basic endpoints ----------

    @GetMapping("/api/events")
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @GetMapping("/api/events/count")
    public long getEventCount() {
        return eventRepository.count();
    }

    @GetMapping("/api/sessions")
    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    @GetMapping("/api/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/api/screens")
    public List<Screen> getAllScreens() {
        return screenRepository.findAll();
    }

    // ---------- Analytics endpoints ----------

    @GetMapping("/api/analytics/events-by-type")
    public List<Map<String, Object>> getEventsByType() {
        List<Object[]> results = eventRepository.countEventsByType();
        List<Map<String, Object>> response = new ArrayList<>();
        for (Object[] row : results) {
            Map<String, Object> entry = new HashMap<>();
            entry.put("eventType", row[0]);
            entry.put("count", row[1]);
            response.add(entry);
        }
        return response;
    }

    @GetMapping("/api/analytics/events-by-screen")
    public List<Map<String, Object>> getEventsByScreen() {
        List<Object[]> results = eventRepository.countEventsByScreen();
        List<Map<String, Object>> response = new ArrayList<>();
        for (Object[] row : results) {
            Map<String, Object> entry = new HashMap<>();
            entry.put("screenName", row[0]);
            entry.put("count", row[1]);
            response.add(entry);
        }
        return response;
    }

    @GetMapping("/api/analytics/dau")
    public List<Map<String, Object>> getDailyActiveUsers() {
        List<Object[]> results = eventRepository.getDailyActiveUsers();
        List<Map<String, Object>> response = new ArrayList<>();
        for (Object[] row : results) {
            Map<String, Object> entry = new HashMap<>();
            entry.put("date", row[0].toString());
            entry.put("activeUsers", row[1]);
            response.add(entry);
        }
        return response;
    }

    @GetMapping("/api/analytics/avg-session-duration")
    public Map<String, Object> getAvgSessionDuration() {
        Double avgMinutes = sessionRepository.getAverageSessionDurationMinutes();
        Map<String, Object> response = new HashMap<>();
        response.put("averageMinutes", avgMinutes != null ? Math.round(avgMinutes * 10) / 10.0 : 0);
        return response;
    }
}