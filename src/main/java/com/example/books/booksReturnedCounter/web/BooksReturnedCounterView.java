package com.example.books.booksReturnedCounter.web;

import com.example.books.issueStatus.web.IssueStatusView;
import com.example.books.loanOfBooks.web.LoanOfBooksView;
import com.example.books.returnOfBooks.web.ReturnOfBooksView;

public class BooksReturnedCounterView {
    private Long id;
    private int quantityReturned;
    private IssueStatusView issueStatus;
    private LoanOfBooksView loanOfBooks;
    private ReturnOfBooksView returnOfBooks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantityReturned() {
        return quantityReturned;
    }

    public void setQuantityReturned(int quantityReturned) {
        this.quantityReturned = quantityReturned;
    }

    public IssueStatusView getIssueStatus() {
        return issueStatus;
    }

    public void setIssueStatus(IssueStatusView issueStatus) {
        this.issueStatus = issueStatus;
    }

    public LoanOfBooksView getLoanOfBooks() {
        return loanOfBooks;
    }

    public void setLoanOfBooks(LoanOfBooksView loanOfBooks) {
        this.loanOfBooks = loanOfBooks;
    }

    public ReturnOfBooksView getReturnOfBooks() {return returnOfBooks;}

    public void setReturnOfBooks(ReturnOfBooksView returnOfBooks) {this.returnOfBooks = returnOfBooks;}
}
