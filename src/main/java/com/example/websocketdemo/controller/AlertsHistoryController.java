package com.example.websocketdemo.controller;

import com.example.websocketdemo.domain.AlertMessage;
import com.example.websocketdemo.repository.AlertMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/alerts")
@RequiredArgsConstructor
public class AlertsHistoryController {
    private final AlertMessageRepository alertMessageRepository;

    @GetMapping
    public List<AlertMessage> getAlertsHistory() {
        return alertMessageRepository.findAll();
    }
}
