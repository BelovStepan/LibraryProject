package com.example.books.returnOfBooks.web;

import com.example.books.returnOfBooks.ReturnOfBooks;
import com.example.books.returnOfBooks.ReturnOfBooksRepository;
import com.example.books.returnOfBooks.ReturnOfBooksService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/return_of_books")
public class ReturnOfBooksController {
    private final ReturnOfBooksRepository returnOfBooksRepository;
    private final ReturnOfBooksService returnOfBooksService;

    public ReturnOfBooksController(ReturnOfBooksRepository returnOfBooksRepository, ReturnOfBooksService returnOfBooksService) {
        this.returnOfBooksRepository = returnOfBooksRepository;
        this.returnOfBooksService = returnOfBooksService;
    }


    //GET_ID
    @GetMapping("/{id}")
    @ResponseBody
    public ReturnOfBooksView getReturnOfBooks(@PathVariable Long id) {
        return returnOfBooksService.getReturnOfBooks(id);
    }

    //CREATE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ReturnOfBooksView create(@RequestBody @Valid ReturnOfBooksBaseReq req) {
        return returnOfBooksService.create(req);
    }

    //DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReturnOfBooks(@PathVariable Long id){
        returnOfBooksService.delete(id);
    }

    //UPDATE
    @PutMapping("/{id}")
    public ReturnOfBooksView updateReturnOfBooks(@PathVariable(name = "id") Long id,
                                             @RequestBody @Valid ReturnOfBooksBaseReq req){
        ReturnOfBooks returnOfBooks = returnOfBooksService.findReturnOfBooksOrThrow(id);
        return returnOfBooksService.update(returnOfBooks, req);
    }
}
