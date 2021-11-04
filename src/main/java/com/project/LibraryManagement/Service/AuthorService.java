package com.project.LibraryManagement.Service;

import com.project.LibraryManagement.Entity.Author;
import com.project.LibraryManagement.Repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }

    public String addAuthor(Author author) {
        authorRepository.save(author);
        return author.toString() + " saved!";
    }

    public Author getAuthorById(Long id) {
        return authorRepository.findById(id).orElse(null);
    }

    public String deleteAuthor(Long id) {
        Author author = authorRepository.findById(id).orElse(null);
        if (author != null) {
            authorRepository.delete(author);
            return "Author deleted!";
        } else {
            return "Author not found!";
        }
    }
}
