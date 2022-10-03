package com.example.books.book.converter;

import com.example.books.book.Books;
import com.example.books.book.web.BooksView;
import com.example.books.authors.Authors;
import com.example.books.authors.converter.AuthorsToAuthorsViewConverter;
import com.example.books.authors.web.AuthorsView;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class FullBookConverterAuthor implements Converter<Books, BooksView> {
    private final AuthorsToAuthorsViewConverter authorsToAuthorsViewConverter;

    public FullBookConverterAuthor(AuthorsToAuthorsViewConverter authorsToAuthorsViewConverter) {
        this.authorsToAuthorsViewConverter = authorsToAuthorsViewConverter;
    }

    @Override
    public BooksView convert(@NonNull Books books) {
        BooksView view = new BooksView();
        view.setId(books.getId());
        view.setNameBook(books.getNameBook());
        view.setYearPublishing(books.getYearPublishing());
        view.setNumberPages(books.getNumberPages());
        view.setAvailability(books.getAvailability());
        Set<AuthorsView> authorsViewSet = new HashSet<>();
        Set<Authors> authors = books.getAuthors();
        view.setAuthors(authorsViewSet);
        authors.forEach(author -> authorsViewSet.add(authorsToAuthorsViewConverter.convert(author)));
        view.setAuthors(authorsViewSet);
        return view;
    }
}