package com.spyj.fakestore.Services;

import com.spyj.fakestore.Models.Product;

import java.util.List;

public interface ProductService {
    Product getProductDetails(Long id);
    List<Product> getAllProducts();
}
