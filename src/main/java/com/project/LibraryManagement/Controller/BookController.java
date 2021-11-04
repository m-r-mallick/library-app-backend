package com.project.LibraryManagement.Controller;

import com.project.LibraryManagement.Entity.Book;
import com.project.LibraryManagement.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("/addBook")
    public String addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @GetMapping("/")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/search/{isbn}")
    public Book getBookById(@PathVariable(value = "isbn") Long isbn) {
        return bookService.getBookById(isbn);
    }

    @GetMapping("/search/title")
    public List<Book> getBooksByTitle(@RequestParam(value = "title") String title) {
        return bookService.getBooksByTitle(title);
    }

    @GetMapping("/search/author/{id}")
    public List<Book> getBooksByAuthor(@PathVariable(value = "id") Long id) {
        return bookService.getBooksByAuthor(id);
    }

    @GetMapping("/search/subject")
    public List<Book> getBooksBySubject(@RequestParam(value = "subject") String subject) {
        return bookService.getBooksBySubject(subject);
    }

    @GetMapping("/search/publisher")
    public List<Book> getBooksByPublisher(@RequestParam(value = "date") String publisher) {
        return bookService.getBooksByPublisher(publisher);
    }

    @PutMapping("/edit/{isbn}")
    public String editBook(@PathVariable(value = "isbn") Long isbn,
                           @RequestBody Book book) {
        return bookService.editBook(isbn, book);
    }

    @DeleteMapping("/delete/{isbn}")
    public String deleteBook(@PathVariable(value = "isbn") Long isbn) {
        return bookService.deleteBook(isbn);
    }

}
