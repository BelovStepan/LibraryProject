package com.example.books.readers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadersRepository extends JpaRepository<Readers, Long> {
    Page findByLastNameLikeIgnoreCase(String lastName, Pageable pageable);
}
