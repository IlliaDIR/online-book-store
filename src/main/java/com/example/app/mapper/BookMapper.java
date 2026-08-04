package com.example.app.mapper;

import com.example.app.config.BookMapperConfig;
import com.example.app.dto.book.BookDto;
import com.example.app.dto.book.BookDtoWithoutCategoryIds;
import com.example.app.dto.book.CreateBookRequestDto;
import com.example.app.model.Book;
import com.example.app.model.Category;
import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = BookMapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto bookDto);

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateBook(CreateBookRequestDto requestDto, @MappingTarget Book book);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        List<Long> categories = book.getCategories().stream()
                .map(Category::getId)
                .toList();
        bookDto.setCategoryIds(categories);
    }
}
