package com.spyj.estore.Services;

import com.spyj.estore.Models.Product;

import java.util.List;

public interface ProductService {
    Product getProductDetails(Long id);
    List<Product> getAllProducts();
}
