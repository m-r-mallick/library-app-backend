package com.project.LibraryManagement.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.LibraryManagement.Model.BookFormat;
import com.project.LibraryManagement.Model.BookStatus;
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
@Table(name = "book_copies")
public class BookItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long barcode;

    private Boolean isReferenceOnly;
    private Date borrowed;
    private Date dueDate;
    @Column(nullable = false)
    private double price;
    @Enumerated(value = EnumType.STRING)
    private BookFormat format;
    @Enumerated(value = EnumType.STRING)
    private BookStatus status;
    private Date dateOfPurchase;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "ISBN")
    @JsonBackReference(value = "copy-book")
    private Book originalBook;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    @JsonBackReference(value = "copy-customer")
    private Account customer;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "reserved_books",
            joinColumns = @JoinColumn(name = "book_item_id"),
            inverseJoinColumns = @JoinColumn(name = "account_id")
    )
    @JsonIgnore
    private List<Account> reservees;

}
