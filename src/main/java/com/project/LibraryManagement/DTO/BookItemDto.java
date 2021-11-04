package com.project.LibraryManagement.DTO;

import com.project.LibraryManagement.Model.BookFormat;
import com.project.LibraryManagement.Model.BookStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookItemDto {

    private long barcode;

    private String title;
    private String subject;
    private String publisher;
    private String language;
    private int numberOfPages;

    private Boolean isReferenceOnly;
    private Date borrowed;
    private Date dueDate;
    private double price;
    private BookFormat format;
    private BookStatus status;
    private Date dateOfPurchase;
    private Date publicationDate;

    private BookDto originalBookDto;

}
