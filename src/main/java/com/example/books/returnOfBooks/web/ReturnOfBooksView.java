package com.example.books.returnOfBooks.web;

import com.example.books.loanOfBooks.web.LoanOfBooksView;
import com.example.books.readers.web.ReadersView;
import com.example.books.readers.web.ReadersViewNotLoanOfBooksAndReturnOfBooks;

public class ReturnOfBooksView {
    private Long id;
    private int returnAmount;
    private ReadersViewNotLoanOfBooksAndReturnOfBooks readers;
    private LoanOfBooksView loanOfBooks;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(int returnAmount) {
        this.returnAmount = returnAmount;
    }

    public ReadersViewNotLoanOfBooksAndReturnOfBooks getReaders() {
        return readers;
    }

    public void setReaders(ReadersViewNotLoanOfBooksAndReturnOfBooks readers) {
        this.readers = readers;
    }

    public LoanOfBooksView getLoanOfBooks() {
        return loanOfBooks;
    }

    public void setLoanOfBooks(LoanOfBooksView loanOfBooks) {
        this.loanOfBooks = loanOfBooks;
    }
}
