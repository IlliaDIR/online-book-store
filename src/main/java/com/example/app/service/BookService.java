package com.example.app.service;

import com.example.app.dto.book.BookDto;
import com.example.app.dto.book.BookDtoWithoutCategoryIds;
import com.example.app.dto.book.BookSearchParameters;
import com.example.app.dto.book.CreateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    Page<BookDto> findAll(Pageable pageable);

    BookDto getBookById(Long id);

    void deleteById(Long id);

    BookDto update(Long id, CreateBookRequestDto requestDto);

    List<BookDto> search(BookSearchParameters searchParameters);

    List<BookDtoWithoutCategoryIds> findAllByCategoryId(Long id);
}
