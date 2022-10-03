package com.example.books.returnOfBooks.converter;

import com.example.books.loanOfBooks.LoanOfBooks;
import com.example.books.loanOfBooks.converter.LoanOfBooksToLoanOfBooksViewConverterNotReaders;
import com.example.books.loanOfBooks.web.LoanOfBooksView;
import com.example.books.returnOfBooks.ReturnOfBooks;
import com.example.books.returnOfBooks.web.ReturnOfBooksView;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
public class ReturnOfBooksToReturnOfBooksViewConverterNotReaders implements Converter<ReturnOfBooks, ReturnOfBooksView> {
    private final LoanOfBooksToLoanOfBooksViewConverterNotReaders loanOfBooksToLoanOfBooksViewConverterNotReaders;

    public ReturnOfBooksToReturnOfBooksViewConverterNotReaders(LoanOfBooksToLoanOfBooksViewConverterNotReaders loanOfBooksToLoanOfBooksViewConverterNotReaders) {
        this.loanOfBooksToLoanOfBooksViewConverterNotReaders = loanOfBooksToLoanOfBooksViewConverterNotReaders;
    }

    @Override
    public ReturnOfBooksView convert(@NotNull ReturnOfBooks returnOfBooks) {
        ReturnOfBooksView view = new ReturnOfBooksView();
        view.setId(returnOfBooks.getId());
        view.setReturnAmount(returnOfBooks.getReturnAmount());
        LoanOfBooks loanOfBooks = returnOfBooks.getLoanOfBooks();
        LoanOfBooksView loanOfBooksView = loanOfBooksToLoanOfBooksViewConverterNotReaders.convert(loanOfBooks);
        view.setLoanOfBooks(loanOfBooksView);
        return view;
    }
}
