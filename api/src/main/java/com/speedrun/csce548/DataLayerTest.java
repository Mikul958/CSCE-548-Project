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
import java.util.List;

@Component
public class DataLayerTest implements CommandLineRunner
{
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

            // =================================================
            System.out.println("\nGrabbing all runs with game ID 3 and category 100%");
            System.out.println("Game name: " + gameRepository.findById(3).get().getTitle());
            List<Run> runs = runRepository.findByGame_IdAndCategoryOrderByTimeMillisecondsAsc(3, "100%");
            for (Run r : runs) {
                System.out.println(r.getId() + " | " + r.getAuthor().getDisplayName() + " | " + r.getCategory() + " | " + r.getTimeMilliseconds() + " | " + r.getSetDate());
            }

            System.out.println("\nGrabbing all runs with game ID 5 and category Any%");
            System.out.println("Game name: " + gameRepository.findById(5).get().getTitle());
            runs = runRepository.findByGame_IdAndCategoryOrderByTimeMillisecondsAsc(5, "Any%");
            for (Run r : runs) {
                System.out.println(r.getId() + " | " + r.getAuthor().getDisplayName() + " | " + r.getCategory() + " | " + r.getTimeMilliseconds() + " | " + r.getSetDate());
            }

            System.out.println("\nGrabbing all runs with author ID 1");
            System.out.println("Author username: " + authorRepository.findById(1).get().getUsername());
            runs = runRepository.findByAuthor_IdOrderBySetDateDesc(1);
            for (Run r : runs) {
                System.out.println(r.getId() + " | " + r.getAuthor().getDisplayName() + " | " + r.getCategory() + " | " + r.getTimeMilliseconds() + " | " + r.getSetDate());
            }

            System.out.println("\nGrabbing all existing categories for game with ID 3");
            System.out.println("Game name: " + gameRepository.findById(3).get().getTitle());
            List<String> categories = runRepository.findDistinctCategoriesByGameId(3);
            for (String category : categories) {
                System.out.println(category);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
