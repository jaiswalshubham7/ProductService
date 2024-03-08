package com.spyj.estore.Controllers;

import com.spyj.estore.DTOs.CategoryRequestDto;
import com.spyj.estore.Exceptions.CategoryNotFoundException;
import com.spyj.estore.Models.Category;
import com.spyj.estore.Services.CategoryService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @PostMapping("/")
    public ResponseEntity<CategoryRequestDto> createCategory(@RequestBody CategoryRequestDto categoryRequestDto){
        Category category = categoryService.createCategory(categoryRequestDto);
        CategoryRequestDto categoryResponseDto = convertCategoryIntoCategoryResponse(category);
        return new ResponseEntity<>(
                categoryResponseDto, HttpStatusCode.valueOf(200)
        );
    }
    @GetMapping("/{id}")
    public ResponseEntity<CategoryRequestDto> getCategoryDetails(@PathVariable("id") Long categoryId) throws CategoryNotFoundException {
        Category category = categoryService.getCategory(categoryId);
        CategoryRequestDto categoryRequestDto = convertCategoryIntoCategoryResponse(category);
        return new ResponseEntity<>(
                categoryRequestDto, HttpStatusCode.valueOf(200)
        );
    }

    private CategoryRequestDto convertCategoryIntoCategoryResponse(Category category){
        CategoryRequestDto categoryRequestDto = new CategoryRequestDto();
        categoryRequestDto.setId(category.getId());
        categoryRequestDto.setCategoryName(category.getCategoryName());
        categoryRequestDto.setDescription(category.getDescription());
        return categoryRequestDto;
    }
}
