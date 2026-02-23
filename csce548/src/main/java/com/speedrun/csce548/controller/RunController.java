package com.speedrun.csce548.controller;

import com.speedrun.csce548.models.Run;
import com.speedrun.csce548.service.RunService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/runs")
public class RunController {

    private final RunService runService;

    public RunController(RunService runService) {
        this.runService = runService;
    }

    
    // CREATE ENDPOINTS
    @PostMapping
    public ResponseEntity<Run> create(@RequestParam Integer gameId, @RequestParam Integer authorId, @RequestBody Run run) {
        Run addedRun = runService.addRun(run, gameId, authorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedRun);
    }

    // READ ENDPOINTS
    @GetMapping
    public ResponseEntity<List<Run>> getAll() {
        List<Run> retrievedRuns = runService.getAllRuns();
        return ResponseEntity.ok(retrievedRuns);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Run> getById(@PathVariable Integer id) {
        Run retrievedRun = runService.getRunById(id);
        return ResponseEntity.ok(retrievedRun);
    }

    // UPDATE ENDPOINTS
    @PutMapping("/{id}/data")
    public ResponseEntity<Run> update(@PathVariable Integer id, @RequestBody Run run) {
        Run updatedRun = runService.updateRun(id, run);
        return ResponseEntity.ok(updatedRun);
    }

    @PutMapping("/{id}/links")
    public ResponseEntity<Run> update(@PathVariable Integer id, @RequestParam Integer gameId, @RequestParam Integer authorId) {
        Run updatedRun = runService.updateRunLinks(id, gameId, authorId);
        return ResponseEntity.ok(updatedRun);
    }

    // DELETE ENDPOINTS
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        runService.deleteRun(id);
        return ResponseEntity.noContent().build();
    }
}
