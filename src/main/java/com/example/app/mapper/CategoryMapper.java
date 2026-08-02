package com.example.app.mapper;

import com.example.app.config.CategoryMapperConfig;
import com.example.app.dto.category.CategoryDto;
import com.example.app.dto.category.CreateCategoryRequestDto;
import com.example.app.model.Category;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = CategoryMapperConfig.class)
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    Category toModel(CreateCategoryRequestDto categoryDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateCategory(CreateCategoryRequestDto requestDto, @MappingTarget Category category);
}
