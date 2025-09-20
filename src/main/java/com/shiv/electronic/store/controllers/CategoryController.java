package com.shiv.electronic.store.controllers;


import com.shiv.electronic.store.custome.PageableResponse;
import com.shiv.electronic.store.dto.CategoryDto;
import com.shiv.electronic.store.service.CategoryService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;


    //Create Category
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(
            @Valid @RequestBody CategoryDto categoryDto
    ){
        CategoryDto category = categoryService.createCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }


    //Update Category
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(
            @Valid @RequestBody CategoryDto categoryDto,
            @PathVariable String categoryId
    ){
        CategoryDto updatedCategoryDto = categoryService.updateCategory(categoryDto, categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(updatedCategoryDto);
    }


    //Delete Category
    @DeleteMapping("/{categoryId}")
    public void deleteCategory(
            @PathVariable String categoryId
    ){
        categoryService.deleteCategory(categoryId);
    }

    //Get All Category
    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCategories(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
        PageableResponse<CategoryDto> allCategory = categoryService.getAllCategory(pageNumber, pageSize, sortBy, sortDir);

        return new ResponseEntity<>(allCategory,HttpStatus.OK);
    }


    //Get Single Category
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getSingleCategory(
            @PathVariable String categoryId
    ){
        CategoryDto singleCategory = categoryService.getSingleCategory(categoryId);
        return ResponseEntity.ok(singleCategory);
    }
}
