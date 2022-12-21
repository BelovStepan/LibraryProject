package com.example.books.book;

import com.example.books.authors.Authors;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")
public class Books {

    @Id
    @Column(name = "id")
    @GenericGenerator(
            name = "books_id_seq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "books_id_seq"),
                    @Parameter(name = "INCREMENT", value = "1"),
                    @Parameter(name = "MINVALUE", value = "1"),
                    @Parameter(name = "MAXVALUE", value = "9223372036854775807"),
                    @Parameter(name = "CACHE", value = "1")
            }
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "books_id_seq")
    private Long id;

    @Column(name = "name_book")
    private String nameBook;

    @Column(name = "year_publishing")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date yearPublishing;

    @Column(name = "number_pages")
    private int numberPages;

    @Column(name = "availability")
    private int availability;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinTable(name="fc_books_authors",
            joinColumns = { @JoinColumn(name = "id_book") },
            inverseJoinColumns = { @JoinColumn(name = "id_author") })
    private Set<Authors> authors = new HashSet<>();

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getNameBook() {
        return nameBook;
    }

    public void setNameBook(String nameBook) {
        this.nameBook = nameBook;
    }

    public Date getYearPublishing() {
        return yearPublishing;
    }

    public void setYearPublishing(Date yearPublishing) {
        this.yearPublishing = yearPublishing;
    }

    public int getNumberPages() {
        return numberPages;
    }

    public void setNumberPages(int numberPages) {
        this.numberPages = numberPages;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    public Set<Authors> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Authors> authors) {
        this.authors = authors;
    }

    public void addBook(Authors authors){
        this.authors.add(authors);
    }

}

