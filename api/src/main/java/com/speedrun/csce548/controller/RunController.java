package com.speedrun.csce548.controller;

import com.speedrun.csce548.models.RunRequest;
import com.speedrun.csce548.models.RunResponse;
import com.speedrun.csce548.service.RunService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/runs")
public class RunController
{
    private final RunService runService;

    public RunController(RunService runService) {
        this.runService = runService;
    }


    // CREATE ENDPOINTS
    @PostMapping
    public ResponseEntity<RunResponse> create(@RequestBody RunRequest run) {
        RunResponse addedRun = runService.addRun(run);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedRun);
    }

    // READ ENDPOINTS
    @GetMapping
    public ResponseEntity<List<RunResponse>> getAll() {
        List<RunResponse> retrievedRuns = runService.getAllRuns();
        return ResponseEntity.ok(retrievedRuns);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RunResponse> getById(@PathVariable Integer id) {
        RunResponse retrievedRun = runService.getRunById(id);
        return ResponseEntity.ok(retrievedRun);
    }

    // UPDATE ENDPOINTS
    @PutMapping("/{id}")
    public ResponseEntity<RunResponse> update(@PathVariable Integer id, @RequestBody RunRequest run) {
        RunResponse updatedRun = runService.updateRun(id, run);
        return ResponseEntity.ok(updatedRun);
    }

    // DELETE ENDPOINTS
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        runService.deleteRun(id);
        return ResponseEntity.noContent().build();
    }
}
