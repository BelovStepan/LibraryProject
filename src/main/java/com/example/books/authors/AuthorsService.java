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

    public AuthorsService(AuthorsRepository authorsRepository,
                          AuthorsToAuthorsViewConverter authorsToAuthorsViewConverter,
                          FullAuthorConverterBook fullAuthorConverterBook,
                          MessageUtil messageUtil,
                          BooksRepository booksRepository) {
        this.authorsRepository = authorsRepository;
        this.authorsToAuthorsViewConverter = authorsToAuthorsViewConverter;
        this.fullAuthorConverterBook = fullAuthorConverterBook;
        this.messageUtil = messageUtil;
        this.booksRepository = booksRepository;
    }

    /*
    Формирование поиск автора по фамилии
     */
    public Page<AuthorsView> findAuthorsByLastName(String lastName, Pageable pageable){
        Page<Authors> authors  = authorsRepository.findByLastNameLikeIgnoreCase("%"+lastName+"%", pageable);
        List<AuthorsView> authorsViews = new ArrayList<>();
        authors.forEach(author -> {
            AuthorsView authorsView = fullAuthorConverterBook.convert(author);
            authorsViews.add(authorsView);
        });
        return new PageImpl<>(authorsViews, pageable, authors.getTotalElements());
    }

    /*
    Формирование поиска автора по имени
     */
    public Page<AuthorsView> findAuthorsByFirstName(String firstName, Pageable pageable){
        Page<Authors> authors  = authorsRepository.findByFirstNameLikeIgnoreCase("%"+firstName+"%", pageable);
        List<AuthorsView> authorsViews = new ArrayList<>();
        authors.forEach(author -> {
            AuthorsView authorsView = fullAuthorConverterBook.convert(author);
            authorsViews.add(authorsView);
        });
        return new PageImpl<>(authorsViews, pageable, authors.getTotalElements());
    }

    /*
    Формирование поиска автора по отчеству
    */
    public Page<AuthorsView> findAuthorsByPatronymic(String patronymic, Pageable pageable){
        Page<Authors> authors  = authorsRepository.findByPatronymicLikeIgnoreCase("%"+patronymic+"%", pageable);
        List<AuthorsView> authorsViews = new ArrayList<>();
        authors.forEach(author -> {
            AuthorsView authorsView = fullAuthorConverterBook.convert(author);
            authorsViews.add(authorsView);
        });
        return new PageImpl<>(authorsViews, pageable, authors.getTotalElements());
    }

    /*
    Формирование поиска автора по ФИО
     */
    public Page<AuthorsView> findByLastNameLikeAndFirstNameLikeAndPatronymic(String lastName, String firstName, String patronymic, Pageable pageable){
        Page<Authors> authors  = authorsRepository.findByLastNameLikeAndFirstNameLikeAndPatronymicLikeIgnoreCase("%"+lastName+"%", "%"+firstName+"%","%"+patronymic+"%", pageable);
        List<AuthorsView> authorsViews = new ArrayList<>();
        authors.forEach(author -> {
            AuthorsView authorsView = fullAuthorConverterBook.convert(author);
            authorsViews.add(authorsView);
        });
        return new PageImpl<>(authorsViews, pageable, authors.getTotalElements());
    }

    /*
    Формирование информации об авторе по id
    */
    public AuthorsView getAuthor(Long id) {
        Authors authors = findAuthorsOrThrow(id);
        return authorsToAuthorsViewConverter.convert(authors);
    }

    /*
    формирование информации об авторе вместе с книгами по id
    */
    public AuthorsView getAuthorFull(Long id) {
        Authors authors = findAuthorsOrThrow(id);
        return fullAuthorConverterBook.convert(authors);
    }

    /*
    Поиск id
     */
    public Authors findAuthorsOrThrow(Long id) {
        return authorsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageUtil.getMessage("author.NotFound", id)));
    }

    /*
    Формирование информации об авторе без книг
     */
    public Page<AuthorsView> findAllAuthors(Pageable pageable){
        Page<Authors> authors = authorsRepository.findAll(pageable);
        List<AuthorsView> authorsViews = new ArrayList<>();
        authors.forEach(author -> {
            AuthorsView authorsView = authorsToAuthorsViewConverter.convert(author);
            authorsViews.add(authorsView);
        });
        return new PageImpl<>(authorsViews, pageable, authors.getTotalElements());
    }

    /*
    Формирование информации об авторе с книгами
     */
    public Page<AuthorsView> findAllAuthorsFull(Pageable pageable){
        Page<Authors> authors = authorsRepository.findAll(pageable);
        List<AuthorsView> authorsViews = new ArrayList<>();
        authors.forEach(author -> {
            AuthorsView authorsView = fullAuthorConverterBook.convert(author);
            authorsViews.add(authorsView);
        });
        return new PageImpl<>(authorsViews, pageable, authors.getTotalElements());
    }


    /*
    Создание записи об авторе
     */
    public AuthorsView create(AuthorsBaseReq req) {
        Authors authors = new Authors();
        this.prepare(authors, req);
        Authors authorSave = authorsRepository.save(authors);
        return fullAuthorConverterBook.convert(authorSave);
    }

    /*
    Поиск по id
     */
    @Transactional
    public void delete(Long id) {
        try {
            authorsRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(messageUtil.getMessage("author.NotFound", id));
        }
    }

    /*
    Обновление записи об авторе
     */
    public AuthorsView update(Authors authors, AuthorsBaseReq req) {
        Authors newAuthor = this.prepare(authors,req);
        Authors authorSave = authorsRepository.save(newAuthor);
        return authorsToAuthorsViewConverter.convert(authorSave);
    }

    /*
    Преобразование данных
     */
    private Authors prepare(Authors authors, AuthorsBaseReq authorsBaseReq){
            authors.setLastName(authorsBaseReq.getLastName());
            authors.setFirstName(authorsBaseReq.getFirstName());
            authors.setPatronymic(authorsBaseReq.getPatronymic());
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
