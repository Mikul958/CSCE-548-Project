package com.speedrun.csce548.controller;

import com.speedrun.csce548.models.Author;
import com.speedrun.csce548.service.AuthorService;
import com.speedrun.csce548.models.Run;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }


    // CREATE ENDPOINTS
    @PostMapping
    public ResponseEntity<Author> create(@RequestBody Author author) {
        Author addedAuthor = authorService.addAuthor(author);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedAuthor);
    }

    // READ ENDPOINTS
    @GetMapping
    public ResponseEntity<List<Author>> getAll() {
        List<Author> retrievedAuthors = authorService.getAllAuthors();
        return ResponseEntity.ok(retrievedAuthors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getById(@PathVariable Integer id) {
        Author retrievedAuthor = authorService.getAuthorById(id);
        return ResponseEntity.ok(retrievedAuthor);
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<List<Run>> getHistory(@PathVariable Integer id) {
        List<Run> authorHistory = authorService.getRunHistory(id);
        return ResponseEntity.ok(authorHistory);
    }

    // UPDATE ENDPOINTS
    @PutMapping("/{id}")
    public ResponseEntity<Author> update(@PathVariable Integer id, @RequestBody Author author) {
        Author updatedAuthor = authorService.updateAuthor(id, author);
        return ResponseEntity.ok(updatedAuthor);
    }

    // DELETE ENDPOINTS
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }
}
