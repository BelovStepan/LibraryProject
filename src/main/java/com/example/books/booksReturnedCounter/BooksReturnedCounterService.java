package com.example.books.booksReturnedCounter;

import com.example.books.booksReturnedCounter.converter.BooksReturnedCounterToBooksReturnedCounterViewConverter;
import com.example.books.booksReturnedCounter.web.BooksReturnedCounterBaseReq;
import com.example.books.booksReturnedCounter.web.BooksReturnedCounterView;
import com.example.books.issueStatus.IssueStatusRepository;
import com.example.books.loanOfBooks.LoanOfBooksRepository;
import com.example.books.returnOfBooks.ReturnOfBooksRepository;
import com.example.books.util.MessageUtil;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
public class BooksReturnedCounterService {
    private final BooksReturnedCounterRepository booksReturnedCounterRepository;
    private final BooksReturnedCounterToBooksReturnedCounterViewConverter booksReturnedCounterToBooksReturnedCounterViewConverter;
    private final ReturnOfBooksRepository returnOfBooksRepository;
    private final IssueStatusRepository issueStatusRepository;
    private final LoanOfBooksRepository loanOfBooksRepository;
    private final MessageUtil messageUtil;

    public BooksReturnedCounterService(BooksReturnedCounterRepository booksReturnedCounterRepository, BooksReturnedCounterToBooksReturnedCounterViewConverter booksReturnedCounterToBooksReturnedCounterViewConverter, ReturnOfBooksRepository returnOfBooksRepository, IssueStatusRepository issueStatusRepository, LoanOfBooksRepository loanOfBooksRepository, MessageUtil messageUtil) {
        this.booksReturnedCounterRepository = booksReturnedCounterRepository;
        this.booksReturnedCounterToBooksReturnedCounterViewConverter = booksReturnedCounterToBooksReturnedCounterViewConverter;
        this.returnOfBooksRepository = returnOfBooksRepository;
        this.issueStatusRepository = issueStatusRepository;
        this.loanOfBooksRepository = loanOfBooksRepository;
        this.messageUtil = messageUtil;
    }

    //GET_ID
    public BooksReturnedCounterView getBooksReturnedCounter(Long id) {
        BooksReturnedCounter booksReturnedCounter = findBooksReturnedCounterOrThrow(id);
        return booksReturnedCounterToBooksReturnedCounterViewConverter.convert(booksReturnedCounter);
    }

    //Search_GET_ID
    public BooksReturnedCounter findBooksReturnedCounterOrThrow(Long id) {
        return booksReturnedCounterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageUtil.getMessage("booksReturnedCounter.NotFound", id)));
    }


    //CREATE
    public BooksReturnedCounterView create(BooksReturnedCounterBaseReq req) {
        BooksReturnedCounter booksReturnedCounter = new BooksReturnedCounter();
        this.prepare(booksReturnedCounter, req);
        BooksReturnedCounter booksReturnedCounterSave = booksReturnedCounterRepository.save(booksReturnedCounter);
        return booksReturnedCounterToBooksReturnedCounterViewConverter.convert(booksReturnedCounterSave);
    }

    @Transactional
    //DELETE
    public void delete(Long id) {
        try {
            booksReturnedCounterRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(messageUtil.getMessage("booksReturnedCounter.NotFound", id));
        }
    }

    //UPDATE
    public BooksReturnedCounterView update(BooksReturnedCounter booksReturnedCounter, BooksReturnedCounterBaseReq req) {
        BooksReturnedCounter newBooksReturnedCounter = this.prepare(booksReturnedCounter,req);
        BooksReturnedCounter booksReturnedCounterSave = booksReturnedCounterRepository.save(newBooksReturnedCounter);
        return booksReturnedCounterToBooksReturnedCounterViewConverter.convert(booksReturnedCounterSave);
    }

    //PREPARE
    private BooksReturnedCounter prepare(BooksReturnedCounter booksReturnedCounter, BooksReturnedCounterBaseReq booksReturnedCounterReq){
        booksReturnedCounter.setQuantityReturned(booksReturnedCounterReq.getQuantityReturned());
        booksReturnedCounter.setIssueStatus(issueStatusRepository.getOne(booksReturnedCounterReq.getIssueStatusId()));
        booksReturnedCounter.setLoanOfBooks(loanOfBooksRepository.getOne(booksReturnedCounterReq.getLoanOfBooksId()));
        booksReturnedCounter.setReturnOfBooks(returnOfBooksRepository.getOne(booksReturnedCounterReq.getReturnOfBooksId()));
        return booksReturnedCounter;
    }
}
