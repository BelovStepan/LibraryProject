package com.example.books.loanOfBooks;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanOfBooksRepository extends JpaRepository<LoanOfBooks, Long> {
}
