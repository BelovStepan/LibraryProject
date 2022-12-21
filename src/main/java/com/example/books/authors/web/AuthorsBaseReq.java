package com.example.books.authors.web;

import com.example.books.book.Books;
import com.example.books.base.BaseRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class AuthorsBaseReq extends BaseRequest {
    @NotEmpty
    private List<@Valid Id> books;

    @NotEmpty
    private String lastName;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String patronymic;

    @NotEmpty
    private String city;

    @NotNull
    Date dateOfBirth = new Date();

    public Date getDateOfBirth() {return dateOfBirth;}

    public void setDateOfBirth(Date dateOfBirth) {this.dateOfBirth = dateOfBirth;}

    public List<Id> getBooks() {
        return books;
    }

    public void setBooks(List<Id> books) {
        this.books = books;
    }

    public Set<Books> booksSet;

    public String getLastName() {return lastName;}

    public void setLastName(String lastName) {this.lastName = lastName;}

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
}
