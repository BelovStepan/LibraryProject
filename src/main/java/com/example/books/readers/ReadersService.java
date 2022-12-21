package com.example.books.readers;

import com.example.books.readers.converter.ReadersToReadersViewConverter;
import com.example.books.readers.converter.ReadersToReadersViewConverterWithAdditionLoanOfBooks;
import com.example.books.readers.converter.ReadersToReadersViewConverterWithAdditionReturnOfBooks;
import com.example.books.readers.web.ReadersBaseReq;
import com.example.books.readers.web.ReadersView;
import com.example.books.readers.web.ReadersViewNotLoanOfBooksAndReturnOfBooks;
import com.example.books.util.MessageUtil;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReadersService {

    private final ReadersRepository readersRepository;
    private final ReadersToReadersViewConverter readersToReadersViewConverter;
    private final ReadersToReadersViewConverterWithAdditionReturnOfBooks readersToReadersViewConverterReturnOfBooks;
    private final ReadersToReadersViewConverterWithAdditionLoanOfBooks readersToReadersViewConverterLoanOfBooks;
    private final MessageUtil messageUtil;

    public ReadersService(ReadersRepository readersRepository,
                          ReadersToReadersViewConverter readersToReadersViewConverter,
                          ReadersToReadersViewConverterWithAdditionReturnOfBooks readersToReadersViewConverterReturnOfBooks,
                          ReadersToReadersViewConverterWithAdditionLoanOfBooks readersToReadersViewConverterLoanOfBooks,
                          MessageUtil messageUtil) {
        this.readersRepository = readersRepository;
        this.readersToReadersViewConverter = readersToReadersViewConverter;
        this.readersToReadersViewConverterReturnOfBooks = readersToReadersViewConverterReturnOfBooks;
        this.readersToReadersViewConverterLoanOfBooks = readersToReadersViewConverterLoanOfBooks;
        this.messageUtil = messageUtil;
    }

   /*
   Формирование вывода о читателе по фамилии
    */
    public Page<ReadersViewNotLoanOfBooksAndReturnOfBooks> findReadersByLastName(String lastName, Pageable pageable){
        Page<Readers> readers  = readersRepository.findByLastNameLikeIgnoreCase("%"+lastName+"%", pageable);
        List<ReadersViewNotLoanOfBooksAndReturnOfBooks> readersViews = new ArrayList<>();
        readers.forEach(reader -> {
            ReadersViewNotLoanOfBooksAndReturnOfBooks readersView = readersToReadersViewConverter.convert(reader);
            readersViews.add(readersView);
        });
        return new PageImpl<>(readersViews, pageable, readers.getTotalElements());
    }

    /*
    Формирование вывода о читателе по имени
     */
    public Page<ReadersViewNotLoanOfBooksAndReturnOfBooks> findReadersByFirstName(String firstName, Pageable pageable){
        Page<Readers> readers  = readersRepository.findByFirstNameLikeIgnoreCase("%"+firstName+"%", pageable);
        List<ReadersViewNotLoanOfBooksAndReturnOfBooks> readersViews = new ArrayList<>();
        readers.forEach(reader -> {
            ReadersViewNotLoanOfBooksAndReturnOfBooks readersView = readersToReadersViewConverter.convert(reader);
            readersViews.add(readersView);
        });
        return new PageImpl<>(readersViews, pageable, readers.getTotalElements());
    }

    /*
    Формирование вывода о читателе по отчеству
     */
    public Page<ReadersViewNotLoanOfBooksAndReturnOfBooks> findReadersByPatronymic(String patronymic, Pageable pageable){
        Page<Readers> readers  = readersRepository.findByPatronymicLikeIgnoreCase("%"+patronymic+"%", pageable);
        List<ReadersViewNotLoanOfBooksAndReturnOfBooks> readersViews = new ArrayList<>();
        readers.forEach(reader -> {
            ReadersViewNotLoanOfBooksAndReturnOfBooks readersView = readersToReadersViewConverter.convert(reader);
            readersViews.add(readersView);
        });
        return new PageImpl<>(readersViews, pageable, readers.getTotalElements());
    }

    /*
    Формирование вывода по фамилии, имени, отчеству
    Используется LIKE для частичного ввода информации
    Регистр не учитывается
     */
    public Page<ReadersViewNotLoanOfBooksAndReturnOfBooks> findReadersByLastNameAndFirstNameAndPatronymic(String lastName, String firstName, String patronymic,  Pageable pageable){
        Page<Readers> readers  = readersRepository.findByLastNameLikeAndFirstNameLikeAndPatronymicLikeIgnoreCase("%"+lastName+"%", "%"+firstName+"%","%"+patronymic+"%", pageable);
        List<ReadersViewNotLoanOfBooksAndReturnOfBooks> readersViews = new ArrayList<>();
        readers.forEach(reader -> {
            ReadersViewNotLoanOfBooksAndReturnOfBooks readersView = readersToReadersViewConverter.convert(reader);
            readersViews.add(readersView);
        });
        return new PageImpl<>(readersViews, pageable, readers.getTotalElements());
    }

    /*
    Формирование вывода всех читателей
     */
    public Page<ReadersViewNotLoanOfBooksAndReturnOfBooks> findAllReaders(Pageable pageable){
        Page<Readers> readers = readersRepository.findAll(pageable);
        List<ReadersViewNotLoanOfBooksAndReturnOfBooks> readersViewNotLoanOfBooksAndReturnOfBooks = new ArrayList<>();
        readers.forEach(reader -> {
            ReadersViewNotLoanOfBooksAndReturnOfBooks readersViewNotLoanOfBooksAndReturnOfBook = readersToReadersViewConverter.convert(reader);
            readersViewNotLoanOfBooksAndReturnOfBooks.add(readersViewNotLoanOfBooksAndReturnOfBook);
        });
        return new PageImpl<>(readersViewNotLoanOfBooksAndReturnOfBooks, pageable, readers.getTotalElements());
    }

    /*
    Формирование вывода читателя по id
     */
    public ReadersViewNotLoanOfBooksAndReturnOfBooks getReaders(Long id) {
        Readers readers = findReadersOrThrow(id);
        return readersToReadersViewConverter.convert(readers);
    }

    /*
    Формирование вывода читателя с возвратами по id
     */
    public ReadersView getReturnReaders(Long id) {
        Readers readers = findReadersOrThrow(id);
        return readersToReadersViewConverterReturnOfBooks.convert(readers);
    }

    /*
    Формирование вывода читателя с выдачами по id
     */
    public ReadersView getLoanOfBooksReaders(Long id) {
        Readers readers = findReadersOrThrow(id);
        return readersToReadersViewConverterLoanOfBooks.convert(readers);
    }

    /*
    Поиск id
     */
    public Readers findReadersOrThrow(Long id) {
        return readersRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageUtil.getMessage("readers.NotFound", id)));
    }

    /*
    Создание записи о читателе
     */
    public ReadersViewNotLoanOfBooksAndReturnOfBooks create(ReadersBaseReq req) {
        Readers readers = new Readers();
        this.prepare(readers, req);
        Readers readersSave = readersRepository.save(readers);
        return readersToReadersViewConverter.convert(readersSave);
    }

    /*
    Удаление записи о читателе
     */
    @Transactional
    public void delete(Long id) {
        try {
            readersRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(messageUtil.getMessage("readers.NotFound", id));
        }
    }

    /*
    Обновление записи о читателе
     */
    public ReadersViewNotLoanOfBooksAndReturnOfBooks update(Readers readers, ReadersBaseReq req) {
        Readers newReaders = this.prepare(readers,req);
        Readers readersSave = readersRepository.save(newReaders);
        return readersToReadersViewConverter.convert(readersSave);
    }

    /*
    Преобразование данных
     */
    private Readers prepare(Readers readers, ReadersBaseReq readersBaseReq){
        readers.setLastName(readersBaseReq.getLastName());
        readers.setFirstName(readersBaseReq.getFirstName());
        readers.setPatronymic(readersBaseReq.getPatronymic());
        readers.setPhoneNumber(readersBaseReq.getPhoneNumber());
        readers.setPassportData(readersBaseReq.getPassportData());
        readers.setDateOfBirth(readersBaseReq.getDateOfBirth());
        return readers;
    }
}
