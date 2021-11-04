package com.project.LibraryManagement.DTO;

import com.project.LibraryManagement.Entity.Author;
import com.project.LibraryManagement.Entity.Book;
import com.project.LibraryManagement.Entity.BookItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {

    AuthorDto authorToDto(Author author);

    @Mapping(source = "book.author", target = "authorDto")
    BookDto bookToDto(Book book);

    @Mapping(source = "bookItem.originalBook", target = "originalBookDto")
    BookItemDto bookItemToDto(BookItem bookItem);

    @Mapping(target = "booksByAuthor", ignore = true)
    Author authorFromDto(AuthorDto authorDto);

    @Mapping(source = "bookDto.authorDto", target = "author")
    Book bookFromDto(BookDto bookDto);

    @Mapping(source = "bookItemDto.originalBookDto", target = "originalBook")
    BookItem bookItemFromDto(BookItemDto bookItemDto);

}
