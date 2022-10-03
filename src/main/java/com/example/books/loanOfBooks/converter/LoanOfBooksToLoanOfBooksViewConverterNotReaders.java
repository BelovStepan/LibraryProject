package com.example.books.loanOfBooks.converter;

import com.example.books.book.Books;
import com.example.books.book.converter.BookToBookViewConverter;
import com.example.books.book.web.BooksView;
import com.example.books.loanOfBooks.LoanOfBooks;
import com.example.books.loanOfBooks.web.LoanOfBooksView;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class LoanOfBooksToLoanOfBooksViewConverterNotReaders implements Converter<LoanOfBooks, LoanOfBooksView> {

    private final BookToBookViewConverter bookToBookViewConverter;

    public LoanOfBooksToLoanOfBooksViewConverterNotReaders(BookToBookViewConverter bookToBookViewConverter) {
        this.bookToBookViewConverter = bookToBookViewConverter;
    }

    @Override
    public LoanOfBooksView convert(@NonNull LoanOfBooks loanOfBooks) {
        LoanOfBooksView view = new LoanOfBooksView();
        view.setId(loanOfBooks.getId());
        view.setDateOfIssue(loanOfBooks.getDateOfIssue());
        view.setIssuancePeriod(loanOfBooks.getIssuancePeriod());
        view.setNumberOfBooksGivenOut(loanOfBooks.getNumberOfBooksGivenOut());
        Books books = loanOfBooks.getBooks();
        BooksView booksView = bookToBookViewConverter.convert(books);
        view.setBooks(booksView);
        return view;
    }
}
