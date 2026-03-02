package com.speedrun.csce548.controller;

import com.speedrun.csce548.models.AuthorRequest;
import com.speedrun.csce548.models.AuthorResponse;
import com.speedrun.csce548.service.AuthorService;
import com.speedrun.csce548.models.RunResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController
{
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }


    // CREATE ENDPOINTS
    @PostMapping
    public ResponseEntity<AuthorResponse> create(@RequestBody AuthorRequest author) {
        AuthorResponse addedAuthor = authorService.addAuthor(author);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedAuthor);
    }

    // READ ENDPOINTS
    @GetMapping
    public ResponseEntity<List<AuthorResponse>> getAll() {
        List<AuthorResponse> retrievedAuthors = authorService.getAllAuthors();
        return ResponseEntity.ok(retrievedAuthors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponse> getById(@PathVariable Integer id) {
        AuthorResponse retrievedAuthor = authorService.getAuthorById(id);
        return ResponseEntity.ok(retrievedAuthor);
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<List<RunResponse>> getHistory(@PathVariable Integer id) {
        List<RunResponse> authorHistory = authorService.getRunHistory(id);
        return ResponseEntity.ok(authorHistory);
    }

    // UPDATE ENDPOINTS
    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponse> update(@PathVariable Integer id, @RequestBody AuthorRequest author) {
        AuthorResponse updatedAuthor = authorService.updateAuthor(id, author);
        return ResponseEntity.ok(updatedAuthor);
    }

    // DELETE ENDPOINTS
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }
}
