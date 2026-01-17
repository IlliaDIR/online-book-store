package com.example.app.service;

import com.example.app.dto.BookDto;
import com.example.app.dto.CreateBookRequestDto;
import com.example.app.exception.EntityNotFoundException;
import com.example.app.mapper.BookMapper;
import com.example.app.model.Book;
import com.example.app.repository.BookRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<Book> findALl() {
        return bookRepository.findAll();
    }

    @Override
    public BookDto getBookById(Long id) {
        Book book = bookRepository.getBookById(id).orElseThrow(() ->
                new EntityNotFoundException("Unable to find book by id: " + id));
        return bookMapper.toDto(book);
    }
}
