package com.spyj.estore.Controllers;

import com.spyj.estore.DTOs.ProductRequestDto;
import com.spyj.estore.DTOs.ProductResponseDto;
import com.spyj.estore.Exceptions.FakeStoreServerError;
import com.spyj.estore.Exceptions.ProductNotFoundException;
import com.spyj.estore.Models.Product;
import com.spyj.estore.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
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
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable("id") Long id) throws ProductNotFoundException {
        Product product =  productService.getProductDetails(id);
        ProductResponseDto productResponseDto = convertProductToProductResponseDto(product);
        ResponseEntity<ProductResponseDto> productResponseDtoResponseEntity = new ResponseEntity<>(
                productResponseDto, HttpStatusCode.valueOf(200)
        );
        return productResponseDtoResponseEntity;
    }

    @GetMapping()
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() throws ProductNotFoundException {
        List<Product> productList =  productService.getAllProducts();
        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();
        for (Product product : productList){
            productResponseDtoList.add(convertProductToProductResponseDto(product));
        }
        ResponseEntity<List<ProductResponseDto>> productListResponseEntity = new ResponseEntity<>(
                productResponseDtoList, HttpStatusCode.valueOf(200)
        );
        return productListResponseEntity;
    }

    @PostMapping()
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto productDTO) throws FakeStoreServerError {
        Product newProduct = productService.createProduct(productDTO);
        ProductResponseDto productResponseDto = convertProductToProductResponseDto(newProduct);
        ResponseEntity<ProductResponseDto> productResponseDtoResponseEntity = new ResponseEntity<>(
                productResponseDto, HttpStatusCode.valueOf(200)
        );
        return productResponseDtoResponseEntity;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProductDetails(@PathVariable("id") Long id, @RequestBody ProductRequestDto productDTO) throws FakeStoreServerError {
        Product updatedProduct = productService.updateProductDetails(id, productDTO);
        ProductResponseDto productResponseDto = convertProductToProductResponseDto(updatedProduct);
        ResponseEntity<ProductResponseDto> productResponseDtoResponseEntity = new ResponseEntity<>(
                productResponseDto, HttpStatusCode.valueOf(200)
        );
        return productResponseDtoResponseEntity;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateEntireProduct(@PathVariable("id") Long id, @RequestBody ProductRequestDto productDTO){
        Product updatedProduct = productService.updateEntireProduct(id, productDTO);
        ProductResponseDto productResponseDto = convertProductToProductResponseDto(updatedProduct);
        ResponseEntity<ProductResponseDto> productResponseDtoResponseEntity = new ResponseEntity<>(
                productResponseDto, HttpStatusCode.valueOf(200)
        );
        return productResponseDtoResponseEntity;
    }

    public ProductResponseDto convertProductToProductResponseDto(Product product){
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(product.getId());
        productResponseDto.setTitle(product.getTitle());
        productResponseDto.setPrice(product.getPrice());
        productResponseDto.setCategory(product.getCategory().toString());
        productResponseDto.setDescription(product.getDescription());
        productResponseDto.setImage(product.getImage());

        return productResponseDto;
    }
}
