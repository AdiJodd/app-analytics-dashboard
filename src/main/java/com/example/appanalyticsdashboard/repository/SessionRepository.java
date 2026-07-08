package com.example.appanalyticsdashboard.repository;

import com.example.appanalyticsdashboard.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    @Query(value = "SELECT AVG(TIMESTAMPDIFF(MINUTE, start_time, end_time)) FROM sessions", nativeQuery = true)
    Double getAverageSessionDurationMinutes();
}