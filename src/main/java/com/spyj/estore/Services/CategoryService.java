package com.spyj.estore.Services;

import com.spyj.estore.DTOs.CategoryRequestDto;
import com.spyj.estore.Exceptions.CategoryNotFoundException;
import com.spyj.estore.Models.Category;

public interface CategoryService {
    Category createCategory(CategoryRequestDto categoryRequestDto);
    Category getCategory(Long id) throws CategoryNotFoundException;
    Category getCategoryByName(String categoryName) throws CategoryNotFoundException;
}
