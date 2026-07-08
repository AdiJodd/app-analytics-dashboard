package com.example.appanalyticsdashboard.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Data
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session session;

    @ManyToOne
    @JoinColumn(name = "screen_id")
    private Screen screen;

    private String eventType; // click, view, purchase, tap, error, scroll

    private String eventLabel; // e.g. "click_1"

    private LocalDateTime timestamp;
}