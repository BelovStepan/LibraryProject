package com.example.books.loanOfBooks.web;

import com.example.books.base.BaseRequest;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class LoanOfBooksBaseReq extends BaseRequest {

    @NotNull
    private Date dateOfIssue;

    @NotNull
    private Date issuancePeriod;

    @NotNull
    private int numberOfBooksGivenOut;

    @NotNull
    private Long booksId;

    @NotNull
    private Long readersId;

    @NotNull
    private Long issueId;

    public Date getDateOfIssue() {return dateOfIssue;}

    public void setDateOfIssue(Date dateOfIssue) {this.dateOfIssue = dateOfIssue;}

    public Date getIssuancePeriod() {return issuancePeriod;}

    public void setIssuancePeriod(Date issuancePeriod) {this.issuancePeriod = issuancePeriod;}

    public int getNumberOfBooksGivenOut() {return numberOfBooksGivenOut;}

    public void setNumberOfBooksGivenOut(int numberOfBooksGivenOut) {this.numberOfBooksGivenOut = numberOfBooksGivenOut;}

    public Long getBooksId() {return booksId;}

    public void setBooksId(Long booksId) {this.booksId = booksId;}

    public Long getReadersId() {return readersId;}

    public void setReadersId(Long readersId) {this.readersId = readersId;}

    public Long getIssueId() {return issueId;}

    public void setIssueId(Long issueId) {this.issueId = issueId;}
}
