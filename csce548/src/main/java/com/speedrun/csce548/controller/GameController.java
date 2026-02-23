package com.speedrun.csce548.controller;

import com.speedrun.csce548.models.Game;
import com.speedrun.csce548.service.GameService;
import com.speedrun.csce548.models.Run;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/games")
public class GameController
{
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }


    // CREATE ENDPOINTS
    @PostMapping
    public ResponseEntity<Game> create(@RequestBody Game game) {
        Game addedGame = gameService.addGame(game);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedGame);
    }

    // READ ENDPOINTS
    @GetMapping
    public ResponseEntity<List<Game>> getAll() {
        List<Game> retrievedGames = gameService.getAllGames();
        return ResponseEntity.ok(retrievedGames);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> getById(@PathVariable Integer id) {
        Game retrievedGame = gameService.getGameById(id);
        return ResponseEntity.ok(retrievedGame);
    }

    @GetMapping("/{id}/categories")
    public ResponseEntity<List<String>> getCategories(@PathVariable Integer id) {
        List<String> availableCategories = gameService.getCategoriesForGame(id);
        return ResponseEntity.ok(availableCategories);
    }

    @GetMapping("/{id}/leaderboard")
    public ResponseEntity<List<Run>> getLeaderboard(@PathVariable Integer id, @RequestParam String category) {
        List<Run> leaderboard = gameService.getLeaderboardForGame(id, category);
        return ResponseEntity.ok(leaderboard);
    }

    // UPDATE ENDPOINTS
    @PutMapping("/{id}")
    public ResponseEntity<Game> update(@PathVariable Integer id, @RequestBody Game game) {
        Game updatedGame = gameService.updateGame(id, game);
        return ResponseEntity.ok(updatedGame);
    }

    // DELETE ENDPOINTS
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        gameService.deleteGame(id);
        return ResponseEntity.noContent().build();
    }
}
