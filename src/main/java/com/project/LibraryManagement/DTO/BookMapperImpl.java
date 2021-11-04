package com.project.LibraryManagement.DTO;

import com.project.LibraryManagement.Entity.Author;
import com.project.LibraryManagement.Entity.Book;
import com.project.LibraryManagement.Entity.BookItem;
import org.springframework.stereotype.Component;

@Component
public class BookMapperImpl implements BookMapper{
    @Override
    public AuthorDto authorToDto(Author author) {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setDescription(author.getDescription());
        authorDto.setName(author.getName());
        return authorDto;
    }

    @Override
    public BookDto bookToDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setAuthorDto(authorToDto(book.getAuthor()));
        bookDto.setLanguage(book.getLanguage());
        bookDto.setPublisher(book.getPublisher());
        bookDto.setNumberOfPages(book.getNumberOfPages());
        bookDto.setSubject(book.getSubject());
        bookDto.setTitle(book.getTitle());
        bookDto.setPublicationDate(book.getPublicationDate());
        return bookDto;
    }

    @Override
    public BookItemDto bookItemToDto(BookItem bookItem) {
        BookItemDto bookItemDto = new BookItemDto();

        bookItemDto.setBarcode(bookItem.getBarcode());
        bookItemDto.setBorrowed(bookItem.getBorrowed());
        bookItemDto.setDueDate(bookItem.getDueDate());
        bookItemDto.setFormat(bookItem.getFormat());
        bookItemDto.setDateOfPurchase(bookItem.getDateOfPurchase());
        bookItemDto.setIsReferenceOnly(bookItem.getIsReferenceOnly());
        bookItemDto.setPrice(bookItem.getPrice());
        bookItemDto.setStatus(bookItem.getStatus());
        bookItemDto.setOriginalBookDto(bookToDto(bookItem.getOriginalBook()));

        return bookItemDto;
    }

    @Override
    public Author authorFromDto(AuthorDto authorDto) {
        Author author = new Author();
        author.setName(authorDto.getName());
        author.setDescription(authorDto.getDescription());
        return author;
    }

    @Override
    public Book bookFromDto(BookDto bookDto) {
        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setAuthor(authorFromDto(bookDto.getAuthorDto()));
        book.setSubject(bookDto.getSubject());
        book.setLanguage(bookDto.getLanguage());
        book.setPublisher(bookDto.getPublisher());
        book.setNumberOfPages(bookDto.getNumberOfPages());
        book.setPublicationDate(bookDto.getPublicationDate());
        return book;
    }

    @Override
    public BookItem bookItemFromDto(BookItemDto bookItemDto) {
        BookItem bookItem = new BookItem();

        bookItem.setBarcode(bookItemDto.getBarcode());
        bookItem.setBorrowed(bookItemDto.getBorrowed());
        bookItem.setDueDate(bookItemDto.getDueDate());
        bookItem.setFormat(bookItemDto.getFormat());
        bookItem.setDateOfPurchase(bookItemDto.getDateOfPurchase());
        bookItem.setIsReferenceOnly(bookItemDto.getIsReferenceOnly());
        bookItem.setPrice(bookItemDto.getPrice());
        bookItem.setStatus(bookItemDto.getStatus());
        bookItem.setOriginalBook(bookFromDto(bookItemDto.getOriginalBookDto()));

        return bookItem;
    }
}
