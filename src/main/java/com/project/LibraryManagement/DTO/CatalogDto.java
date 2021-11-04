package com.project.LibraryManagement.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@Component
@Getter
@Setter
public class CatalogDto {

    private Date creationDate;
    private int totalBooks;
    private Map<String, List> bookTitles;
    private Map<String, List> bookAuthors;
    private Map<String, List> bookSubjects;
    private Map<Date, List> bookPublicationDates;

}
