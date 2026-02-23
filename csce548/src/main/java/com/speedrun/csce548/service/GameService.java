package com.speedrun.csce548.service;

import com.speedrun.csce548.exceptions.EntryNotFoundException;
import com.speedrun.csce548.models.Game;
import com.speedrun.csce548.models.Run;
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
    public Game addGame(Game game) {
        if (game.getId() != null)
            throw new IllegalArgumentException("New game cannot already have an ID");
        return gameRepository.save(game);
    }

    /**
     * Gets all games in the database.
     * @return A list containing all existing games.
     */
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    /**
     * Gets the game with the given ID.
     * @param id Target ID of the game to retrieve.
     * @return The target game.
     */
    public Game getGameById(Integer id) {
        return gameRepository.findById(id)
            .orElseThrow(() -> new EntryNotFoundException("No game found with ID: " + id));
    }

    /**
     * Updates the game with the given ID with the provided data
     * @param id Target ID of the game to update.
     * @param updatedGame Game data to update the run in the database with.
     * @return The updated game.
     */
    public Game updateGame(Integer id, Game updatedGame) {
        Game foundGame = gameRepository.findById(id)
            .orElseThrow(() -> new EntryNotFoundException("No game found with ID: " + id));

        foundGame.setTitle(updatedGame.getTitle());
        foundGame.setDescription(updatedGame.getDescription());
        foundGame.setThumbnailUrl(updatedGame.getThumbnailUrl());
        foundGame.setReleaseDate(updatedGame.getReleaseDate());
        foundGame.setDeveloper(updatedGame.getDeveloper());
        foundGame.setPublisher(updatedGame.getPublisher());

        return gameRepository.save(foundGame);
    }

    /**
     * Deletes the game with the given ID from the database.
     * @param id Target ID to delete.
     */
    public void deleteGame(Integer id) {
        gameRepository.deleteById(id);
    }

    // Other specific logic

    /**
     * Retrieves a leaderboard containing a list of runs for a given game and category.
     * @param gameId The ID of the game to retrieve times from.
     * @param category The category of the game to retrieve times for.
     * @return A list of runs ordered from fastest to slowest.
     */
    public List<Run> getLeaderboardForGame(Integer gameId, String category) {
        return runRepository.findByGame_IdAndCategoryOrderByTimeMillisecondsAsc(gameId, category);
    }

    // DISTINCT CATEGORIES
    public List<String> getCategoriesForGame(Integer gameId) {
        return runRepository.findDistinctCategoriesByGameId(gameId);
    }
}
