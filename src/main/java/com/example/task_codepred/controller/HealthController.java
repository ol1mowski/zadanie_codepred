package com.example.task_codepred.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/actuator")
public class HealthController {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> healthInfo = new HashMap<>();
        healthInfo.put("status", "UP");
        healthInfo.put("application", "Aplikacja Ogłoszeniowa");
        healthInfo.put("version", "1.0.0");
        healthInfo.put("timestamp", LocalDateTime.now().format(formatter));
        healthInfo.put("environment", "Development");
        healthInfo.put("message", "Aplikacja jest w pełni operacyjna i gotowa do obsługi ogłoszeń");
        
        return ResponseEntity.ok(healthInfo);
    }
}
