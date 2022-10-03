package com.example.books.readers.web;

import com.example.books.readers.Readers;
import com.example.books.readers.ReadersRepository;
import com.example.books.readers.ReadersService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/readers")
public class ReadersController {
    private final ReadersRepository readersRepository;
    private final ReadersService readersService;

    public ReadersController(ReadersRepository readersRepository, ReadersService readersService) {
        this.readersRepository = readersRepository;
        this.readersService = readersService;
    }

    //GET_BY_LAST_NAME
    @GetMapping("/")
    @ResponseBody
    public Page<ReadersViewNotLoanOfBooksAndReturnOfBooks> getReadersByLastName(@RequestParam(required = false) String lastName, @PageableDefault(size = 5, page = 0) Pageable pageable) {
        return readersService.findReadersByLastName(lastName, pageable);
    }

    //GET_ALL_ALPHABETICALLY
    @GetMapping
    @ResponseBody
    public Page<ReadersViewNotLoanOfBooksAndReturnOfBooks> getAllReaders(@PageableDefault(sort = "lastName", direction = Sort.Direction.ASC) Pageable pageable) {
        return readersService.findAllReaders(pageable);
    }

    //GET_ID
    @GetMapping("/{id}")
    @ResponseBody
    public ReadersViewNotLoanOfBooksAndReturnOfBooks getReaders(@PathVariable Long id) {
        return readersService.getReaders(id);
    }

    //GET_ID_RETURN_READERS
    @GetMapping("/return/{id}")
    @ResponseBody
    public ReadersView getReturnReaders(@PathVariable Long id) {
        return readersService.getReturnReaders(id);
    }

    //GET_ID_LOAN_OF_BOOKS_READERS
    @GetMapping("/loan_of_books/{id}")
    @ResponseBody
    public ReadersView getLoanOfBooksReaders(@PathVariable Long id) {
        return readersService.getLoanOfBooksReaders(id);
    }

    //CREATE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ReadersViewNotLoanOfBooksAndReturnOfBooks create(@RequestBody @Valid ReadersBaseReq req) {
        return readersService.create(req);
    }

    //DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReaders(@PathVariable Long id){
        readersService.delete(id);
    }

    //UPDATE
    @PutMapping("/{id}")
    public ReadersViewNotLoanOfBooksAndReturnOfBooks updateReaders(@PathVariable(name = "id") Long id,
                                             @RequestBody @Valid ReadersBaseReq req){
        Readers readers = readersService.findReadersOrThrow(id);
        return readersService.update(readers, req);
    }
}
