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

    public ReadersController(ReadersRepository readersRepository,
                             ReadersService readersService) {
        this.readersRepository = readersRepository;
        this.readersService = readersService;
    }

    /*
    Поиск читателя по фамилии
     */
    @GetMapping("/get_reader_by_lastName")
    @ResponseBody
    public Page<ReadersViewNotLoanOfBooksAndReturnOfBooks> getReadersByLastName(@RequestParam(required = false) String lastName, @PageableDefault(size = 5, page = 0) Pageable pageable) {
        return readersService.findReadersByLastName(lastName, pageable);
    }

    /*
    Поиск читателя по имени
     */
    @GetMapping("/get_reader_by_firstName")
    @ResponseBody
    public Page<ReadersViewNotLoanOfBooksAndReturnOfBooks> getReadersByFirstName(@RequestParam(required = false) String firstName, @PageableDefault(size = 5, page = 0) Pageable pageable) {
        return readersService.findReadersByFirstName(firstName, pageable);
    }

    /*
    Поиск читателя по отчеству
     */
    @GetMapping("/get_reader_by_patronymic")
    @ResponseBody
    public Page<ReadersViewNotLoanOfBooksAndReturnOfBooks> getReadersByPatronymic(@RequestParam(required = false) String patronymic, @PageableDefault(size = 5, page = 0) Pageable pageable) {
        return readersService.findReadersByPatronymic(patronymic, pageable);
    }

    /*
    Поиск читателя по полному ФИО
     */
    @GetMapping("/get_reader_full")
    @ResponseBody
    public Page<ReadersViewNotLoanOfBooksAndReturnOfBooks> findReadersByLastNameAndFirstNameAndPatronymic(@RequestParam(required = false)
                                                                                                          String lastName,
                                                                                                          String firstName,
                                                                                                          String patronymic,
                                                                                                          @PageableDefault(size = 5, page = 0) Pageable pageable) {
        return readersService.findReadersByLastNameAndFirstNameAndPatronymic(lastName, firstName, patronymic, pageable);
    }

    /*
    Вывод информации о читателе с сортировкой по фамилии
     */
    @GetMapping
    @ResponseBody
    public Page<ReadersViewNotLoanOfBooksAndReturnOfBooks> getAllReaders(@PageableDefault(sort = "lastName", direction = Sort.Direction.ASC) Pageable pageable) {
        return readersService.findAllReaders(pageable);
    }

    /*
    Вывод информации о читателе по id
     */
    @GetMapping("/{id}")
    @ResponseBody
    public ReadersViewNotLoanOfBooksAndReturnOfBooks getReaders(@PathVariable Long id) {
        return readersService.getReaders(id);
    }

    /*
    Вывод информации о читателе вместе с возвратами по id
     */
    @GetMapping("/return/{id}")
    @ResponseBody
    public ReadersView getReturnReaders(@PathVariable Long id) {
        return readersService.getReturnReaders(id);
    }

    /*
    Вывод информации о читателе вместе с выдачами по id
     */
    @GetMapping("/loan_of_books/{id}")
    @ResponseBody
    public ReadersView getLoanOfBooksReaders(@PathVariable Long id) {
        return readersService.getLoanOfBooksReaders(id);
    }

    /*
    Создание нового читателя
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ReadersViewNotLoanOfBooksAndReturnOfBooks create(@RequestBody @Valid ReadersBaseReq req) {
        return readersService.create(req);
    }

    /*
    Удаление читателя по id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReaders(@PathVariable Long id){
        readersService.delete(id);
    }

    /*
    Обновление информации о читателе по id
     */
    @PutMapping("/{id}")
    public ReadersViewNotLoanOfBooksAndReturnOfBooks updateReaders(@PathVariable(name = "id") Long id,
                                             @RequestBody @Valid ReadersBaseReq req){
        Readers readers = readersService.findReadersOrThrow(id);
        return readersService.update(readers, req);
    }
}
