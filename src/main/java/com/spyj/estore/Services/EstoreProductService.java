package com.spyj.estore.Services;

import com.spyj.estore.DTOs.ProductRequestDto;
import com.spyj.estore.DTOs.ProductResponseDto;
import com.spyj.estore.Exceptions.CategoryNotFoundException;
import com.spyj.estore.Exceptions.ProductNotFoundException;
import com.spyj.estore.Exceptions.ServerError;
import com.spyj.estore.Models.Category;
import com.spyj.estore.Models.Product;
import com.spyj.estore.Repository.ProductRepository;
import com.spyj.estore.Repository.Projections.ProductWithIdAndTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class EstoreProductService implements ProductService{

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    @Autowired
    public EstoreProductService(ProductRepository productRepository, CategoryService categoryService){
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    @Override
    public Product getProductDetails(Long id) throws ProductNotFoundException {
//        Projections
//        ProductWithIdAndTitle projectedProduct = productRepository.getProductWithIdAndTitle(id);
//        projectedProduct.getId();

        Optional<Product> product = productRepository.findByIdAndAndIsDeleted(id, Boolean.FALSE);
        if(product.isEmpty()){
            throw new ProductNotFoundException("Product Not found in the Database");
        }
        return product.get();
    }

    @Override
    public List<Product> getAllProducts() throws ProductNotFoundException {
        List<Product> products = productRepository.findByIsDeleted(Boolean.FALSE);
        if(products.isEmpty()){
            throw new ProductNotFoundException("Product Not found in the Database");
        }
        return products;
    }

    @Override
    public Product createProduct(ProductRequestDto productDTO) throws CategoryNotFoundException {

        Product product = new Product();
        product.setTitle(productDTO.getTitle());
        product.setDescription(productDTO.getDescription());
        product.setImage(productDTO.getImage());
        product.setPrice(productDTO.getPrice());

        Category category = categoryService.getCategoryByName(productDTO.getCategory());
        product.setCategory(category);

        return productRepository.save(product);
    }

    @Override
    public Product updateProductDetails(Long id, ProductRequestDto productDTO) throws CategoryNotFoundException, ProductNotFoundException {
        Product product = this.getProductDetails(id);
        product.setId(id);
        if(productDTO.getTitle() != null){
            product.setTitle(productDTO.getTitle());
        }
        if(productDTO.getDescription() != null){
            product.setDescription(productDTO.getDescription());
        }
        if(productDTO.getImage() != null){
            product.setImage(productDTO.getImage());
        }
        if(productDTO.getPrice() != null){
            product.setPrice(productDTO.getPrice());
        }
        if(productDTO.getCategory() != null){
            Category category = categoryService.getCategoryByName(productDTO.getCategory());
            product.setCategory(category);
        }
        return productRepository.save(product);
    }

    @Override
    public Product updateEntireProduct(Long id, ProductRequestDto productDTO) throws ProductNotFoundException, CategoryNotFoundException, ServerError {
//      Check whether the product exist or not
        this.getProductDetails(id);
        Category category = categoryService.getCategoryByName(productDTO.getCategory());

        int updatedStatus = productRepository.updateProductDetails(productDTO.getTitle(), productDTO.getDescription(), category, productDTO.getPrice(), id);
        if(updatedStatus == 0 ){
            throw new ServerError("Failed to update the product details for product id : " + id);
        }
        return productRepository.findById(id).get();
    }

    @Override
    public void deleteProduct(Long id) throws ProductNotFoundException {
//      Check whether the product exist or not
        this.getProductDetails(id);
        productRepository.deleteProductById(Boolean.TRUE, id);
    }


}
