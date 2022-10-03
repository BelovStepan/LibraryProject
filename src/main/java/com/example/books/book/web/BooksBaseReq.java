package com.example.books.book.web;

import com.example.books.base.BaseRequest;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class BooksBaseReq extends BaseRequest {

    @NotEmpty
    private List<@Valid Id> authors;

    @NotEmpty
    private String nameBook;

    @NotNull
    Date yearPublishing = new Date();

    @NotNull
    private int numberPages;

    @NotNull
    private int availability;

    public List<Id> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Id> authors) {
        this.authors = authors;
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

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }
}
