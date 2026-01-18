package com.example.app.service;

import com.example.app.dto.BookDto;
import com.example.app.dto.CreateBookRequestDto;
import com.example.app.model.Book;
import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<Book> findALl();

    BookDto getBookById(Long id);
}
