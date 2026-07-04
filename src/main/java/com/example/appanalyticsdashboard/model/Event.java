package com.example.appanalyticsdashboard.model;

import jakarta.persistence.*;
import jakarta.websocket.Session;
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
    private Session session;   // now correctly resolves to your own Session entity

    @ManyToOne
    @JoinColumn(name = "screen_id")
    private com.example.appanalyticsdashboard.model.Screen screen;

    private String eventType;
    private String eventLabel;
    private LocalDateTime timestamp;
}