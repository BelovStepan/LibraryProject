package com.example.books.book.converter;

import com.example.books.book.Books;
import com.example.books.book.web.BooksView;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class BookToBookViewConverter implements Converter<Books, BooksView> {

    @Override
   public BooksView convert(@NonNull Books books) {
       BooksView view = new BooksView();
       view.setId(books.getId());
       view.setNameBook(books.getNameBook());
       view.setYearPublishing(books.getYearPublishing());
       view.setNumberPages(books.getNumberPages());
       view.setAvailability(books.getAvailability());
       return view;
   }
}
