package data;

import models.Author;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO
{
    public List<Author> getAll() throws Exception {
        List<Author> authors = new ArrayList<>();
        String sql = "SELECT * FROM Author";

        try (Connection connection = DatabaseUtil.getConnection(); Statement statement = connection.createStatement(); ResultSet results = statement.executeQuery(sql)) {
            while (results.next()) {
                authors.add(new Author(
                    results.getInt("id"),
                    results.getString("username"),
                    results.getString("display_name"),
                    results.getString("password_hash"),
                    results.getString("profile_picture_url"),
                    results.getObject("create_date", LocalDate.class)
                ));
            }
        }
        return authors;
    }

    public void insert(Author author) throws Exception {
        String sql = "INSERT INTO Author (id, username, display_name, password_hash, profile_picture_url, create_date) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, author.getId());
            statement.setString(2, author.getUsername());
            statement.setString(3, author.getDisplayName());
            statement.setString(4, author.getPasswordHash());
            statement.setString(5, author.getProfilePictureUrl());
            statement.setObject(6, author.getCreateDate(), Types.DATE);
            statement.executeUpdate();
        }
    }

    public void update(Author author) throws Exception {
		String sql = "UPDATE Author SET username=?, display_name=?, password_hash=?, profile_picture_url=?, create_date=? WHERE id=?";
		try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, author.getUsername());
            statement.setString(2, author.getDisplayName());
            statement.setString(3, author.getPasswordHash());
            statement.setString(4, author.getProfilePictureUrl());
            statement.setObject(5, author.getCreateDate(), Types.DATE);
            statement.setInt(6, author.getId());
			statement.executeUpdate();
		}
	}
	
	public void delete(int authorId) throws Exception {
		String sql = "DELETE FROM Author WHERE id=?";
		try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, authorId);
			statement.executeUpdate();
		}
	}
}
