package com.speedrun.csce548.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/health")
public class HealthController
{
    // Server health check used on startup by Fly.io
    @GetMapping
    public ResponseEntity<String> checkHealth() {
        return ResponseEntity.ok("Service alive");
    }
}
