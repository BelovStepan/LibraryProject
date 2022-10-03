package com.example.books.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepository extends JpaRepository<Books, Long> {
    Page findByNameBookLikeIgnoreCase(String lastName, Pageable pageable);
}
