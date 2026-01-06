package com.example.app.service;

import com.example.app.model.Book;
import java.util.List;

public interface BookService {
    Book save(Book book);

    List<Book> findALl();
}
