package com.project.LibraryManagement.DTO;

import com.project.LibraryManagement.Entity.BookItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    private String title;
    private String subject;
    private String publisher;
    private String language;
    private int numberOfPages;
    private Date publicationDate;

    private AuthorDto authorDto;

    private List<BookItem> copies;
}
