package com.project.LibraryManagement.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "books")
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ISBN", nullable = false, updatable = false)
    private long ISBN;
    private String title;
    private String subject;
    private String publisher;
    private String language;
    private int numberOfPages;
    private Date publicationDate;

    @OneToOne()
    @JoinColumn()
    @JsonBackReference
    private Author author;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "originalBook", cascade = {
            CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE
    })
    @JsonManagedReference(value = "copy-book")
    private List<BookItem> copies;
}
