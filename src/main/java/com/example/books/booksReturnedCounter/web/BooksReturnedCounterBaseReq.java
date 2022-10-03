package com.example.books.booksReturnedCounter.web;

import javax.validation.constraints.NotNull;

public class BooksReturnedCounterBaseReq {

    @NotNull
    private int quantityReturned;

    @NotNull
    private Long issueStatusId;

    @NotNull
    private Long loanOfBooksId;

    @NotNull
    private Long returnOfBooksId;

    public int getQuantityReturned() {
        return quantityReturned;
    }

    public void setQuantityReturned(int quantityReturned) {
        this.quantityReturned = quantityReturned;
    }

    public Long getIssueStatusId() {
        return issueStatusId;
    }

    public void setIssueStatusId(Long issueStatusId) {
        this.issueStatusId = issueStatusId;
    }

    public Long getLoanOfBooksId() {
        return loanOfBooksId;
    }

    public void setLoanOfBooksId(Long loanOfBooksId) {
        this.loanOfBooksId = loanOfBooksId;
    }

    public Long getReturnOfBooksId() {
        return returnOfBooksId;
    }

    public void setReturnOfBooksId(Long returnOfBooksId) {
        this.returnOfBooksId = returnOfBooksId;
    }
}
