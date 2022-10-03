package com.example.books.readers.converter;

import com.example.books.loanOfBooks.LoanOfBooks;
import com.example.books.loanOfBooks.converter.LoanOfBooksToLoanOfBooksViewConverterNotReaders;
import com.example.books.loanOfBooks.web.LoanOfBooksView;
import com.example.books.readers.Readers;
import com.example.books.readers.web.ReadersView;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class ReadersToReadersViewConverterWithAdditionLoanOfBooks implements Converter<Readers, ReadersView> {
    private final LoanOfBooksToLoanOfBooksViewConverterNotReaders loanOfBooksToLoanOfBooksViewConverterNotReaders;

    public ReadersToReadersViewConverterWithAdditionLoanOfBooks(LoanOfBooksToLoanOfBooksViewConverterNotReaders loanOfBooksToLoanOfBooksViewConverterNotReaders) {
        this.loanOfBooksToLoanOfBooksViewConverterNotReaders = loanOfBooksToLoanOfBooksViewConverterNotReaders;
    }

    @Override
    public ReadersView convert(@NonNull Readers readers) {
        ReadersView view = new ReadersView();
        view.setId(readers.getId());
        view.setPhoneNumber(readers.getPhoneNumber());
        view.setLastName(readers.getLastName());
        view.setPassportData(readers.getPassportData());
        view.setDateOfBirth(readers.getDateOfBirth());

        Set<LoanOfBooksView> loanOfBooksViewSet = new HashSet<>();
        Set<LoanOfBooks> loanOfBooks = readers.getLoanOfBooks();
        view.setLoanOfBooks(loanOfBooksViewSet);
        loanOfBooks.forEach(reader -> loanOfBooksViewSet.add(loanOfBooksToLoanOfBooksViewConverterNotReaders.convert(reader)));
        view.setLoanOfBooks(loanOfBooksViewSet);
        return view;
    }
}
