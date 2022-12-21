package com.example.books.authors.converter;

import com.example.books.authors.Authors;
import com.example.books.authors.web.AuthorsView;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;


@Component
public class AuthorsToAuthorsViewConverter implements Converter<Authors, AuthorsView> {

    @Override
    public AuthorsView convert(@NonNull Authors authors) {
        AuthorsView view = new AuthorsView();
        view.setId(authors.getId());
        view.setLastName(authors.getLastName());
        view.setFirstName(authors.getFirstName());
        view.setPatronymic(authors.getPatronymic());
        view.setCity(authors.getCity());
        view.setDateOfBirth(authors.getDateOfBirth());
        return view;
    }
}
