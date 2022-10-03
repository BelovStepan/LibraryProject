package com.example.books.loanOfBooks.converter;

import com.example.books.book.Books;
import com.example.books.book.converter.BookToBookViewConverter;
import com.example.books.book.web.BooksView;
import com.example.books.loanOfBooks.LoanOfBooks;
import com.example.books.loanOfBooks.web.LoanOfBooksView;
import com.example.books.readers.Readers;
import com.example.books.readers.converter.ReadersToReadersViewConverter;
import com.example.books.readers.web.ReadersViewNotLoanOfBooksAndReturnOfBooks;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class LoanOfBooksToLoanOfBooksViewConverter implements Converter<LoanOfBooks, LoanOfBooksView> {

    private final BookToBookViewConverter bookToBookViewConverter;
    private final ReadersToReadersViewConverter readersToReadersViewConverter;

    public LoanOfBooksToLoanOfBooksViewConverter(BookToBookViewConverter bookToBookViewConverter, ReadersToReadersViewConverter readersToReadersViewConverter) {
        this.bookToBookViewConverter = bookToBookViewConverter;
        this.readersToReadersViewConverter = readersToReadersViewConverter;
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
        Readers readers = loanOfBooks.getReaders();
        ReadersViewNotLoanOfBooksAndReturnOfBooks readersView = readersToReadersViewConverter.convert(readers);
        view.setReaders(readersView);
        return view;
    }
}
