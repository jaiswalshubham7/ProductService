package com.spyj.estore.Services;

import com.spyj.estore.DTOs.ProductRequestDto;
import com.spyj.estore.Exceptions.FakeStoreServerError;
import com.spyj.estore.Exceptions.ProductNotFoundException;
import com.spyj.estore.Models.Product;

import java.util.List;

public interface ProductService {
    Product getProductDetails(Long id) throws ProductNotFoundException;
    List<Product> getAllProducts() throws ProductNotFoundException;
    Product createProduct(ProductRequestDto productDTO) throws FakeStoreServerError;
    Product updateProductDetails(Long id, ProductRequestDto productDTO) throws FakeStoreServerError;
    Product updateEntireProduct(Long id, ProductRequestDto productDTO);
}
