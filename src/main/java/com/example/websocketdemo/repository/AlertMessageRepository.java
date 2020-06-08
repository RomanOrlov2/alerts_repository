package com.example.websocketdemo.repository;

import com.example.websocketdemo.domain.AlertMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertMessageRepository extends JpaRepository<AlertMessage, Long> {
}
