package com.example.books.booksReturnedCounter.web;

import com.example.books.booksReturnedCounter.BooksReturnedCounter;
import com.example.books.booksReturnedCounter.BooksReturnedCounterRepository;
import com.example.books.booksReturnedCounter.BooksReturnedCounterService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/books_returned_counter")
public class BooksReturnedCounterController {
    private final BooksReturnedCounterRepository booksReturnedCounterRepository;
    private final BooksReturnedCounterService booksReturnedCounterService;

    public BooksReturnedCounterController(BooksReturnedCounterRepository booksReturnedCounterRepository,
                                          BooksReturnedCounterService booksReturnedCounterService) {
        this.booksReturnedCounterRepository = booksReturnedCounterRepository;
        this.booksReturnedCounterService = booksReturnedCounterService;
    }


    /*
    Поиск по id
     */
    @GetMapping("/{id}")
    @ResponseBody
    public BooksReturnedCounterView getBooksReturnedCounter(@PathVariable Long id) {
        return booksReturnedCounterService.getBooksReturnedCounter(id);
    }

    /*
    Создание записи возврата книги
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public BooksReturnedCounterView create(@RequestBody @Valid BooksReturnedCounterBaseReq req) {
        return booksReturnedCounterService.create(req);
    }

    /*
    Удаление записи о возврате
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBooksReturnedCounter(@PathVariable Long id){
        booksReturnedCounterService.delete(id);
    }

    /*
    Обновление записи о возврате
     */
    @PutMapping("/{id}")
    public BooksReturnedCounterView updateBooksReturnedCounter(@PathVariable(name = "id") Long id, @RequestBody @Valid BooksReturnedCounterBaseReq req){
        BooksReturnedCounter booksReturnedCounter = booksReturnedCounterService.findBooksReturnedCounterOrThrow(id);
        return booksReturnedCounterService.update(booksReturnedCounter, req);
    }
}
