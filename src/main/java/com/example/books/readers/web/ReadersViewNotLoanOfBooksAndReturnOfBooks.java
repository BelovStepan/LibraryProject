package com.example.books.readers.web;

import java.util.Date;

public class ReadersViewNotLoanOfBooksAndReturnOfBooks {
    private Long id;
    private String lastName;
    private String phoneNumber;
    private String passportData;
    private Date dateOfBirth;

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getLastName() {return lastName;}

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
}
