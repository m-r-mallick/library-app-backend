package com.project.LibraryManagement.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.LibraryManagement.Model.AccountStatus;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "accounts")
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Enumerated(value = EnumType.STRING)
    private AccountStatus status;

    private Date dateOfMembership;
    private Integer totalBooksCheckedOut;
    private Double fineUnPaid;
    private Double finePaid;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = {
            CascadeType.MERGE, CascadeType.REMOVE, CascadeType.PERSIST
    })
    @JsonManagedReference(value = "copy-customer")
    @JsonIgnore
    private List<BookItem> issuedBooks;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "reservees")
    @JsonIgnore
    private List<BookItem> reservedBooks;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "accounts_roles",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @JsonIgnore
    private Collection<Role> roles;

}
