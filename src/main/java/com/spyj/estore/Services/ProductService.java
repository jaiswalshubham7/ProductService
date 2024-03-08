package com.spyj.estore.Services;

import com.spyj.estore.DTOs.ProductRequestDto;
import com.spyj.estore.Exceptions.CategoryNotFoundException;
import com.spyj.estore.Exceptions.ProductNotFoundException;
import com.spyj.estore.Exceptions.ServerError;
import com.spyj.estore.Models.Product;

import java.util.List;

public interface ProductService {
    Product getProductDetails(Long id) throws ProductNotFoundException;
    List<Product> getAllProducts() throws ProductNotFoundException;
    Product createProduct(ProductRequestDto productDTO) throws ServerError, CategoryNotFoundException;
    Product updateProductDetails(Long id, ProductRequestDto productDTO) throws ServerError, ProductNotFoundException, CategoryNotFoundException;
    Product updateEntireProduct(Long id, ProductRequestDto productDTO) throws ProductNotFoundException, CategoryNotFoundException, ServerError;
    void deleteProduct(Long id) throws ProductNotFoundException;
}
