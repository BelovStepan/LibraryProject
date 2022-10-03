package com.example.books.authors;

import com.example.books.book.Books;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "authors")
public class Authors {
    @Id
    @Column(name = "id")
    @GenericGenerator(
            name = "authors_id_seq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "authors_id_seq"),
                    @org.hibernate.annotations.Parameter(name= "INCREMENT", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "MINVALUE", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "MAXVALUE", value = "9223372036854775807"),
                    @org.hibernate.annotations.Parameter(name = "CACHE", value = "1")
            }
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authors_id_seq")
    private Long id;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "city")
    private String city;

    @Column(name = "date_of_birth")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateOfBirth;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinTable(name="fc_books_authors",
            joinColumns = { @JoinColumn(name = "id_author") },
            inverseJoinColumns = { @JoinColumn(name = "id_book") })
    private Set<Books> books = new HashSet<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getDateOfBirth() {return dateOfBirth;}

    public void setDateOfBirth(Date dateOfBirth) {this.dateOfBirth = dateOfBirth;}

    public Set<Books> getBooks() {return books;}

    public void setBooks(Set<Books> books) {this.books = books;}
}
