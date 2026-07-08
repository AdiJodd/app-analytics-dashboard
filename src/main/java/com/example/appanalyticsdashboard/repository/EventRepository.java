package com.example.appanalyticsdashboard.repository;

import com.example.appanalyticsdashboard.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("SELECT e.eventType, COUNT(e) FROM Event e GROUP BY e.eventType")
    List<Object[]> countEventsByType();

    @Query("SELECT s.screenName, COUNT(e) FROM Event e JOIN e.screen s GROUP BY s.screenName")
    List<Object[]> countEventsByScreen();

    @Query(value =
            "SELECT DATE(e.timestamp) as event_date, COUNT(DISTINCT s.user_id) as active_users " +
                    "FROM events e JOIN sessions s ON e.session_id = s.id " +
                    "GROUP BY DATE(e.timestamp) ORDER BY DATE(e.timestamp)",
            nativeQuery = true)
    List<Object[]> getDailyActiveUsers();
}