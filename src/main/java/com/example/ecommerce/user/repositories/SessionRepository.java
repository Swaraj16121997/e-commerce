package com.example.ecommerce.user.repositories;

import com.example.ecommerce.user.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {

    Optional<Session> findByTokenAndUser_Id(String token, Long userId);
    Optional<Session> findByToken(String token);
}
