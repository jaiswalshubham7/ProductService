package com.spyj.estore.Services;

import com.spyj.estore.DTOs.CategoryRequestDto;
import com.spyj.estore.Exceptions.CategoryNotFoundException;
import com.spyj.estore.Models.Category;
import com.spyj.estore.Repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EstoreCategoryService implements CategoryService{

    private final CategoryRepository categoryRepository;

    public EstoreCategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }
    @Override
    public Category createCategory(CategoryRequestDto categoryRequestDto) {
        Category category = new Category();
        category.setCategoryName(categoryRequestDto.getCategoryName());
        category.setDescription(categoryRequestDto.getDescription());

        return categoryRepository.save(category);
    }

    @Override
    public Category getCategory(Long id) throws CategoryNotFoundException {
        Optional<Category> category =  categoryRepository.findById(id);
        if (category.isEmpty()){
            throw new CategoryNotFoundException("Category Not Found in Database");
        }
        return category.get();
    }
    @Override
    public Category getCategoryByName(String categoryName) throws CategoryNotFoundException {
        Optional<Category> category = categoryRepository.findByCategoryName(categoryName);
        if (category.isEmpty()){
            throw new CategoryNotFoundException("Category Not Found in Database");
        }
        return category.get();
    }
}
