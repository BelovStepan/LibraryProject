package com.example.books.readers.web;

import com.example.books.base.BaseRequest;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class ReadersBaseReq extends BaseRequest {
    @NotEmpty
    private String lastName;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String patronymic;

    @NotEmpty
    private String phoneNumber;

    @NotEmpty
    private String passportData;

    @NotNull
    private Date dateOfBirth;

    @NotEmpty
    private List<@Valid Id> loanOfBooks;

    @NotEmpty
    private List<@Valid Id> returnOfBooks;

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

    public List<Id> getLoanOfBooks() {
        return loanOfBooks;
    }

    public void setLoanOfBooks(List<Id> loanOfBooks) {
        this.loanOfBooks = loanOfBooks;
    }

    public List<Id> getReturnOfBooks() {return returnOfBooks;}

    public void setReturnOfBooks(List<Id> returnOfBooks) {this.returnOfBooks = returnOfBooks;}
}
