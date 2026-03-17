package com.speedrun.csce548.service;

import com.speedrun.csce548.exceptions.EntryNotFoundException;
import com.speedrun.csce548.models.Author;
import com.speedrun.csce548.models.Game;
import com.speedrun.csce548.models.Run;
import com.speedrun.csce548.models.RunRequest;
import com.speedrun.csce548.models.RunResponse;
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
    public RunResponse addRun(RunRequest run) {
        Game foundGame = gameRepository.findById(run.getGameId())
                .orElseThrow(() -> new EntryNotFoundException("Game not found: " + run.getGameId()));
        Author foundAuthor = authorRepository.findById(run.getAuthorId())
                .orElseThrow(() -> new EntryNotFoundException("Author not found: " + run.getAuthorId()));
        
        Run runEntity = run.fromRequest(foundGame, foundAuthor);
        Run savedRun = runRepository.save(runEntity);
        return new RunResponse(savedRun);
    }

    /**
     * Gets all runs in the database.
     * @return A list containing all existing runs.
     */
    public List<RunResponse> getAllRuns() {
        List<Run> runEntities = runRepository.findAllByOrderBySetDateDesc();
        return runEntities.stream().map(RunResponse::new).toList();
    }

    /**
     * Gets the run with the given ID.
     * @param id Target ID of the run to retrieve.
     * @return The target run.
     */
    public RunResponse getRunById(Integer id) {
        Run foundRun = runRepository.findById(id)
            .orElseThrow(() -> new EntryNotFoundException("No run found with ID: " + id));
        return new RunResponse(foundRun);
    }

    /**
     * Updates the run with the given ID with the provided data.
     * @param id Target ID of the run to update.
     * @param updatedRun Run data to update the run in the database with, including a gameId and authorId to update references with.
     * @return The updated run.
     */
    public RunResponse updateRun(Integer id, RunRequest updatedRun) {
        Run foundRun = runRepository.findById(id)
            .orElseThrow(() -> new EntryNotFoundException("No run found with ID: " + id));
        Game foundGame = gameRepository.findById(updatedRun.getGameId())
                .orElseThrow(() -> new EntryNotFoundException("Game not found: " + updatedRun.getGameId()));
        Author foundAuthor = authorRepository.findById(updatedRun.getAuthorId())
                .orElseThrow(() -> new EntryNotFoundException("Author not found: " + updatedRun.getAuthorId()));

        foundRun = updatedRun.fromRequest(foundGame, foundAuthor);
        foundRun.setId(id);
        Run savedRun = runRepository.save(foundRun);
        return new RunResponse(savedRun);
    }

    /**
     * Deletes the run with the given ID from the database.
     * @param id Target ID to delete.
     */
    public void deleteRun(Integer id) {
        runRepository.findById(id)
            .orElseThrow(() -> new EntryNotFoundException("No run found with ID: " + id));
        runRepository.deleteById(id);
    }
}
