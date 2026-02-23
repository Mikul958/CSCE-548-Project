package com.speedrun.csce548;

import com.speedrun.csce548.models.Author;
import com.speedrun.csce548.models.Game;
import com.speedrun.csce548.models.Run;
import com.speedrun.csce548.repository.AuthorRepository;
import com.speedrun.csce548.repository.GameRepository;
import com.speedrun.csce548.repository.RunRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
public class DataLayerTest implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final GameRepository gameRepository;
    private final RunRepository runRepository;

    public DataLayerTest(
        AuthorRepository authorRepository,
        GameRepository gameRepository,
        RunRepository runRepository
    ) {
        this.authorRepository = authorRepository;
        this.gameRepository = gameRepository;
        this.runRepository = runRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {

        try {

            System.out.println("=== INSERT TESTS ===");

            // ---------- INSERT AUTHOR ----------
            Author author = new Author(
                    "test_runner",
                    "Test Runner",
                    "hashed_password_here",
                    "https://example.com/pfp.png",
                    LocalDate.now()
            );

            author = authorRepository.save(author);
            System.out.println("Inserted Author: " + author.getUsername());

            // ---------- INSERT GAME ----------
            Game game = new Game(
                    "Test Speed Game",
                    "Game used for integration tests",
                    "https://example.com/game.png",
                    LocalDate.of(2020, 1, 1),
                    "Test Dev Studio",
                    "Test Publisher"
            );

            game = gameRepository.save(game);
            System.out.println("Inserted Game: " + game.getTitle());

            // ---------- INSERT RUN ----------
            Run run = new Run(
                    game,
                    author,
                    "Any%",
                    3600000,
                    "https://example.com/run.mp4",
                    LocalDate.now(),
                    true
            );

            run = runRepository.save(run);
            System.out.println("Inserted Run");


            // =================================================
            System.out.println("\n=== READ TESTS ===");

            System.out.println("--- Authors ---");
            for (Author a : authorRepository.findAll()) {
                System.out.println(a.getId() + " - " + a.getUsername());
            }

            System.out.println("\n--- Games ---");
            for (Game g : gameRepository.findAll()) {
                System.out.println(g.getId() + " - " + g.getTitle());
            }

            System.out.println("\n--- Runs ---");
            for (Run r : runRepository.findAll()) {
                System.out.println(
                        r.getId() +
                                " - GameID: " + r.getGame().getId() +
                                " AuthorID: " + r.getAuthor().getId() +
                                " Time(ms): " + r.getTimeMilliseconds()
                );
            }


            // =================================================
            System.out.println("\n=== UPDATE TESTS ===");

            // Update Author
            author.setDisplayName("Updated Runner Name");
            authorRepository.save(author);
            System.out.println("Updated Author ID: " + author.getId());

            // Update Game
            game.setDeveloper("Updated Dev Studio");
            gameRepository.save(game);
            System.out.println("Updated Game ID: " + game.getId());

            // Update Run
            run.setTimeMilliseconds(3500000);
            runRepository.save(run);
            System.out.println("Updated Run ID: " + run.getId());


            // =================================================
            System.out.println("\n=== DELETE TESTS ===");

            // Delete Run first (FK safety)
            runRepository.delete(run);
            System.out.println("Deleted Run ID: " + run.getId());

            gameRepository.delete(game);
            System.out.println("Deleted Game ID: " + game.getId());

            authorRepository.delete(author);
            System.out.println("Deleted Author ID: " + author.getId());


            // =================================================
            System.out.println("\n=== FINAL DB STATE ===");
            System.out.println("Authors: " + authorRepository.count());
            System.out.println("Games: " + gameRepository.count());
            System.out.println("Runs: " + runRepository.count());

            System.out.println("\nDATA LAYER TEST COMPLETED SUCCESSFULLY");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
