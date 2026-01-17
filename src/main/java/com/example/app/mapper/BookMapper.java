package com.example.app.mapper;

import com.example.app.config.BookMapperConfig;
import com.example.app.dto.BookDto;
import com.example.app.dto.CreateBookRequestDto;
import com.example.app.model.Book;
import org.mapstruct.Mapper;

@Mapper(config = BookMapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto bookDto);
}
