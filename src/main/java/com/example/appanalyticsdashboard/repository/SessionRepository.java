package com.example.appanalyticsdashboard.repository;

import com.example.appanalyticsdashboard.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {
}