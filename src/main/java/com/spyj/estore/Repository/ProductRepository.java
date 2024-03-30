package com.spyj.estore.Repository;

import com.spyj.estore.Models.Category;
import com.spyj.estore.Models.Product;
import com.spyj.estore.Repository.Projections.ProductWithIdAndTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product save(Product product);

    Optional<Product> findByIdAndAndIsDeleted(Long id, Boolean isDeleted);

    List<Product> findByIsDeleted(Boolean isDeleted);

    @Query("select p.id, p.title from Product p where p.id = ?1")
    ProductWithIdAndTitle getProductWithIdAndTitle(Long id);

    @Transactional
    @Modifying
    @Query("update Product p set p.title = ?1, p.description = ?2, p.category = ?3, p.price = ?4 where p.id = ?5")
    int updateProductDetails(String title, String description, Category category, Double price, Long id);

    @Transactional
    @Modifying
    @Query("update Product p set p.isDeleted = ?1 where p.id = ?2")
    int deleteProductById(Boolean isDeleted, Long id);
}
