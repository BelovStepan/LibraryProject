package com.example.books.loanOfBooks;

import com.example.books.book.BooksRepository;
import com.example.books.issueStatus.IssueStatusRepository;
import com.example.books.loanOfBooks.converter.LoanOfBooksToLoanOfBooksViewConverter;
import com.example.books.loanOfBooks.web.LoanOfBooksBaseReq;
import com.example.books.loanOfBooks.web.LoanOfBooksView;
import com.example.books.readers.ReadersRepository;
import com.example.books.util.MessageUtil;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
public class LoanOfBooksService {
    private final LoanOfBooksRepository loanOfBooksRepository;
    private final LoanOfBooksToLoanOfBooksViewConverter loanOfBooksToLoanOfBooksViewConverter;
    private final BooksRepository booksRepository;
    private final ReadersRepository readersRepository;
    private final IssueStatusRepository issueStatusRepository;
    private final MessageUtil messageUtil;

    public LoanOfBooksService(LoanOfBooksRepository loanOfBooksRepository,
                              LoanOfBooksToLoanOfBooksViewConverter loanOfBooksToLoanOfBooksViewConverter, BooksRepository booksRepository, ReadersRepository readersRepository,
                              IssueStatusRepository issueStatusRepository, MessageUtil messageUtil) {
        this.loanOfBooksRepository = loanOfBooksRepository;
        this.loanOfBooksToLoanOfBooksViewConverter = loanOfBooksToLoanOfBooksViewConverter;
        this.booksRepository = booksRepository;
        this.readersRepository = readersRepository;
        this.issueStatusRepository = issueStatusRepository;
        this.messageUtil = messageUtil;
    }


    //GET_ID
    public LoanOfBooksView getLoanOfBooks(Long id) {
        LoanOfBooks loanOfBooks = findLoanOfBooksOrThrow(id);
        return loanOfBooksToLoanOfBooksViewConverter.convert(loanOfBooks);
    }

    //Search_GET_ID
    public LoanOfBooks findLoanOfBooksOrThrow(Long id) {
        return loanOfBooksRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageUtil.getMessage("loanOfBooks.NotFound", id)));
    }


    //CREATE
    public LoanOfBooksView create(LoanOfBooksBaseReq req) {
        LoanOfBooks loanOfBooks = new LoanOfBooks();
        this.prepare(loanOfBooks, req);
        LoanOfBooks LoanOfBooksSave = loanOfBooksRepository.save(loanOfBooks);
        return loanOfBooksToLoanOfBooksViewConverter.convert(LoanOfBooksSave);
    }

    @Transactional
    //DELETE
    public void delete(Long id) {
        try {
            loanOfBooksRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(messageUtil.getMessage("loanOfBooks.NotFound", id));
        }
    }

    //UPDATE
    public LoanOfBooksView update(LoanOfBooks loanOfBooks, LoanOfBooksBaseReq req) {
        LoanOfBooks newLoanOfBooks = this.prepare(loanOfBooks,req);
        LoanOfBooks loanOfBookSave = loanOfBooksRepository.save(newLoanOfBooks);
        return loanOfBooksToLoanOfBooksViewConverter.convert(loanOfBookSave);
    }

    //PREPARE
    private LoanOfBooks prepare(LoanOfBooks loanOfBooks, LoanOfBooksBaseReq loanOfBooksBaseReq){
        loanOfBooks.setDateOfIssue(loanOfBooksBaseReq.getDateOfIssue());
        loanOfBooks.setIssuancePeriod(loanOfBooksBaseReq.getIssuancePeriod());
        loanOfBooks.setNumberOfBooksGivenOut(loanOfBooksBaseReq.getNumberOfBooksGivenOut());
        loanOfBooks.setBooks(booksRepository.getOne(loanOfBooksBaseReq.getBooksId()));
        loanOfBooks.setReaders(readersRepository.getOne(loanOfBooksBaseReq.getReadersId()));
        return loanOfBooks;
    }
}
