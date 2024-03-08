package com.spyj.estore.Repository;

import com.spyj.estore.Models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Override
    Optional<Category> findById(Long aLong);
    Optional<Category> findByCategoryName(String categoryName);
}
