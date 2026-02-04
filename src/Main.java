import data.AuthorDAO;
import data.GameDAO;
import data.RunDAO;
import models.Author;
import models.Game;
import models.Run;

import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        try {
            AuthorDAO authorDAO = new AuthorDAO();
            GameDAO gameDAO = new GameDAO();
            RunDAO runDAO = new RunDAO();

            System.out.println("=== INSERT TESTS ===");

            // ---------- INSERT AUTHOR ----------
            Author author = new Author(
                    0,
                    "test_runner",
                    "Test Runner",
                    "hashed_password_here",
                    "https://example.com/pfp.png",
                    LocalDate.now()
            );

            authorDAO.insert(author);
            System.out.println("Inserted Author: " + author.getUsername());

            // Fetch inserted author reference
            List<Author> authors = authorDAO.getAll();
            Author authorRef = authors.get(authors.size() - 1);


            // ---------- INSERT GAME ----------
            Game game = new Game(
                    0,
                    "Test Speed Game",
                    "Game used for integration tests",
                    "https://example.com/game.png",
                    LocalDate.of(2020, 1, 1),
                    "Test Dev Studio",
                    "Test Publisher"
            );

            gameDAO.insert(game);
            System.out.println("Inserted Game: " + game.getTitle());

            // Fetch inserted game reference
            List<Game> games = gameDAO.getAll();
            Game gameRef = games.get(games.size() - 1);


            // ---------- INSERT RUN ----------
            Run run = new Run(
                    0,
                    gameRef,
                    authorRef,
                    "Any%",
                    3600000, // 1 hour
                    "https://example.com/run.mp4",
                    LocalDate.now(),
                    true
            );

            runDAO.insert(run);
            System.out.println("Inserted Run");


            // =================================================
            System.out.println("\n=== READ TESTS ===");

            System.out.println("--- Authors ---");
            for (Author a : authorDAO.getAll()) {
                System.out.println(a.getId() + " - " + a.getUsername());
            }

            System.out.println("\n--- Games ---");
            for (Game g : gameDAO.getAll()) {
                System.out.println(g.getId() + " - " + g.getTitle());
            }

            System.out.println("\n--- Runs ---");
            for (Run r : runDAO.getAll()) {
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
            authorRef.setDisplayName("Updated Runner Name");
            authorDAO.update(authorRef);
            System.out.println("Updated Author ID: " + authorRef.getId());

            // Update Game
            gameRef.setDeveloper("Updated Dev Studio");
            gameDAO.update(gameRef);
            System.out.println("Updated Game ID: " + gameRef.getId());

            // Update Run
            List<Run> runs = runDAO.getAll();
            Run runRef = runs.get(runs.size() - 1);

            runRef.setTimeMilliseconds(3500000); // Faster run
            runDAO.update(runRef);
            System.out.println("Updated Run ID: " + runRef.getId());


            // =================================================
            System.out.println("\n=== DELETE TESTS ===");

            // Delete Run first (FK safety)
            runDAO.delete(runRef.getId());
            System.out.println("Deleted Run ID: " + runRef.getId());

            // Delete Game
            gameDAO.delete(gameRef.getId());
            System.out.println("Deleted Game ID: " + gameRef.getId());

            // Delete Author
            authorDAO.delete(authorRef.getId());
            System.out.println("Deleted Author ID: " + authorRef.getId());


            // =================================================
            System.out.println("\n=== FINAL DB STATE ===");
            System.out.println("Authors: " + authorDAO.getAll().size());
            System.out.println("Games: " + gameDAO.getAll().size());
            System.out.println("Runs: " + runDAO.getAll().size());

            System.out.println("\nDATA LAYER TEST COMPLETED SUCCESSFULLY");

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
