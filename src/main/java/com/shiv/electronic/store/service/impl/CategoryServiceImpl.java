package com.shiv.electronic.store.service.impl;

import com.shiv.electronic.store.custome.PageableResponse;
import com.shiv.electronic.store.dto.CategoryDto;
import com.shiv.electronic.store.entities.Category;
import com.shiv.electronic.store.exceptions.ResourceNotFoundException;
import com.shiv.electronic.store.helpers.Helper;
import com.shiv.electronic.store.repositories.CategoryRepository;
import com.shiv.electronic.store.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;

    //Create Category
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        String categoryId = UUID.randomUUID().toString();

        categoryDto.setCategoryId(categoryId);

        Category category = mapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return mapper.map(savedCategory, CategoryDto.class);
    }

    //Update Category
    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Category not found by Given Id: " + categoryId)
                );

        if (categoryDto.getTitle()!=null){
            category.setTitle(categoryDto.getTitle());
        }
        if(categoryDto.getDescription()!=null){
            category.setDescription(categoryDto.getDescription());
        }
        if(categoryDto.getCoverImageName()!=null){
            category.setCoverImageName(categoryDto.getCoverImageName());
        }

        return mapper.map(category, CategoryDto.class);
    }

    @Override
    public void deleteCategory(String categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Category not found by Id: " + categoryId)
                );

        categoryRepository.delete(category);
    }

    @Override
    public PageableResponse<CategoryDto> getAllCategory(
            int pageNumber,
            int pageSize,
            String sortBy,
            String sortDir
    ) {
        //Sorting Logic
        Sort sort = (sortDir.equalsIgnoreCase("asc"))
                ?Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();

        //Pageable Object
        Pageable pageable = PageRequest.of(pageNumber,pageSize, sort);

        Page<Category> page = categoryRepository.findAll(pageable);

        PageableResponse<CategoryDto> pageableResponse = Helper.getPageableResponse(page, CategoryDto.class);
        return pageableResponse;
    }

    @Override
    public CategoryDto getSingleCategory(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category not found by Id: " + categoryId)
        );

        return mapper.map(category, CategoryDto.class);
    }
}
