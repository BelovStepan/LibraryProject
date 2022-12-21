package com.example.books.authors.web;

import com.example.books.book.web.BooksView;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class AuthorsView {
    private Long id;
    private String lastName;
    private String firstName;
    private String patronymic;
    private String city;
    private Date dateOfBirth;

    private Set<BooksView> books = new HashSet<>();

    public Set<BooksView> getBooks() {
        return books;
    }

    public void setBooks(Set<BooksView> books) {
        this.books = books;
    }

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getDateOfBirth() {return dateOfBirth;}

    public void setDateOfBirth(Date dateOfBirth) {this.dateOfBirth = dateOfBirth;}
}
