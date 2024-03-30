package com.spyj.estore.Controllers;

import com.spyj.estore.DTOs.ProductRequestDto;
import com.spyj.estore.DTOs.ProductResponseDto;
import com.spyj.estore.Exceptions.CategoryNotFoundException;
import com.spyj.estore.Exceptions.ProductNotFoundException;
import com.spyj.estore.Exceptions.ServerError;
import com.spyj.estore.Models.Product;
import com.spyj.estore.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable("id") Long id) throws ProductNotFoundException {
        Product product =  productService.getProductDetails(id);
        ProductResponseDto productResponseDto = convertProductToProductResponseDto(product);
        return new ResponseEntity<>(
                productResponseDto, HttpStatus.OK
        );
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() throws ProductNotFoundException {
        List<Product> productList =  productService.getAllProducts();
        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();
        for (Product product : productList){
            productResponseDtoList.add(convertProductToProductResponseDto(product));
        }
        return new ResponseEntity<>(
                productResponseDtoList, HttpStatusCode.valueOf(200)
        );
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto productDTO) throws ServerError, CategoryNotFoundException {
        Product newProduct = productService.createProduct(productDTO);
        ProductResponseDto productResponseDto = convertProductToProductResponseDto(newProduct);
        return new ResponseEntity<>(
                productResponseDto, HttpStatusCode.valueOf(200)
        );
    }

    @PatchMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ProductResponseDto> updateProductDetails(@PathVariable("id") Long id, @RequestBody ProductRequestDto productDTO) throws ServerError, CategoryNotFoundException, ProductNotFoundException {
        productService.updateProductDetails(id, productDTO);
        return new ResponseEntity<>(
                HttpStatusCode.valueOf(200)
        );
    }

    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ProductResponseDto> updateEntireProduct(@PathVariable("id") Long id, @RequestBody ProductRequestDto productDTO) throws ServerError, CategoryNotFoundException, ProductNotFoundException {
        Product updatedProduct = productService.updateEntireProduct(id, productDTO);
        ProductResponseDto productResponseDto = convertProductToProductResponseDto(updatedProduct);
        return new ResponseEntity<>(
                productResponseDto, HttpStatusCode.valueOf(200)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) throws ProductNotFoundException {
        productService.deleteProduct(id);
        return new ResponseEntity<>(
                HttpStatusCode.valueOf(204)
        );
    }

    public ProductResponseDto convertProductToProductResponseDto(Product product){
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(product.getId());
        productResponseDto.setTitle(product.getTitle());
        productResponseDto.setPrice(product.getPrice());
        productResponseDto.setCategory(product.getCategory().getCategoryName());
        productResponseDto.setDescription(product.getDescription());
        productResponseDto.setImage(product.getImage());

        return productResponseDto;
    }
}
