package com.example.books.book.web;

import com.example.books.book.Books;
import com.example.books.book.BooksRepository;
import com.example.books.book.BooksService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
public class BooksController {

    private final BooksService booksService;
    private final BooksRepository booksRepository;

    public BooksController(BooksService booksService, BooksRepository booksRepository) {
        this.booksService = booksService;
        this.booksRepository = booksRepository;
    }

    //GET_BY_NAME_BOOK
    @GetMapping("book_main/")
    @ResponseBody
    public Page<BooksView> getBooksByNameBook(@RequestParam(required = false) String nameBook, @PageableDefault(size = 5, page = 0) Pageable pageable) {
        return booksService.findBooksByNameBook(nameBook, pageable);
    }

    //GET_ID
    @GetMapping("book_main/{id}")
    @ResponseBody
    public BooksView getBooks(@PathVariable Long id) {
        return booksService.getById(id);
    }

    //GET_ALL_HEADER (Заголовочная страница)
    @GetMapping
    @ResponseBody
    public Page<BooksView> findAllBooksHeader(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return booksService.findAllBooksHeader(pageable);
    }

    //GET_ALL
    @GetMapping("book_main")
    @ResponseBody
    public Page<BooksView> findAllBooks(@PageableDefault(sort = "nameBook", direction = Sort.Direction.ASC) Pageable pageable) {
        return booksService.findAllBooks(pageable);
    }

    //FULL_GET_ALL
    @GetMapping("book_main/full_information_book")
    @ResponseBody
    public Page<BooksView> findAllBooksFull(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return booksService.findAllBooksFull(pageable);
    }

    //FULL_GET_ALL_ID
    @GetMapping("book_main/full_information_book/{id}")
    @ResponseBody
    public BooksView findAllBooksFull(@PathVariable Long id) {
        return booksService.getByIdFull(id);
    }

    //CREATE
    @PostMapping("book_main")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public BooksView create(@RequestBody @Valid BooksBaseReq req) {
        return booksService.create(req);
    }


    //DELETE
    @DeleteMapping("book_main/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBooks(@PathVariable Long id){
        booksService.delete(id);
    }

    //UPDATE
    @PutMapping("book_main/{id}")
    public BooksView updateBooks(@PathVariable(name = "id") Long id,
                                   @RequestBody @Valid BooksBaseReq req){
        Books books = booksService.findBooksOrThrow(id);
        return booksService.update(books, req);
    }
}