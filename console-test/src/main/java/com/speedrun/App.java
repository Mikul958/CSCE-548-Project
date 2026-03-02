package com.speedrun;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Temporary app that allows API endpoints to be executed via console tool.
 */
public class App
{
    private static final String BASE_URL = "https://speedrun-csce548.fly.dev";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        
        System.out.println("Tester started");
        
        while (true) {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. Authors");
            System.out.println("2. Games");
            System.out.println("3. Runs");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> authorsMenu();
                case 2 -> gamesMenu();
                case 3 -> runsMenu();
                case 4 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid option");
            }
        }
    }

    private static void authorsMenu() throws Exception {
        while (true) {
            System.out.println("\n--- AUTHORS ---");
            System.out.println("1. Create");
            System.out.println("2. Get All");
            System.out.println("3. Get By ID");
            System.out.println("4. Get History");
            System.out.println("5. Update");
            System.out.println("6. Delete");
            System.out.println("7. Back");
            System.out.print("Choose option: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> createAuthor();
                case 2 -> getAllAuthors();
                case 3 -> getAuthorById();
                case 4 -> getAuthorHistory();
                case 5 -> updateAuthor();
                case 6 -> deleteAuthor();
                case 7 -> { return; }
                default -> System.out.println("Invalid option");
            }
        }
    }

    private static void gamesMenu() throws Exception {
        while (true) {
            System.out.println("\n--- GAMES ---");
            System.out.println("1. Create");
            System.out.println("2. Get All");
            System.out.println("3. Get By ID");
            System.out.println("4. Get Categories");
            System.out.println("5. Get Leaderboard");
            System.out.println("6. Update");
            System.out.println("7. Delete");
            System.out.println("8. Back");
            System.out.print("Choose option: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> createGame();
                case 2 -> getAllGames();
                case 3 -> getGameById();
                case 4 -> getCategories();
                case 5 -> getLeaderboard();
                case 6 -> updateGame();
                case 7 -> deleteGame();
                case 8 -> { return; }
                default -> System.out.println("Invalid option");
            }
        }
    }

    private static void runsMenu() throws Exception {
        while (true) {
            System.out.println("\n--- RUNS ---");
            System.out.println("1. Create");
            System.out.println("2. Get All");
            System.out.println("3. Get By ID");
            System.out.println("4. Update Data");
            System.out.println("5. Update Links");
            System.out.println("6. Delete");
            System.out.println("7. Back");
            System.out.print("Choose option: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> createRun();
                case 2 -> getAllRuns();
                case 3 -> getRunById();
                case 4 -> updateRunData();
                case 5 -> updateRunLinks();
                case 6 -> deleteRun();
                case 7 -> { return; }
                default -> System.out.println("Invalid option");
            }
        }
    }

    // =========================
    // AUTHOR ENDPOINTS
    // =========================

    private static void createAuthor() throws Exception {
        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Display Name: ");
        String displayName = scanner.nextLine();

        System.out.print("Password Hash: ");
        String passwordHash = scanner.nextLine();

        System.out.print("Profile Picture URL (optional, press enter to skip): ");
        String profilePictureUrl = scanner.nextLine();

        System.out.print("Create Date (yyyy-MM-dd): ");
        String createDate = scanner.nextLine();

        String json = String.format("""
            {
                "username": "%s",
                "displayName": "%s",
                "passwordHash": "%s",
                "profilePictureUrl": %s,
                "createDate": "%s"
            }
            """,
            username,
            displayName,
            passwordHash,
            profilePictureUrl.isBlank() ? "null" : "\"" + profilePictureUrl + "\"",
            createDate
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/authors"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Status: " + response.statusCode());
        System.out.println("Response: " + response.body());
    }

    private static void getAllAuthors() throws Exception {
        sendGet("/authors");
    }

    private static void getAuthorById() throws Exception {
        System.out.print("Enter author ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        sendGet("/authors/" + id);
    }

    private static void getAuthorHistory() throws Exception {
        System.out.print("Enter author ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        sendGet("/authors/" + id + "/history");
    }

    private static void updateAuthor() throws Exception {
        System.out.print("Author ID to update: ");
        Integer id = Integer.parseInt(scanner.nextLine());

        System.out.print("New Username: ");
        String username = scanner.nextLine();

        System.out.print("New Display Name: ");
        String displayName = scanner.nextLine();

        System.out.print("New Password Hash: ");
        String passwordHash = scanner.nextLine();

        System.out.print("New Profile Picture URL (optional, press enter to skip): ");
        String profilePictureUrl = scanner.nextLine();

        System.out.print("New Create Date (yyyy-MM-dd): ");
        String createDate = scanner.nextLine();

        String json = String.format("""
            {
                "username": "%s",
                "displayName": "%s",
                "passwordHash": "%s",
                "profilePictureUrl": %s,
                "createDate": "%s"
            }
            """,
            username,
            displayName,
            passwordHash,
            profilePictureUrl.isBlank() ? "null" : "\"" + profilePictureUrl + "\"",
            createDate
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/authors/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Status: " + response.statusCode());
        System.out.println("Response: " + response.body());
    }

    private static void deleteAuthor() throws Exception {
        System.out.print("Enter author ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine());
        sendDelete("/authors/" + id);
    }

    // =========================
    // GAME ENDPOINTS
    // =========================

    private static void createGame() throws Exception {
        System.out.print("Title: ");
        String title = scanner.nextLine();

        System.out.print("Description (optional): ");
        String description = scanner.nextLine();

        System.out.print("Thumbnail URL (optional): ");
        String thumbnailUrl = scanner.nextLine();

        System.out.print("Release Date (yyyy-MM-dd, optional): ");
        String releaseDate = scanner.nextLine();

        System.out.print("Developer (optional): ");
        String developer = scanner.nextLine();

        System.out.print("Publisher (optional): ");
        String publisher = scanner.nextLine();

        String json = String.format("""
            {
                "title": "%s",
                "description": %s,
                "thumbnailUrl": %s,
                "releaseDate": %s,
                "developer": %s,
                "publisher": %s
            }
            """,
            title,
            description.isBlank() ? "null" : "\"" + description + "\"",
            thumbnailUrl.isBlank() ? "null" : "\"" + thumbnailUrl + "\"",
            releaseDate.isBlank() ? "null" : "\"" + releaseDate + "\"",
            developer.isBlank() ? "null" : "\"" + developer + "\"",
            publisher.isBlank() ? "null" : "\"" + publisher + "\""
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/games"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Status: " + response.statusCode());
        System.out.println("Response: " + response.body());
    }

    private static void getAllGames() throws Exception {
        sendGet("/games");
    }

    private static void getGameById() throws Exception {
        System.out.print("Enter game ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        sendGet("/games/" + id);
    }

    private static void getCategories() throws Exception {
        System.out.print("Enter game ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        sendGet("/games/" + id + "/categories");
    }

    private static void getLeaderboard() throws Exception {
        System.out.print("Game ID: ");
        Integer gameId = Integer.parseInt(scanner.nextLine());

        System.out.print("Category: ");
        String category = scanner.nextLine();

        // Encode the category to handle spaces and special characters
        String encodedCategory = URLEncoder.encode(category, StandardCharsets.UTF_8);

        String url = BASE_URL + "/games/leaderboard/" + gameId + "?category=" + encodedCategory;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Status: " + response.statusCode());
        System.out.println("Response: " + response.body());
    }

    private static void updateGame() throws Exception {
        System.out.print("Game ID to update: ");
        Integer id = Integer.parseInt(scanner.nextLine());

        System.out.print("New Title: ");
        String title = scanner.nextLine();

        System.out.print("New Description (optional): ");
        String description = scanner.nextLine();

        System.out.print("New Thumbnail URL (optional): ");
        String thumbnailUrl = scanner.nextLine();

        System.out.print("New Release Date (yyyy-MM-dd, optional): ");
        String releaseDate = scanner.nextLine();

        System.out.print("New Developer (optional): ");
        String developer = scanner.nextLine();

        System.out.print("New Publisher (optional): ");
        String publisher = scanner.nextLine();

        String json = String.format("""
            {
                "title": "%s",
                "description": %s,
                "thumbnailUrl": %s,
                "releaseDate": %s,
                "developer": %s,
                "publisher": %s
            }
            """,
            title,
            description.isBlank() ? "null" : "\"" + description + "\"",
            thumbnailUrl.isBlank() ? "null" : "\"" + thumbnailUrl + "\"",
            releaseDate.isBlank() ? "null" : "\"" + releaseDate + "\"",
            developer.isBlank() ? "null" : "\"" + developer + "\"",
            publisher.isBlank() ? "null" : "\"" + publisher + "\""
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/games/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Status: " + response.statusCode());
        System.out.println("Response: " + response.body());
    }

    private static void deleteGame() throws Exception {
        System.out.print("Enter game ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        sendDelete("/games/" + id);
    }

    // =========================
    // RUN ENDPOINTS
    // =========================

    private static void createRun() throws Exception {
        System.out.print("Game ID: ");
        Integer gameId = Integer.parseInt(scanner.nextLine());

        System.out.print("Author ID: ");
        Integer authorId = Integer.parseInt(scanner.nextLine());

        System.out.print("Category: ");
        String category = scanner.nextLine();

        System.out.print("Time (milliseconds): ");
        Integer timeMilliseconds = Integer.parseInt(scanner.nextLine());

        System.out.print("Video URL (optional): ");
        String videoUrl = scanner.nextLine();

        System.out.print("Set Date (yyyy-MM-dd): ");
        String setDate = scanner.nextLine();

        System.out.print("Verified (true/false): ");
        Boolean verified = Boolean.parseBoolean(scanner.nextLine());

        String json = String.format("""
            {
                "category": "%s",
                "timeMilliseconds": %d,
                "videoUrl": %s,
                "setDate": "%s",
                "verified": %s
            }
            """,
            category,
            timeMilliseconds,
            videoUrl.isBlank() ? "null" : "\"" + videoUrl + "\"",
            setDate,
            verified
        );

        String url = BASE_URL + "/runs?gameId=" + gameId + "&authorId=" + authorId;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Status: " + response.statusCode());
        System.out.println("Response: " + response.body());
    }

    private static void getAllRuns() throws Exception {
        sendGet("/runs");
    }

    private static void getRunById() throws Exception {
        System.out.print("Enter run ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        sendGet("/runs/" + id);
    }

    private static void updateRunData() throws Exception {
        System.out.print("Run ID to update: ");
        Integer id = Integer.parseInt(scanner.nextLine());

        System.out.print("New Category: ");
        String category = scanner.nextLine();

        System.out.print("New Time (milliseconds): ");
        Integer timeMilliseconds = Integer.parseInt(scanner.nextLine());

        System.out.print("New Set Date (yyyy-MM-dd): ");
        String setDate = scanner.nextLine();

        System.out.print("Verified (true/false): ");
        Boolean verified = Boolean.parseBoolean(scanner.nextLine());

        String json = String.format("""
            {
                "category": "%s",
                "timeMilliseconds": %d,
                "setDate": "%s",
                "verified": %s
            }
            """,
            category,
            timeMilliseconds,
            setDate,
            verified
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/runs/" + id + "/data"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Status: " + response.statusCode());
        System.out.println("Response: " + response.body());
    }

    private static void updateRunLinks() throws Exception {
        System.out.print("Run ID to update: ");
        Integer id = Integer.parseInt(scanner.nextLine());

        System.out.print("New Game ID: ");
        Integer gameId = Integer.parseInt(scanner.nextLine());

        System.out.print("New Author ID: ");
        Integer authorId = Integer.parseInt(scanner.nextLine());

        String url = BASE_URL + "/runs/" + id +
                    "/links?gameId=" + gameId +
                    "&authorId=" + authorId;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Status: " + response.statusCode());
        System.out.println("Response: " + response.body());
    }

    private static void deleteRun() throws Exception {
        System.out.print("Enter run ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        sendDelete("/runs/" + id);
    }

    // =========================
    // GENERIC REQUEST METHODS
    // =========================

    private static void sendGet(String path) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + path))
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        printResponse(response);
    }

    private static void sendPost(String path, String json) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + path))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        printResponse(response);
    }

    private static void sendPut(String path, String json) throws Exception {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + path));

        if (json != null && !json.isEmpty()) {
            builder.header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json));
        } else {
            builder.PUT(HttpRequest.BodyPublishers.noBody());
        }

        HttpResponse<String> response =
                client.send(builder.build(), HttpResponse.BodyHandlers.ofString());

        printResponse(response);
    }

    private static void sendDelete(String path) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + path))
                .DELETE()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        printResponse(response);
    }

    private static void printResponse(HttpResponse<String> response) {
        System.out.println("Status: " + response.statusCode());
        System.out.println("Body:");
        System.out.println(response.body());
    }
}
