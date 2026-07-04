package com.example.appanalyticsdashboard.repository;

import com.example.appanalyticsdashboard.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}