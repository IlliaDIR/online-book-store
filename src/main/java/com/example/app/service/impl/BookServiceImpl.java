package com.example.app.service.impl;

import com.example.app.dto.book.BookDto;
import com.example.app.dto.book.BookDtoWithoutCategoryIds;
import com.example.app.dto.book.BookSearchParameters;
import com.example.app.dto.book.CreateBookRequestDto;
import com.example.app.exception.EntityNotFoundException;
import com.example.app.mapper.BookMapper;
import com.example.app.model.Book;
import com.example.app.model.Category;
import com.example.app.repository.book.BookRepository;
import com.example.app.repository.book.BookSpecificationBuilder;
import com.example.app.repository.category.CategoryRepository;
import com.example.app.service.BookService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Page<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).map(bookMapper::toDto);
    }

    @Override
    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Unable to find book by id: " + id));
        return bookMapper.toDto(book);
    }

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        List<Category> categoriesByIds = categoryRepository
                .findAllById(requestDto.getCategoryIds());
        book.setCategories(new HashSet<>(categoriesByIds));
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public BookDto update(Long id, CreateBookRequestDto requestDto) {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Unable to find book by id: " + id));

        Set<Category> categoryIds = categoryRepository
                .findAllById(requestDto.getCategoryIds()).stream()
                .collect(Collectors.toSet());

        book.setCategories(categoryIds);
        bookMapper.updateBook(requestDto, book);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> search(BookSearchParameters searchParameters) {
        Specification<Book> bookSpecification = bookSpecificationBuilder.build(searchParameters);
        return bookRepository.findAll(bookSpecification)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public List<BookDtoWithoutCategoryIds> findAllByCategoryId(Long id) {
        return bookRepository.findAllByCategoryId(id).stream()
                .map(bookMapper::toDtoWithoutCategories)
                .toList();
    }
}
