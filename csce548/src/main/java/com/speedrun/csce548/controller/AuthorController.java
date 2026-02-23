package com.speedrun.csce548.controller;

import com.speedrun.csce548.models.Author;
import com.speedrun.csce548.service.AuthorService;
import com.speedrun.csce548.models.Run;

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
    public Author create(@RequestBody Author author) {
        return authorService.addAuthor(author);
    }

    // READ ENDPOINTS
    @GetMapping
    public List<Author> getAll() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/{id}")
    public Author getById(@PathVariable Integer id) {
        return authorService.getAuthorById(id);
    }

    @GetMapping("/{id}/history")
    public List<Run> getHistory(@PathVariable Integer id) {
        return authorService.getRunHistory(id);
    }

    // UPDATE ENDPOINTS
    @PutMapping("/{id}")
    public Author update(@PathVariable Integer id, @RequestBody Author author) {
        return authorService.updateAuthor(id, author);
    }

    // DELETE ENDPOINTS
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        authorService.deleteAuthor(id);
    }
}
