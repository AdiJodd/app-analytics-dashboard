package com.example.appanalyticsdashboard.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "screens")
@Data
public class Screen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String screenName; // e.g. "HomeScreen", "Checkout", "Profile"

    private String screenCategory; // e.g. "core", "settings", "onboarding"
}
