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
@RequestMapping("/book_main")
public class BooksController {

    private final BooksService booksService;
    private final BooksRepository booksRepository;

    public BooksController(BooksService booksService,
                           BooksRepository booksRepository) {
        this.booksService = booksService;
        this.booksRepository = booksRepository;
    }

    /*
    Запроса с поиском по названию книги
     */
    @GetMapping("/")
    @ResponseBody
    public Page<BooksView> getBooksByNameBook(@RequestParam(required = false) String nameBook, @PageableDefault(size = 5, page = 0) Pageable pageable) {
        return booksService.findBooksByNameBook(nameBook, pageable);
    }

    /*
    Запрос с поиском книги по ее id
     */
    @GetMapping("/{id}")
    @ResponseBody
    public BooksView getBooks(@PathVariable Long id) {
        return booksService.getById(id);
    }

    /*
    Запрос с выводом книг с сортировкой по id
     */
    @GetMapping
    @ResponseBody
    public Page<BooksView> findAllBooksHeader(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return booksService.findAllBooks(pageable);
    }

    /*
    Запрос с выводом книг с сортировкой по наименованию книги
     */
    @GetMapping("/sort_nameBook")
    @ResponseBody
    public Page<BooksView> findAllBooks(@PageableDefault(sort = "nameBook", direction = Sort.Direction.ASC) Pageable pageable) {
        return booksService.findAllBooks(pageable);
    }

    /*
    Вывод книг вместе с их авторами
    Сортировка по id
     */
    @GetMapping("/full_information_book")
    @ResponseBody
    public Page<BooksView> findAllBooksFull(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return booksService.findAllBooksFull(pageable);
    }

    /*
    Вывод полной информации о книге с авторами
    Поиск по id
     */
    @GetMapping("/full_information_book/{id}")
    @ResponseBody
    public BooksView findAllBooksFull(@PathVariable Long id) {
        return booksService.getByIdFull(id);
    }

    /*
    Создание новой книги
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public BooksView create(@RequestBody @Valid BooksBaseReq req) {
        return booksService.create(req);
    }

    /*
    Удаление книги
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBooks(@PathVariable Long id){
        booksService.delete(id);
    }

    /*
    Обновление записи о книге
     */
    @PutMapping("/{id}")
    public BooksView updateBooks(@PathVariable(name = "id") Long id,
                                   @RequestBody @Valid BooksBaseReq req){
        Books books = booksService.findBooksOrThrow(id);
        return booksService.update(books, req);
    }
}