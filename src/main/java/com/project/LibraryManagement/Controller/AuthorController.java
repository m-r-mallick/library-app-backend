package com.project.LibraryManagement.Controller;

import com.project.LibraryManagement.Entity.Author;
import com.project.LibraryManagement.Service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/authors")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @GetMapping("/")
    public List<Author> getAuthors() {
        return authorService.getAuthors();
    }

    @PostMapping("/addAuthor")
    public String addAuthor(@RequestBody Author author) {
        return authorService.addAuthor(author);
    }

    @GetMapping("/{id}")
    public Author getAuthorById(@PathVariable(value = "id") Long id) {
        return authorService.getAuthorById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable(value = "id") Long id) {
        return authorService.deleteAuthor(id);
    }

}
