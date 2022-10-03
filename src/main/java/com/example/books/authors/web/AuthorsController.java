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

    public AuthorsController(AuthorsService authorsService, AuthorsRepository authorsRepository) {
        this.authorsService = authorsService;
        this.authorsRepository = authorsRepository;
    }

    //GET_BY_LAST_NAME
    @GetMapping("/")
    @ResponseBody
    public Page<AuthorsView> getAuthorsByLastName(@RequestParam(required = false) String lastName, @PageableDefault(size = 5, page = 0) Pageable pageable) {
            return authorsService.findAuthorsByLastName(lastName, pageable);
        }

    //GET_ID
    @GetMapping("/{id}")
    @ResponseBody
    public AuthorsView getAuthor(@PathVariable Long id) {
        return authorsService.getAuthor(id);
    }

    //GET_ALL
    @GetMapping
    @ResponseBody
    public Page<AuthorsView> getAllAuthors(@PageableDefault(sort = "lastName", direction = Sort.Direction.ASC) Pageable pageable) {
        return authorsService.findAllAuthors(pageable);
    }

    //FULL_GET_ALL
    @GetMapping("/full_information_author")
    @ResponseBody
    public Page<AuthorsView> findAllAuthorsFull(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return authorsService.findAllAuthorsFull(pageable);
    }

    //FULL_GET_ALL_ID
    @GetMapping("/full_information_author/{id}")
    @ResponseBody
    public AuthorsView findAllAuthorsFullId(@PathVariable Long id) {
        return authorsService.getAuthorFull(id);
    }

    //CREATE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public AuthorsView create(@RequestBody @Valid AuthorsBaseReq req) {
        return authorsService.create(req);
    }


    //DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthor(@PathVariable Long id){
        authorsService.delete(id);
    }

    //UPDATE
    @PutMapping("/{id}")
    public AuthorsView updateAuthor(@PathVariable(name = "id") Long id,
                                    @RequestBody @Valid AuthorsBaseReq req){
        Authors author = authorsService.findAuthorsOrThrow(id);
        return authorsService.update(author, req);
    }
}
