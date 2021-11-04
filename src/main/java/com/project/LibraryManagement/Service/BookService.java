package com.project.LibraryManagement.Service;

import com.project.LibraryManagement.Entity.Book;
import com.project.LibraryManagement.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return new ArrayList<>(bookRepository.findAll());
    }

    public String addBook(Book book) {
        bookRepository.save(book);
        return book.toString() + " added!\n";
    }

    public Book getBookById(Long isbn) {
        return bookRepository.findById(isbn).orElse(null);
    }

    public List<Book> getBooksByTitle(String title) {
        return bookRepository.findAll().stream()
                .filter(book -> book.getTitle().equals(title))
                .collect(Collectors.toList());
    }

    public List<Book> getBooksByAuthor(Long id) {
        return bookRepository.findAll().stream()
                .filter(book -> book.getAuthor().getId().equals(id))
                .collect(Collectors.toList());
    }

    public List<Book> getBooksBySubject(String subject) {
        return bookRepository.findAll().stream()
                .filter(book -> book.getSubject().equals(subject))
                .collect(Collectors.toList());
    }

    public List<Book> getBooksByPublisher(String publisher) {
        return bookRepository.findAll().stream()
                .filter(book -> book.getPublisher().equals(publisher))
                .collect(Collectors.toList());
    }

    public String editBook(Long isbn, Book book) {
        Book ogBook = bookRepository.findById(isbn).orElse(null);
        if (ogBook != null) {
            ogBook.setTitle(book.getTitle());
            ogBook.setSubject(book.getSubject());
            ogBook.setPublisher(book.getPublisher());
            ogBook.setLanguage(book.getLanguage());
            ogBook.setNumberOfPages(book.getNumberOfPages());
            ogBook.setAuthor(book.getAuthor());
            ogBook.setPublicationDate(book.getPublicationDate());
            bookRepository.save(ogBook);
            return "Book successfully updated!";
        } else {
            return "Book not found!";
        }
    }

    public String deleteBook(Long isbn) {
        Book book = bookRepository.findById(isbn).orElse(null);
        if (book != null) {
            bookRepository.delete(book);
            return "Book deleted!";
        } else {
            return "Book not found!";
        }
    }
}
