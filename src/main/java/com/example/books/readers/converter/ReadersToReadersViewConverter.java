package com.example.books.readers.converter;

import com.example.books.readers.Readers;
import com.example.books.readers.web.ReadersViewNotLoanOfBooksAndReturnOfBooks;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ReadersToReadersViewConverter implements Converter<Readers, ReadersViewNotLoanOfBooksAndReturnOfBooks> {
    @Override
    public ReadersViewNotLoanOfBooksAndReturnOfBooks convert(@NonNull Readers readers) {
        ReadersViewNotLoanOfBooksAndReturnOfBooks view = new ReadersViewNotLoanOfBooksAndReturnOfBooks();
        view.setId(readers.getId());
        view.setPhoneNumber(readers.getPhoneNumber());
        view.setLastName(readers.getLastName());
        view.setFirstName(readers.getFirstName());
        view.setPatronymic(readers.getPatronymic());
        view.setPassportData(readers.getPassportData());
        view.setDateOfBirth(readers.getDateOfBirth());
        return view;
    }
}
