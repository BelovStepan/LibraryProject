package com.example.books.authors;

import com.example.books.book.Books;
import com.example.books.book.BooksRepository;
import com.example.books.authors.converter.AuthorsToAuthorsViewConverter;
import com.example.books.authors.converter.FullAuthorConverterBook;
import com.example.books.authors.web.AuthorsBaseReq;
import com.example.books.authors.web.AuthorsView;
import com.example.books.base.BaseRequest;
import com.example.books.util.MessageUtil;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthorsService {
    private final AuthorsRepository authorsRepository;
    private final AuthorsToAuthorsViewConverter authorsToAuthorsViewConverter;
    private final FullAuthorConverterBook fullAuthorConverterBook;
    private final MessageUtil messageUtil;
    private final BooksRepository booksRepository;

    public AuthorsService(AuthorsRepository authorsRepository, AuthorsToAuthorsViewConverter authorsToAuthorsViewConverter, FullAuthorConverterBook fullAuthorConverterBook, MessageUtil messageUtil, BooksRepository booksRepository) {
        this.authorsRepository = authorsRepository;
        this.authorsToAuthorsViewConverter = authorsToAuthorsViewConverter;
        this.fullAuthorConverterBook = fullAuthorConverterBook;
        this.messageUtil = messageUtil;
        this.booksRepository = booksRepository;
    }

    //SEARCH_AUTHORS
    public Page<AuthorsView> findAuthorsByLastName(String lastName, Pageable pageable){
        Page<Authors> authors  = authorsRepository.findByLastNameLikeIgnoreCase("%"+lastName+"%", pageable);
        List<AuthorsView> authorsViews = new ArrayList<>();
        authors.forEach(author -> {
            AuthorsView authorsView = fullAuthorConverterBook.convert(author);
            authorsViews.add(authorsView);
        });
        return new PageImpl<>(authorsViews, pageable, authors.getTotalElements());
    }


    //GET_ID
    public AuthorsView getAuthor(Long id) {
        Authors authors = findAuthorsOrThrow(id);
        return authorsToAuthorsViewConverter.convert(authors);
    }

    //GET_ID_Full
    public AuthorsView getAuthorFull(Long id) {
        Authors authors = findAuthorsOrThrow(id);
        return fullAuthorConverterBook.convert(authors);
    }

    //Search_GET_ID
    public Authors findAuthorsOrThrow(Long id) {
        return authorsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageUtil.getMessage("author.NotFound", id)));
    }

    //GET_ALL
    public Page<AuthorsView> findAllAuthors(Pageable pageable){
        Page<Authors> authors = authorsRepository.findAll(pageable);
        List<AuthorsView> authorsViews = new ArrayList<>();
        authors.forEach(author -> {
            AuthorsView authorsView = authorsToAuthorsViewConverter.convert(author);
            authorsViews.add(authorsView);
        });
        return new PageImpl<>(authorsViews, pageable, authors.getTotalElements());
    }

    //FULL_GET_ALL
    public Page<AuthorsView> findAllAuthorsFull(Pageable pageable){
        Page<Authors> authors = authorsRepository.findAll(pageable);
        List<AuthorsView> authorsViews = new ArrayList<>();
        authors.forEach(author -> {
            AuthorsView authorsView = fullAuthorConverterBook.convert(author);
            authorsViews.add(authorsView);
        });
        return new PageImpl<>(authorsViews, pageable, authors.getTotalElements());
    }


    //CREATE
    public AuthorsView create(AuthorsBaseReq req) {
        Authors authors = new Authors();
        this.prepare(authors, req);
        Authors authorSave = authorsRepository.save(authors);
        return fullAuthorConverterBook.convert(authorSave);
    }

    @Transactional
    //DELETE
    public void delete(Long id) {
        try {
            authorsRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(messageUtil.getMessage("author.NotFound", id));
        }
    }

    //UPDATE
    public AuthorsView update(Authors authors, AuthorsBaseReq req) {
        Authors newAuthor = this.prepare(authors,req);
        Authors authorSave = authorsRepository.save(newAuthor);
        return authorsToAuthorsViewConverter.convert(authorSave);
    }

    private Authors prepare(Authors authors, AuthorsBaseReq authorsBaseReq){
            authors.setLastName(authorsBaseReq.getLastName());
            authors.setCity(authorsBaseReq.getCity());
            authors.setDateOfBirth(authorsBaseReq.getDateOfBirth());
         List<Books> booksList = booksRepository.findAllById(authorsBaseReq.getBooks()
                .stream()
                .map(BaseRequest.Id::getId)
                .collect(Collectors.toSet()));
        Set<Books> booksSet = new HashSet<>(booksList);
        authors.setBooks(booksSet);
        return authors;
    }
}
