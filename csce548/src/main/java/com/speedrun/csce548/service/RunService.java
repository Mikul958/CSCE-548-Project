package com.speedrun.csce548.service;

import com.speedrun.csce548.models.Author;
import com.speedrun.csce548.models.Game;
import com.speedrun.csce548.models.Run;
import com.speedrun.csce548.repository.AuthorRepository;
import com.speedrun.csce548.repository.GameRepository;
import com.speedrun.csce548.repository.RunRepository;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RunService
{
    private final AuthorRepository authorRepository;
    private final GameRepository gameRepository;
    private final RunRepository runRepository;

    public RunService(
            RunRepository runRepository,
            GameRepository gameRepository,
            AuthorRepository authorRepository
    ) {
        this.runRepository = runRepository;
        this.gameRepository = gameRepository;
        this.authorRepository = authorRepository;
    }

    /**
     * Creates a run with the provided data, as well as the IDs of the linked game and author
     * @param run Run data to save.
     * @param gameId ID of the game the run was set for. Cannot be null and must exist.
     * @param authorId ID of the author that set the run. Cannot be null and must exist.
     * @return The created run as it appears in the database.
     */
    public Run create(Run run, Integer gameId, Integer authorId) {
        Game foundGame = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found: " + gameId));
        Author foundAuthor = authorRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found: " + authorId));
        run.setGame(foundGame);
        run.setAuthor(foundAuthor);

        return runRepository.save(run);
    }

    /**
     * Gets all runs in the database.
     * @return A list containing all existing runs.
     */
    public List<Run> getAll() {
        return runRepository.findAll();
    }

    /**
     * Gets the run with the given ID.
     * @param id Target ID of the run to retrieve.
     * @return The target run.
     */
    public Run getById(Integer id) {
        return runRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No run found with ID: " + id));
    }

    /**
     * Updates the run with the given ID with the provided data. Does not change the linked game or author.
     * @param id Target ID of the run to update.
     * @param updatedRun Run data to update the run in the database with.
     * @return The updated run.
     */
    public Run update(Integer id, Run updatedRun) {
        Run foundRun = runRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No run found with ID: " + id));

        foundRun.setCategory(updatedRun.getCategory());
        foundRun.setTimeMilliseconds(updatedRun.getTimeMilliseconds());
        foundRun.setVideoUrl(updatedRun.getVideoUrl());
        foundRun.setSetDate(updatedRun.getSetDate());
        foundRun.setVerified(updatedRun.getVerified());

        return runRepository.save(foundRun);
    }

    /**
     * Updates the run with the given ID with the provided game and author. Does not alter any other run data.
     * @param id Target ID of the run to update.
     * @param gameId Game ID to link the run in the database to. Cannot be null and must exist.
     * @param authorId Author ID to link the run in the database to. Cannot be null and must exist.
     * @return The updated run.
     */
    public Run updateLinks(Integer id, Integer gameId, Integer authorId){
        Run foundRun = runRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No run found with ID: " + id));

        Game foundGame = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found: " + gameId));
        Author foundAuthor = authorRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found: " + authorId));
        foundRun.setGame(foundGame);
        foundRun.setAuthor(foundAuthor);

        return runRepository.save(foundRun);
    }

    /**
     * Deletes the run with the given ID from the database.
     * @param id Target ID to delete.
     */
    public void delete(Integer id) {
        runRepository.deleteById(id);
    }
}
