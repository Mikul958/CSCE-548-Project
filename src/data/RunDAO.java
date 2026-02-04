package data;

import models.Author;
import models.Game;
import models.Run;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RunDAO
{
    public List<Run> getAll() throws Exception {
        List<Run> runs = new ArrayList<>();
        String sql = """
            SELECT
                r.id AS run_id,
                r.category,
                r.time_milliseconds,
                r.video_url,
                r.set_date,
                r.verified,

                g.id AS game_id,
                g.title,
                g.description,
                g.thumbnail_url,
                g.release_date,
                g.developer,
                g.publisher,

                a.id AS author_id,
                a.username,
                a.display_name,
                a.password_hash,
                a.profile_picture_url,
                a.create_date

            FROM Run r
            LEFT JOIN Game g ON r.game_id = g.id
            LEFT JOIN Author a ON r.author_id = a.id
        """;

        try (Connection connection = DatabaseUtil.getConnection(); Statement statement = connection.createStatement(); ResultSet results = statement.executeQuery(sql)) {
            while (results.next()) {
                Game game = null;
                if (results.getObject("game_id") != null) {
                    game = new Game(
                        results.getInt("game_id"),
                        results.getString("title"),
                        results.getString("description"),
                        results.getString("thumbnail_url"),
                        results.getObject("release_date", LocalDate.class),
                        results.getString("developer"),
                        results.getString("publisher")
                    );
                }

                Author author = null;
                if (results.getObject("author_id") != null) {
                    author = new Author(
                        results.getInt("author_id"),
                        results.getString("username"),
                        results.getString("display_name"),
                        results.getString("password_hash"),
                        results.getString("profile_picture_url"),
                        results.getObject("create_date", LocalDate.class)
                    );
                }

                Run run = new Run(
                    results.getInt("run_id"),
                    game,
                    author,
                    results.getString("category"),
                    results.getInt("time_milliseconds"),
                    results.getString("video_url"),
                    results.getObject("set_date", LocalDate.class),
                    results.getBoolean("verified")
                );
                runs.add(run);
            }
        }
        return runs;
    }

    public void insert(Run run) throws Exception {
        String sql = "INSERT INTO Run (id, game_id, author_id, category, time_milliseconds, video_url, set_date, verified) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, run.getId());
            statement.setInt(2, run.getGame().getId());
            statement.setInt(3, run.getAuthor().getId());
            statement.setString(4, run.getCategory());
            statement.setInt(5, run.getTimeMilliseconds());
            statement.setString(6, run.getVideoUrl());
            statement.setObject(7, run.getSetDate(), Types.DATE);
            statement.setBoolean(8, run.getVerified());
            statement.executeUpdate();
        }
    }

    public void update(Run run) throws Exception {
		String sql = "UPDATE Run SET game_id=?, author_id=?, category=?, time_milliseconds=?, video_url=?, set_date=?, verified=? WHERE id=?";
		try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, run.getGame().getId());
            statement.setInt(2, run.getAuthor().getId());
            statement.setString(3, run.getCategory());
            statement.setInt(4, run.getTimeMilliseconds());
            statement.setString(5, run.getVideoUrl());
            statement.setObject(6, run.getSetDate(), Types.DATE);
            statement.setBoolean(7, run.getVerified());
            statement.setInt(8, run.getId());
			statement.executeUpdate();
		}
	}
	
	public void delete(int runId) throws Exception {
		String sql = "DELETE FROM Run WHERE id=?";
		try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, runId);
			statement.executeUpdate();
		}
	}
}
