package com.example.books.loanOfBooks.web;

import com.example.books.book.BooksRepository;
import com.example.books.issueStatus.IssueStatusRepository;
import com.example.books.loanOfBooks.LoanOfBooks;
import com.example.books.loanOfBooks.LoanOfBooksRepository;
import com.example.books.loanOfBooks.LoanOfBooksService;
import com.example.books.readers.ReadersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/loan_of_books")
public class LoanOfBooksController {
    private final LoanOfBooksRepository loanOfBooksRepository;
    private final LoanOfBooksService loanOfBooksService;
    private final BooksRepository booksRepository;
    private final ReadersRepository readersRepository;
    private final IssueStatusRepository issueStatusRepository;

    public LoanOfBooksController(LoanOfBooksRepository loanOfBooksRepository, LoanOfBooksService loanOfBooksService, BooksRepository booksRepository, ReadersRepository readersRepository, IssueStatusRepository issueStatusRepository) {
        this.loanOfBooksRepository = loanOfBooksRepository;
        this.loanOfBooksService = loanOfBooksService;
        this.booksRepository = booksRepository;
        this.readersRepository = readersRepository;
        this.issueStatusRepository = issueStatusRepository;
    }


    //GET_ID
    @GetMapping("/{id}")
    @ResponseBody
    public LoanOfBooksView getLoanOfBooks(@PathVariable Long id) {
        return loanOfBooksService.getLoanOfBooks(id);
    }

    //CREATE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public LoanOfBooksView create(@RequestBody @Valid LoanOfBooksBaseReq req) {
        return loanOfBooksService.create(req);
    }

    //DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLoanOfBooks(@PathVariable Long id){
        loanOfBooksService.delete(id);
    }

    //UPDATE
    @PutMapping("/{id}")
    public LoanOfBooksView updateLoanOfBooks(@PathVariable(name = "id") Long id,
                                             @RequestBody @Valid LoanOfBooksBaseReq req){
        LoanOfBooks loanOfBooks = loanOfBooksService.findLoanOfBooksOrThrow(id);
        return loanOfBooksService.update(loanOfBooks, req);
    }
}
