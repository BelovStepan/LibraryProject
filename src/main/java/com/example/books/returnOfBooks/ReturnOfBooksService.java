package com.example.books.returnOfBooks;

import com.example.books.loanOfBooks.LoanOfBooksRepository;
import com.example.books.readers.ReadersRepository;
import com.example.books.returnOfBooks.converter.ReturnOfBooksToReturnOfBooksViewConverter;
import com.example.books.returnOfBooks.web.ReturnOfBooksBaseReq;
import com.example.books.returnOfBooks.web.ReturnOfBooksView;
import com.example.books.util.MessageUtil;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
public class ReturnOfBooksService {
    private final ReturnOfBooksRepository returnOfBooksRepository;
    private final ReturnOfBooksToReturnOfBooksViewConverter returnOfBooksToReturnOfBooksViewConverter;
    private final ReadersRepository readersRepository;
    private final LoanOfBooksRepository loanOfBooksRepository;
    private final MessageUtil messageUtil;

    public ReturnOfBooksService(ReturnOfBooksRepository returnOfBooksRepository,
                                ReturnOfBooksToReturnOfBooksViewConverter returnOfBooksToReturnOfBooksViewConverter, ReadersRepository readersRepository,
                                LoanOfBooksRepository loanOfBooksRepository, MessageUtil messageUtil) {
        this.returnOfBooksRepository = returnOfBooksRepository;
        this.returnOfBooksToReturnOfBooksViewConverter = returnOfBooksToReturnOfBooksViewConverter;
        this.readersRepository = readersRepository;
        this.loanOfBooksRepository = loanOfBooksRepository;
        this.messageUtil = messageUtil;
    }


    //GET_ID
    public ReturnOfBooksView getReturnOfBooks(Long id) {
        ReturnOfBooks returnOfBooks = findReturnOfBooksOrThrow(id);
        return returnOfBooksToReturnOfBooksViewConverter.convert(returnOfBooks);
    }

    //Search_GET_ID
    public ReturnOfBooks findReturnOfBooksOrThrow(Long id) {
        return returnOfBooksRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageUtil.getMessage("returnOfBooks.NotFound", id)));
    }


    //CREATE
    public ReturnOfBooksView create(ReturnOfBooksBaseReq req) {
        ReturnOfBooks returnOfBooks = new ReturnOfBooks();
        this.prepare(returnOfBooks, req);
        ReturnOfBooks returnOfBooksSave = returnOfBooksRepository.save(returnOfBooks);
        return returnOfBooksToReturnOfBooksViewConverter.convert(returnOfBooksSave);
    }

    @Transactional
    //DELETE
    public void delete(Long id) {
        try {
            returnOfBooksRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(messageUtil.getMessage("returnOfBooks.NotFound", id));
        }
    }

    //UPDATE
    public ReturnOfBooksView update(ReturnOfBooks returnOfBooks, ReturnOfBooksBaseReq req) {
        ReturnOfBooks newReturnOfBooks = this.prepare(returnOfBooks,req);
        ReturnOfBooks returnOfBooksSave = returnOfBooksRepository.save(newReturnOfBooks);
        return returnOfBooksToReturnOfBooksViewConverter.convert(returnOfBooksSave);
    }

    //PREPARE
    private ReturnOfBooks prepare(ReturnOfBooks returnOfBooks, ReturnOfBooksBaseReq returnOfBooksBaseReq){
        returnOfBooks.setReturnAmount(returnOfBooksBaseReq.getReturnAmount());
        returnOfBooks.setReaders(readersRepository.getOne(returnOfBooksBaseReq.getReadersId()));
        returnOfBooks.setLoanOfBooks(loanOfBooksRepository.getOne(returnOfBooksBaseReq.getExtraditionId()));
        return returnOfBooks;
    }
}
