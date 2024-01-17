package com.spyj.estore.Services;

import com.spyj.estore.DTOs.ProductDTO;
import com.spyj.estore.Models.Category;
import com.spyj.estore.Models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
public class FakeStoreProductService implements ProductService{
    private final RestTemplate restTemplate;

    @Autowired
    public FakeStoreProductService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getProductDetails(Long id) {
        ProductDTO productDTO = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + id,
                ProductDTO.class
        );
        return convertProductDTOtoProduct(productDTO);
    }

    @Override
    public List<Product> getAllProducts() {
        List<ProductDTO> productDTOList = List.of(Objects.requireNonNull(restTemplate.getForEntity(
                "https://fakestoreapi.com/products",
                ProductDTO[].class
        ).getBody()));

        return productDTOList.stream().map(this::convertProductDTOtoProduct).toList();
    }

    public Product convertProductDTOtoProduct(ProductDTO productDTO){
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setTitle(productDTO.getTitle());
        product.setPrice(productDTO.getPrice());
        product.setCategory(new Category(null, productDTO.getCategory()));
        product.setDescription(productDTO.getDescription());
        product.setImage(productDTO.getImage());

        return product;
    }
}
