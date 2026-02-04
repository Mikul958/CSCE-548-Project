package data;

import models.Game;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GameDAO
{
    public List<Game> getAll() throws Exception {
        List<Game> games = new ArrayList<>();
        String sql = "SELECT * FROM Game";

        try (Connection connection = DatabaseUtil.getConnection(); Statement statement = connection.createStatement(); ResultSet results = statement.executeQuery(sql)) {
            while (results.next()) {
                games.add(new Game(
                    results.getInt("id"),
                    results.getString("title"),
                    results.getString("description"),
                    results.getString("thumbnail_url"),
                    results.getObject("release_date", LocalDate.class),
                    results.getString("developer"),
                    results.getString("publisher")
                ));
            }
        }
        return games;
    }

    public void insert(Game game) throws Exception {
        String sql = "INSERT INTO Game (id, title, description, thumbnail_url, release_date, developer, publisher) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, game.getId());
            statement.setString(2, game.getTitle());
            statement.setString(3, game.getDescription());
            statement.setString(4, game.getThumbnailUrl());
            statement.setObject(5, game.getReleaseDate(), Types.DATE);
            statement.setString(6, game.getDeveloper());
            statement.setString(7, game.getPublisher());
            statement.executeUpdate();
        }
    }

    public void update(Game game) throws Exception {
		String sql = "UPDATE Game SET title=?, description=?, thumbnail_url=?, release_date=?, developer=?, publisher=? WHERE id=?";
		try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, game.getTitle());
            statement.setString(2, game.getDescription());
            statement.setString(3, game.getThumbnailUrl());
            statement.setObject(4, game.getReleaseDate(), Types.DATE);
            statement.setString(5, game.getDeveloper());
            statement.setString(6, game.getPublisher());
            statement.setInt(7, game.getId());
			statement.executeUpdate();
		}
	}
	
	public void delete(int gameId) throws Exception {
		String sql = "DELETE FROM Game WHERE id=?";
		try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, gameId);
			statement.executeUpdate();
		}
	}
}
