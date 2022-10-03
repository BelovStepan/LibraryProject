package com.example.books.authors.converter;

import com.example.books.book.Books;
import com.example.books.book.converter.BookToBookViewConverter;
import com.example.books.book.web.BooksView;
import com.example.books.authors.Authors;
import com.example.books.authors.web.AuthorsView;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class FullAuthorConverterBook implements Converter<Authors, AuthorsView> {

    private final BookToBookViewConverter bookToBookViewConverter;

    public FullAuthorConverterBook(BookToBookViewConverter bookToBookViewConverter) {
        this.bookToBookViewConverter = bookToBookViewConverter;
    }

    @Override
    public AuthorsView convert(@NonNull Authors authors) {
        AuthorsView view = new AuthorsView();
        view.setId(authors.getId());
        view.setLastName(authors.getLastName());
        view.setCity(authors.getCity());
        view.setDateOfBirth(authors.getDateOfBirth());
        Set<BooksView> booksViewSet = new HashSet<>();
        Set<Books> books = authors.getBooks();
        view.setBooks(booksViewSet);
        books.forEach(book -> booksViewSet.add(bookToBookViewConverter.convert(book)));
        view.setBooks(booksViewSet);
        return view;
    }
}