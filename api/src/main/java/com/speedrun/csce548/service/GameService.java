package com.speedrun.csce548.service;

import com.speedrun.csce548.exceptions.EntryNotFoundException;
import com.speedrun.csce548.models.Game;
import com.speedrun.csce548.models.GameRequest;
import com.speedrun.csce548.models.GameResponse;
import com.speedrun.csce548.models.Run;
import com.speedrun.csce548.models.RunResponse;
import com.speedrun.csce548.repository.GameRepository;
import com.speedrun.csce548.repository.RunRepository;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class GameService
{
    private final GameRepository gameRepository;
    private final RunRepository runRepository;

    public GameService(GameRepository gameRepository, RunRepository runRepository) {
        this.gameRepository = gameRepository;
        this.runRepository = runRepository;
    }

    // CRUD operations

    /**
     * Creates a new game with the given data.
     * @param game Data for the new game.
     * @return The created game as it appears in the database.
     */
    public GameResponse addGame(GameRequest game) {
        Game gameEntity = game.fromRequest();
        Game savedGame = gameRepository.save(gameEntity);
        return new GameResponse(savedGame);
    }

    /**
     * Gets all games in the database.
     * @return A list containing all existing games.
     */
    public List<GameResponse> getAllGames() {
        List<Game> gameEntities = gameRepository.findAll();
        return gameEntities.stream().map(GameResponse::new).toList();
    }

    /**
     * Gets the game with the given ID.
     * @param id Target ID of the game to retrieve.
     * @return The target game.
     */
    public GameResponse getGameById(Integer id) {
        Game foundGame = gameRepository.findById(id)
            .orElseThrow(() -> new EntryNotFoundException("No game found with ID: " + id));
        return new GameResponse(foundGame);
    }

    /**
     * Updates the game with the given ID with the provided data
     * @param id Target ID of the game to update.
     * @param updatedGame Game data to update the run in the database with.
     * @return The updated game.
     */
    public GameResponse updateGame(Integer id, GameRequest updatedGame) {
        Game foundGame = gameRepository.findById(id)
            .orElseThrow(() -> new EntryNotFoundException("No game found with ID: " + id));

        foundGame = updatedGame.fromRequest();
        foundGame.setId(id);
        Game savedGame = gameRepository.save(foundGame);
        return new GameResponse(savedGame);
    }

    /**
     * Deletes the game with the given ID from the database.
     * @param id Target ID to delete.
     */
    public void deleteGame(Integer id) {
        gameRepository.findById(id)
            .orElseThrow(() -> new EntryNotFoundException("No game found with ID: " + id));
        gameRepository.deleteById(id);
    }

    // Other specific logic

    /**
     * Retrieves a leaderboard containing a list of runs for a given game and category.
     * @param gameId The ID of the game to retrieve times from.
     * @param category The category of the game to retrieve times for.
     * @return A list of runs ordered from fastest to slowest.
     */
    public List<RunResponse> getLeaderboardForGame(Integer gameId, String category) {
        List<Run> rawLeaderboard = runRepository.findByGame_IdAndCategoryOrderByTimeMillisecondsAsc(gameId, category);
        return rawLeaderboard.stream().map(RunResponse::new).toList();
    }

    /**
     * Retrieved a list of existing categories for a given game.
     * @param gameId The ID of the game to retrieve categories for.
     * @return A list of categories represented as raw strings.
     */
    public List<String> getCategoriesForGame(Integer gameId) {
        return runRepository.findDistinctCategoriesByGameId(gameId);
    }
}
