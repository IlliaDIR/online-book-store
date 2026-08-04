package com.example.app.service.impl;

import com.example.app.dto.category.CategoryDto;
import com.example.app.dto.category.CreateCategoryRequestDto;
import com.example.app.exception.EntityNotFoundException;
import com.example.app.mapper.CategoryMapper;
import com.example.app.model.Category;
import com.example.app.repository.category.CategoryRepository;
import com.example.app.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Page<CategoryDto> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(categoryMapper::toDto);
    }

    @Override
    public CategoryDto getById(Long id) {
        return categoryMapper.toDto(
                categoryRepository.findById(id).orElseThrow(
                        () -> new EntityNotFoundException("Unable to find category with id: " + id)
                )
        );
    }

    @Override
    public CategoryDto save(CreateCategoryRequestDto categoryDto) {
        Category saved = categoryRepository.save(categoryMapper.toModel(categoryDto));
        return categoryMapper.toDto(saved);
    }

    @Override
    public CategoryDto update(Long id, CreateCategoryRequestDto categoryDto) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Unable to find category with id: " + id)
        );
        categoryMapper.updateCategory(categoryDto, category);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toDto(savedCategory);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
