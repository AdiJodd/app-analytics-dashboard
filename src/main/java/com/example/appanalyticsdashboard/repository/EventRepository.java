package com.example.appanalyticsdashboard.repository;

import com.example.appanalyticsdashboard.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}