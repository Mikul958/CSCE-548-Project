package com.speedrun.csce548.controller;

import com.speedrun.csce548.models.Run;
import com.speedrun.csce548.service.RunService;

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
    public Run create(@RequestParam Integer gameId, @RequestParam Integer authorId, @RequestBody Run run) {
        return runService.addRun(run, gameId, authorId);
    }

    // READ ENDPOINTS
    @GetMapping
    public List<Run> getAll() {
        return runService.getAllRuns();
    }

    @GetMapping("/{id}")
    public Run getById(@PathVariable Integer id) {
        return runService.getRunById(id);
    }

    // UPDATE ENDPOINTS
    @PutMapping("/{id}/data")
    public Run update(@PathVariable Integer id, @RequestBody Run run) {
        return runService.updateRun(id, run);
    }

    @PutMapping("/{id}/links")
    public Run update(@PathVariable Integer id, @RequestParam Integer gameId, @RequestParam Integer authorId) {
        return runService.updateRunLinks(id, gameId, authorId);
    }

    // DELETE ENDPOINTS
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        runService.deleteRun(id);
    }
}
