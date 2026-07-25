package com.example.app.mapper;

import com.example.app.config.BookMapperConfig;
import com.example.app.dto.BookDto;
import com.example.app.dto.CreateBookRequestDto;
import com.example.app.model.Book;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = BookMapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto bookDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateBook(CreateBookRequestDto requestDto, @MappingTarget Book book);
}
