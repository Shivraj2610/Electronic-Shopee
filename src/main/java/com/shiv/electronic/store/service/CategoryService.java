package com.shiv.electronic.store.service;

import com.shiv.electronic.store.custome.PageableResponse;
import com.shiv.electronic.store.dto.CategoryDto;
import com.shiv.electronic.store.entities.Category;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {

    //Create
    CategoryDto createCategory(CategoryDto categoryDto);

    //Update
    CategoryDto updateCategory(CategoryDto categoryDto, String categoryId);

    //Delete
    void deleteCategory(String categoryId);

    //Get All
    PageableResponse<CategoryDto> getAllCategory(int pageNumber, int pageSize, String sortBy, String sortDir);

    //Get Single
    CategoryDto getSingleCategory(String categoryId);
}
