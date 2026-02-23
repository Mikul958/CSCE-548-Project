package com.speedrun.csce548.controller;

import com.speedrun.csce548.models.Game;
import com.speedrun.csce548.service.GameService;
import com.speedrun.csce548.models.Run;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }


    // CREATE ENDPOINTS
    @PostMapping
    public Game create(@RequestBody Game game) {
        return gameService.addGame(game);
    }

    // READ ENDPOINTS
    @GetMapping
    public List<Game> getAll() {
        return gameService.getAllGames();
    }

    @GetMapping("/{id}")
    public Game getById(@PathVariable Integer id) {
        return gameService.getGameById(id);
    }

    @GetMapping("/{id}/categories")
    public List<String> getCategories(@PathVariable Integer id) {
        return gameService.getCategoriesForGame(id);
    }

    @GetMapping("/{id}/leaderboard")
    public List<Run> getLeaderboard(@PathVariable Integer id, @RequestParam String category) {
        return gameService.getLeaderboardForGame(id, category);
    }

    // UPDATE ENDPOINTS
    @PutMapping("/{id}")
    public Game update(@PathVariable Integer id, @RequestBody Game game) {
        return gameService.updateGame(id, game);
    }

    // DELETE ENDPOINTS
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        gameService.deleteGame(id);
    }
}
