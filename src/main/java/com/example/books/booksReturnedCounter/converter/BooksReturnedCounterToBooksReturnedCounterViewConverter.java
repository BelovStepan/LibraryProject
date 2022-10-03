package com.example.books.booksReturnedCounter.converter;

import com.example.books.booksReturnedCounter.BooksReturnedCounter;
import com.example.books.booksReturnedCounter.web.BooksReturnedCounterView;
import com.example.books.issueStatus.IssueStatus;
import com.example.books.issueStatus.converter.IssueStatusToIssueStatusViewConverter;
import com.example.books.issueStatus.web.IssueStatusView;
import com.example.books.loanOfBooks.LoanOfBooks;
import com.example.books.loanOfBooks.converter.LoanOfBooksToLoanOfBooksViewConverter;
import com.example.books.loanOfBooks.web.LoanOfBooksView;
import com.example.books.returnOfBooks.ReturnOfBooks;
import com.example.books.returnOfBooks.converter.ReturnOfBooksToReturnOfBooksViewConverter;
import com.example.books.returnOfBooks.web.ReturnOfBooksView;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class BooksReturnedCounterToBooksReturnedCounterViewConverter implements Converter<BooksReturnedCounter, BooksReturnedCounterView> {

    private final IssueStatusToIssueStatusViewConverter issueStatusToIssueStatusViewConverter;
    private final LoanOfBooksToLoanOfBooksViewConverter loanOfBooksToLoanOfBooksViewConverter;
    private final ReturnOfBooksToReturnOfBooksViewConverter returnOfBooksToReturnOfBooksViewConverter;

    public BooksReturnedCounterToBooksReturnedCounterViewConverter(IssueStatusToIssueStatusViewConverter issueStatusToIssueStatusViewConverter, LoanOfBooksToLoanOfBooksViewConverter loanOfBooksToLoanOfBooksViewConverter, ReturnOfBooksToReturnOfBooksViewConverter returnOfBooksToReturnOfBooksViewConverter) {
        this.issueStatusToIssueStatusViewConverter = issueStatusToIssueStatusViewConverter;
        this.loanOfBooksToLoanOfBooksViewConverter = loanOfBooksToLoanOfBooksViewConverter;
        this.returnOfBooksToReturnOfBooksViewConverter = returnOfBooksToReturnOfBooksViewConverter;
    }

    @Override
    public BooksReturnedCounterView convert(@NonNull BooksReturnedCounter booksReturnedCounter) {
        BooksReturnedCounterView view = new BooksReturnedCounterView();
        view.setQuantityReturned(booksReturnedCounter.getQuantityReturned());
        IssueStatus issueStatus = booksReturnedCounter.getIssueStatus();
        IssueStatusView issueStatusView = issueStatusToIssueStatusViewConverter.convert(issueStatus);
        view.setIssueStatus(issueStatusView);
        LoanOfBooks loanOfBooks = booksReturnedCounter.getLoanOfBooks();
        LoanOfBooksView loanOfBooksView = loanOfBooksToLoanOfBooksViewConverter.convert(loanOfBooks);
        ReturnOfBooks returnOfBooks = booksReturnedCounter.getReturnOfBooks();
        ReturnOfBooksView returnOfBooksView = returnOfBooksToReturnOfBooksViewConverter.convert(returnOfBooks);
        view.setLoanOfBooks(loanOfBooksView);
        return view;
    }
}

