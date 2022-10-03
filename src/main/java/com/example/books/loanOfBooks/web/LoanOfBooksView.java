package com.example.books.loanOfBooks.web;

import com.example.books.book.web.BooksView;
import com.example.books.issueStatus.web.IssueStatusView;
import com.example.books.readers.web.ReadersViewNotLoanOfBooksAndReturnOfBooks;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class LoanOfBooksView {
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date dateOfIssue;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date issuancePeriod;

    private int numberOfBooksGivenOut;

    private BooksView books;

    private ReadersViewNotLoanOfBooksAndReturnOfBooks readers;

    private IssueStatusView issueStatus;

    public Long getId() {return id;}

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateOfIssue() {return dateOfIssue;}

    public void setDateOfIssue(Date dateOfIssue) {this.dateOfIssue = dateOfIssue;}

    public Date getIssuancePeriod() {return issuancePeriod;}

    public void setIssuancePeriod(Date issuancePeriod) {this.issuancePeriod = issuancePeriod;}

    public int getNumberOfBooksGivenOut() {return numberOfBooksGivenOut;}

    public void setNumberOfBooksGivenOut(int numberOfBooksGivenOut) {this.numberOfBooksGivenOut = numberOfBooksGivenOut;}

    public BooksView getBooks() {return books;}

    public void setBooks(BooksView books) {this.books = books;}

    public ReadersViewNotLoanOfBooksAndReturnOfBooks getReaders() {return readers;}

    public void setReaders(ReadersViewNotLoanOfBooksAndReturnOfBooks readers) {this.readers = readers;}

    public IssueStatusView getIssueStatus() {return issueStatus;}

    public void setIssueStatus(IssueStatusView issueStatus) {this.issueStatus = issueStatus;}
}
