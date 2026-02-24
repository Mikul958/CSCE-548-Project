package com.speedrun.csce548.service;

import com.speedrun.csce548.exceptions.EntryNotFoundException;
import com.speedrun.csce548.models.Author;
import com.speedrun.csce548.models.Run;
import com.speedrun.csce548.repository.AuthorRepository;
import com.speedrun.csce548.repository.RunRepository;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AuthorService
{
    private final AuthorRepository authorRepository;
    private final RunRepository runRepository;

    public AuthorService(AuthorRepository authorRepository, RunRepository runRepository) {
        this.authorRepository = authorRepository;
        this.runRepository = runRepository;
    }

    // CRUD operations

    /**
     * Creates a new author with the given data.
     * @param author Data for the new author.
     * @return The created author as it appears in the database.
     */
    public Author addAuthor(Author author) {
        if (author.getId() != null)
            throw new IllegalArgumentException("New author cannot already have an ID");
        return authorRepository.save(author);
    }

    /**
     * Gets all authors in the database.
     * @return A list containing all existing authors.
     */
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    /**
     * Gets the author with the given ID.
     * @param id Target ID of the author to retrieve.
     * @return The target author.
     */
    public Author getAuthorById(Integer id) {
        return authorRepository.findById(id)
            .orElseThrow(() -> new EntryNotFoundException("No author found with ID: " + id));
    }

    /**
     * Updates the author with the given ID with the provided data
     * @param id Target ID of the author to update.
     * @param updatedAuthor Author data to update the author in the database with.
     * @return The updated author.
     */
    public Author updateAuthor(Integer id, Author updatedAuthor) {
        Author foundAuthor = authorRepository.findById(id)
            .orElseThrow(() -> new EntryNotFoundException("No author found with ID: " + id));

        foundAuthor.setUsername(updatedAuthor.getUsername());
        foundAuthor.setDisplayName(updatedAuthor.getDisplayName());
        foundAuthor.setPasswordHash(updatedAuthor.getPasswordHash());
        foundAuthor.setProfilePictureUrl(updatedAuthor.getProfilePictureUrl());

        return authorRepository.save(foundAuthor);
    }

    /**
     * Deletes the author with the given ID from the database.
     * @param id Target ID to delete.
     */
    public void deleteAuthor(Integer id) {
        authorRepository.deleteById(id);
    }

    // Other specific logic

    /**
     * Gets a history of runs created by a given author.
     * @param authorId The ID of the author to retrieve the history for.
     * @return A list of all runs created by this author ordered from most recent to least recent.
     */
    public List<Run> getRunHistory(Integer authorId) {
        return runRepository.findByAuthor_IdOrderBySetDateDesc(authorId);
    }
}
