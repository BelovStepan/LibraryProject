package com.example.books.book;

import com.example.books.book.converter.FullBookConverterAuthor;
import com.example.books.book.converter.BookToBookViewConverter;
import com.example.books.book.web.BooksBaseReq;
import com.example.books.authors.Authors;
import com.example.books.authors.AuthorsRepository;
import com.example.books.base.BaseRequest;
import com.example.books.util.MessageUtil;
import com.example.books.book.web.BooksView;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BooksService {

    private final BooksRepository booksRepository;
    private final AuthorsRepository authorsRepository;
    private final BookToBookViewConverter bookToBookViewConverter;
    private final MessageUtil messageUtil;
    private final FullBookConverterAuthor fullBookConverterAuthor;

    public BooksService(BooksRepository booksRepository, AuthorsRepository authorsRepository, BookToBookViewConverter bookToBookViewConverter, MessageUtil messageUtil, FullBookConverterAuthor fullBookConverterAuthor) {
        this.booksRepository = booksRepository;
        this.authorsRepository = authorsRepository;
        this.bookToBookViewConverter = bookToBookViewConverter;
        this.messageUtil = messageUtil;
        this.fullBookConverterAuthor = fullBookConverterAuthor;
    }

    //SEARCH_NAME_BOOK
    public Page<BooksView> findBooksByNameBook(String nameBook, Pageable pageable){
        Page<Books> books  = booksRepository.findByNameBookLikeIgnoreCase("%"+nameBook+"%", pageable);
        List<BooksView> booksViews = new ArrayList<>();
        books.forEach(book -> {
            BooksView booksView = fullBookConverterAuthor.convert(book);
            booksViews.add(booksView);
        });
        return new PageImpl<>(booksViews, pageable, books.getTotalElements());
    }

    //GET_ID
    public BooksView getById(Long id) {
        Books books = findBooksOrThrow(id);
        return bookToBookViewConverter.convert(books);
    }

    //Search_GET_ID
    public Books findBooksOrThrow(Long id) {
        return booksRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageUtil.getMessage("book.NotFound", id)));
    }

    //GET_ALL_HEADER
    public Page<BooksView> findAllBooksHeader(Pageable pageable){
        Page<Books> books = booksRepository.findAll(pageable);
        List<BooksView> booksViews = new ArrayList<>();
        books.forEach(book -> {
            BooksView booksView = bookToBookViewConverter.convert(book);
            booksViews.add(booksView);
        });
        return new PageImpl<>(booksViews, pageable, books.getTotalElements());
    }

    //GET_ALL
    public Page<BooksView> findAllBooks(Pageable pageable){
        Page<Books> books = booksRepository.findAll(pageable);
        List<BooksView> booksViews = new ArrayList<>();
        books.forEach(book -> {
            BooksView booksView = bookToBookViewConverter.convert(book);
            booksViews.add(booksView);
        });
        return new PageImpl<>(booksViews, pageable, books.getTotalElements());
    }

    //FULL_GET_ALL
    public Page<BooksView> findAllBooksFull(Pageable pageable){
        Page<Books> books = booksRepository.findAll(pageable);
        List<BooksView> booksViews = new ArrayList<>();
        books.forEach(book -> {
            BooksView booksView = fullBookConverterAuthor.convert(book);
            booksViews.add(booksView);
        });
        return new PageImpl<>(booksViews, pageable, books.getTotalElements());
    }

    //FULL_GET_ALL_ID
    public BooksView getByIdFull(Long id) {
        Books books = findBooksOrThrow(id);
        return fullBookConverterAuthor.convert(books);
    }

    //CREATE
    public BooksView create(BooksBaseReq req) {
        Books books = new Books();
        this.prepare(books, req);
        Books bookSave = booksRepository.save(books);
        return fullBookConverterAuthor.convert(bookSave);
    }

    @Transactional
    //DELETE
    public void delete(Long id) {
        try {
            booksRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(messageUtil.getMessage("book.NotFound", id));
        }
    }

    //UPDATE
    public BooksView update(Books book, BooksBaseReq req) {
        Books newBook = this.prepare(book,req);
        Books bookSave = booksRepository.save(newBook);
        return bookToBookViewConverter.convert(bookSave);
    }

    private Books prepare(Books books, BooksBaseReq booksBaseReq){
        books.setNameBook(booksBaseReq.getNameBook());
        books.setYearPublishing(booksBaseReq.getYearPublishing());
        books.setNumberPages(booksBaseReq.getNumberPages());
        books.setAvailability(booksBaseReq.getAvailability());
       List<Authors> authorsList = authorsRepository.findAllById(booksBaseReq.getAuthors()
                .stream()
                .map(BaseRequest.Id::getId)
                .collect(Collectors.toSet()));
        Set<Authors> authorsSet = new HashSet<>(authorsList);
        books.setAuthors(authorsSet);
        return books;
    }
}

