package com.example.books;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude={SecurityAutoConfiguration.class})

public class BooksProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(BooksProjectApplication.class, args);
    }
}
