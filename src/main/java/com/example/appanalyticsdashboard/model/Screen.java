package com.example.appanalyticsdashboard.model;

import jakarta.persistence.*;

@Entity
@Table(name = "screens")
public class Screen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String screenName; // e.g. "HomeScreen", "Checkout", "Profile"

    private String screenCategory; // e.g. "core", "settings", "onboarding"

    public Long getId() {
        return id;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getScreenCategory() {
        return screenCategory;
    }

    public void setScreenCategory(String screenCategory) {
        this.screenCategory = screenCategory;
    }
}