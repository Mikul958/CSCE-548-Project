package com.speedrun.csce548.controller;

import com.speedrun.csce548.models.GameRequest;
import com.speedrun.csce548.models.GameResponse;
import com.speedrun.csce548.service.GameService;
import com.speedrun.csce548.models.RunResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


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
    public ResponseEntity<GameResponse> create(@RequestBody GameRequest game) {
        GameResponse addedGame = gameService.addGame(game);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedGame);
    }

    // READ ENDPOINTS
    @GetMapping
    public ResponseEntity<List<GameResponse>> getAll() {
        List<GameResponse> retrievedGames = gameService.getAllGames();
        return ResponseEntity.ok(retrievedGames);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameResponse> getById(@PathVariable Integer id) {
        GameResponse retrievedGame = gameService.getGameById(id);
        return ResponseEntity.ok(retrievedGame);
    }

    @GetMapping("/{id}/categories")
    public ResponseEntity<List<String>> getCategories(@PathVariable Integer id) {
        List<String> availableCategories = gameService.getCategoriesForGame(id);
        return ResponseEntity.ok(availableCategories);
    }

    @GetMapping("/{id}/leaderboard")
    public ResponseEntity<List<RunResponse>> getLeaderboard(@PathVariable Integer id, @RequestParam String category) {
        List<RunResponse> leaderboard = gameService.getLeaderboardForGame(id, category);
        return ResponseEntity.ok(leaderboard);
    }

    // UPDATE ENDPOINTS
    @PutMapping("/{id}")
    public ResponseEntity<GameResponse> update(@PathVariable Integer id, @RequestBody GameRequest game) {
        GameResponse updatedGame = gameService.updateGame(id, game);
        return ResponseEntity.ok(updatedGame);
    }

    // DELETE ENDPOINTS
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        gameService.deleteGame(id);
        return ResponseEntity.noContent().build();
    }
}
