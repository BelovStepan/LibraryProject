package com.example.books.readers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadersRepository extends JpaRepository<Readers, Long> {
    Page findByLastNameLikeIgnoreCase(String lastName, Pageable pageable);
    Page findByFirstNameLikeIgnoreCase(String firstName, Pageable pageable);
    Page findByPatronymicLikeIgnoreCase(String patronymic, Pageable pageable);
    Page findByLastNameLikeAndFirstNameLikeAndPatronymicLikeIgnoreCase(String lastName, String firstName, String patronymic, Pageable pageable);
}
