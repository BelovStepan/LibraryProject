package com.example.books.book.web;

import com.example.books.authors.web.AuthorsView;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class BooksView {
    private Long id;
    private String nameBook;
    private Date yearPublishing;
    private int numberPages;
    private int availability;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public int getAvailability() {return availability;}

    public void setAvailability(int availability) {this.availability = availability;}

    private Set<AuthorsView> authors = new HashSet<>();

    public Set<AuthorsView> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<AuthorsView> authors) {
        this.authors = authors;
    }
}
