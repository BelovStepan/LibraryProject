package com.example.books.readers.web;

import com.example.books.loanOfBooks.web.LoanOfBooksView;
import com.example.books.returnOfBooks.web.ReturnOfBooksView;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ReadersView {
    private Long id;
    private String lastName;
    private String phoneNumber;
    private String passportData;
    private Date dateOfBirth;
    private Set<LoanOfBooksView> loanOfBooks = new HashSet<>();
    private Set<ReturnOfBooksView> returnOfBooks = new HashSet<>();

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassportData() {
        return passportData;
    }

    public void setPassportData(String passportData) {
        this.passportData = passportData;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<LoanOfBooksView> getLoanOfBooks() {return loanOfBooks;}

    public void setLoanOfBooks(Set<LoanOfBooksView> loanOfBooks) {this.loanOfBooks = loanOfBooks;}

    public Set<ReturnOfBooksView> getReturnOfBooks() {return returnOfBooks;}

    public void setReturnOfBooks(Set<ReturnOfBooksView> returnOfBooks) {this.returnOfBooks = returnOfBooks;}
}
