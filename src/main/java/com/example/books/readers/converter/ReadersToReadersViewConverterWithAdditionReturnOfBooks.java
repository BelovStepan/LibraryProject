package com.example.books.readers.converter;

import com.example.books.readers.Readers;
import com.example.books.readers.web.ReadersView;
import com.example.books.returnOfBooks.ReturnOfBooks;
import com.example.books.returnOfBooks.converter.ReturnOfBooksToReturnOfBooksViewConverterNotReaders;
import com.example.books.returnOfBooks.web.ReturnOfBooksView;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class ReadersToReadersViewConverterWithAdditionReturnOfBooks implements Converter<Readers, ReadersView> {
    private final ReturnOfBooksToReturnOfBooksViewConverterNotReaders returnOfBooksToReturnOfBooksViewConverterNotReaders;

    public ReadersToReadersViewConverterWithAdditionReturnOfBooks(ReturnOfBooksToReturnOfBooksViewConverterNotReaders returnOfBooksToReturnOfBooksViewConverterNotReaders) {
        this.returnOfBooksToReturnOfBooksViewConverterNotReaders = returnOfBooksToReturnOfBooksViewConverterNotReaders;
    }
    @Override
    public ReadersView convert(@NonNull Readers readers) {
        ReadersView view = new ReadersView();
        view.setId(readers.getId());
        view.setPhoneNumber(readers.getPhoneNumber());
        view.setLastName(readers.getLastName());
        view.setPassportData(readers.getPassportData());
        view.setDateOfBirth(readers.getDateOfBirth());

        Set<ReturnOfBooksView> returnOfBooksViewSet = new HashSet<>();
        Set<ReturnOfBooks> returnOfBooks = readers.getReturnOfBooks();
        view.setReturnOfBooks(returnOfBooksViewSet);
        returnOfBooks.forEach(reader -> returnOfBooksViewSet.add(returnOfBooksToReturnOfBooksViewConverterNotReaders.convert(reader)));
        view.setReturnOfBooks(returnOfBooksViewSet);
        return view;
    }
}
