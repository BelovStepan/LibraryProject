package com.example.books.returnOfBooks.converter;

import com.example.books.loanOfBooks.LoanOfBooks;
import com.example.books.loanOfBooks.converter.LoanOfBooksToLoanOfBooksViewConverter;
import com.example.books.loanOfBooks.web.LoanOfBooksView;
import com.example.books.readers.Readers;
import com.example.books.readers.converter.ReadersToReadersViewConverter;
import com.example.books.readers.web.ReadersViewNotLoanOfBooksAndReturnOfBooks;
import com.example.books.returnOfBooks.ReturnOfBooks;
import com.example.books.returnOfBooks.web.ReturnOfBooksView;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
public class ReturnOfBooksToReturnOfBooksViewConverter implements Converter<ReturnOfBooks, ReturnOfBooksView> {
    private final ReadersToReadersViewConverter readersToReadersViewConverter;
    private final LoanOfBooksToLoanOfBooksViewConverter loanOfBooksToLoanOfBooksViewConverter;

    public ReturnOfBooksToReturnOfBooksViewConverter(ReadersToReadersViewConverter readersToReadersViewConverter, LoanOfBooksToLoanOfBooksViewConverter loanOfBooksToLoanOfBooksViewConverter) {
        this.readersToReadersViewConverter = readersToReadersViewConverter;
        this.loanOfBooksToLoanOfBooksViewConverter = loanOfBooksToLoanOfBooksViewConverter;
    }

    @Override
    public ReturnOfBooksView convert(@NotNull ReturnOfBooks returnOfBooks) {
        ReturnOfBooksView view = new ReturnOfBooksView();
        view.setId(returnOfBooks.getId());
        view.setReturnAmount(returnOfBooks.getReturnAmount());
        Readers readers = returnOfBooks.getReaders();
        ReadersViewNotLoanOfBooksAndReturnOfBooks readersView = readersToReadersViewConverter.convert(readers);
        view.setReaders(readersView);
        LoanOfBooks loanOfBooks = returnOfBooks.getLoanOfBooks();
        LoanOfBooksView loanOfBooksView = loanOfBooksToLoanOfBooksViewConverter.convert(loanOfBooks);
        view.setLoanOfBooks(loanOfBooksView);
        return view;
    }
}
