package com.example.books.authors.web;

import com.example.books.authors.Authors;
import com.example.books.authors.AuthorsRepository;
import com.example.books.authors.AuthorsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/author_main")
public class AuthorsController {

    private final AuthorsService authorsService;
    private final AuthorsRepository authorsRepository;

    public AuthorsController(AuthorsService authorsService,
                             AuthorsRepository authorsRepository) {
        this.authorsService = authorsService;
        this.authorsRepository = authorsRepository;
    }

    /*
    Поиск автора по фамилии
     */
    @GetMapping("/get_author_by_lastName")
    @ResponseBody
    public Page<AuthorsView> getAuthorsByLastName(@RequestParam(required = false) String lastName, @PageableDefault(size = 5, page = 0) Pageable pageable) {
            return authorsService.findAuthorsByLastName(lastName, pageable);
        }

    /*
    Поиск автора по имени
     */
    @GetMapping("/get_author_by_firstName")
    @ResponseBody
    public Page<AuthorsView> getAuthorsByFirstName(@RequestParam(required = false) String firstName, @PageableDefault(size = 5, page = 0) Pageable pageable) {
        return authorsService.findAuthorsByFirstName(firstName, pageable);
    }

    /*
    Поиск автора по отчеству
     */
    @GetMapping("/get_author_by_patronymic")
    @ResponseBody
    public Page<AuthorsView> getAuthorsByPatronymic(@RequestParam(required = false) String patronymic, @PageableDefault(size = 5, page = 0) Pageable pageable) {
        return authorsService.findAuthorsByPatronymic(patronymic, pageable);
    }

    /*
    Поиск автора по ФИО
     */
    @GetMapping("/get_author_full")
    @ResponseBody
    public Page<AuthorsView> getByLastNameLikeAndFirstNameLikeAndPatronymic(@RequestParam(required = false)
                                                                                          String lastName,
                                                                                          String firstName,
                                                                                          String patronymic,
                                                                                          @PageableDefault(size = 5, page = 0) Pageable pageable) {
        return authorsService.findByLastNameLikeAndFirstNameLikeAndPatronymic(lastName, firstName, patronymic, pageable);
    }

    /*
    Поиск по id
     */
    @GetMapping("/{id}")
    @ResponseBody
    public AuthorsView getAuthor(@PathVariable Long id) {
        return authorsService.getAuthor(id);
    }

    /*
    Вывод всех авторов с сортировкой по фамилии
     */
    @GetMapping
    @ResponseBody
    public Page<AuthorsView> getAllAuthors(@PageableDefault(sort = "lastName", direction = Sort.Direction.ASC) Pageable pageable) {
        return authorsService.findAllAuthors(pageable);
    }

    /*
    Вывод информации о авторе вместе с его книгами
     */
    @GetMapping("/full_information_author")
    @ResponseBody
    public Page<AuthorsView> findAllAuthorsFull(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return authorsService.findAllAuthorsFull(pageable);
    }

    /*
    Вывод информации о авторе с его книгами по id
     */
    @GetMapping("/full_information_author/{id}")
    @ResponseBody
    public AuthorsView findAllAuthorsFullId(@PathVariable Long id) {
        return authorsService.getAuthorFull(id);
    }

    /*
    Добавление автора
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public AuthorsView create(@RequestBody @Valid AuthorsBaseReq req) {
        return authorsService.create(req);
    }


    /*
    Удаление автора
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthor(@PathVariable Long id){
        authorsService.delete(id);
    }

    /*
    Обновление записи об авторе
     */
    @PutMapping("/{id}")
    public AuthorsView updateAuthor(@PathVariable(name = "id") Long id,
                                    @RequestBody @Valid AuthorsBaseReq req){
        Authors author = authorsService.findAuthorsOrThrow(id);
        return authorsService.update(author, req);
    }
}
