package com.spyj.estore.Services;

import com.spyj.estore.DTOs.FakeStoreProductResponseDto;
import com.spyj.estore.DTOs.ProductRequestDto;
import com.spyj.estore.Exceptions.ProductNotFoundException;
import com.spyj.estore.Exceptions.ServerError;
import com.spyj.estore.Models.Category;
import com.spyj.estore.Models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

@Service
public class FakeStoreProductService implements ProductService{
    private final RestTemplate restTemplate;

    @Autowired
    public FakeStoreProductService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getProductDetails(Long id) throws ProductNotFoundException {
        ResponseEntity<FakeStoreProductResponseDto> productDTO = restTemplate.getForEntity(
                "https://fakestoreapi.com/products/" +id,
                FakeStoreProductResponseDto.class
        );

        if (productDTO.getStatusCode() != HttpStatusCode.valueOf(200)){
            Logger.getLogger("Fetching product from FakeStore for id : " + id + ", throws the exception");
            Logger.getLogger("Exception Code : " + productDTO.getStatusCode());
            throw new ProductNotFoundException("Product not found for id : " + id);
        }

        if (productDTO.getBody() == null){
            throw new ProductNotFoundException("Product not found for id : " + id);
        }
        return this.convertProductDTOtoProduct(productDTO.getBody());
    }

    @Override
    public List<Product> getAllProducts() throws ProductNotFoundException {

        ResponseEntity<FakeStoreProductResponseDto[]> fakeStoreProductResponseDtoResponseEntity =  restTemplate.getForEntity(
                "https://fakestoreapi.com/products",
                FakeStoreProductResponseDto[].class
        );
        if (fakeStoreProductResponseDtoResponseEntity.getStatusCode() != HttpStatusCode.valueOf(200)){
            Logger.getLogger("Fetching products from FakeStore throws the exception.");
            Logger.getLogger("Exception Code : " + fakeStoreProductResponseDtoResponseEntity.getStatusCode());
            throw new ProductNotFoundException("Products not found");
        }
        List<FakeStoreProductResponseDto> fakeStoreProductResponseDtoList = List.of(Objects.requireNonNull(fakeStoreProductResponseDtoResponseEntity.getBody()));
        List<Product> productList = new ArrayList<>();
        for(FakeStoreProductResponseDto dto : fakeStoreProductResponseDtoList){
            productList.add(convertProductDTOtoProduct(dto));
        }
        return productList;
    }

    @Override
    public Product createProduct(ProductRequestDto productRequestDto) throws ServerError {
//
        ResponseEntity<FakeStoreProductResponseDto> productDTO = restTemplate.postForEntity(
                "https://fakestoreapi.com/products",
                productRequestDto,
                FakeStoreProductResponseDto.class
        );

        if (productDTO.getStatusCode() != HttpStatusCode.valueOf(200)){
            Logger.getLogger("Creating new Product on FakeStore thrown the exception.");
            Logger.getLogger("Exception Code : " + productDTO.getStatusCode());
            throw new ServerError("Unable to Create Product due to FakeStore Server Error.");
        }

        return this.convertProductDTOtoProduct(Objects.requireNonNull(productDTO.getBody()));
    }

    @Override
    public Product updateProductDetails(Long id, ProductRequestDto productDTO) throws ServerError {

        FakeStoreProductResponseDto fakeStoreProductResponseDto =  restTemplate.patchForObject(
                "https://fakestoreapi.com/products/" + id,
                productDTO,
                FakeStoreProductResponseDto.class
        );
        if (fakeStoreProductResponseDto == null){
            throw new ServerError("FakeStore Server error.");
        }

        return convertProductDTOtoProduct(fakeStoreProductResponseDto);
    }

    @Override
    public Product updateEntireProduct(Long id, ProductRequestDto productDTO) {
//        restTemplate.put(
//                "https://fakestoreapi.com/products/" + id,
//                productDTO
//        );

        RequestCallback requestCallback = restTemplate.httpEntityCallback(productDTO, FakeStoreProductResponseDto.class);
        HttpMessageConverterExtractor<FakeStoreProductResponseDto> responseExtractor = new HttpMessageConverterExtractor(FakeStoreProductResponseDto.class, restTemplate.getMessageConverters());
        FakeStoreProductResponseDto fakeStoreProductResponseDto =  restTemplate.execute("https://fakestoreapi.com/products/" + id, HttpMethod.PUT, requestCallback, responseExtractor);
        return convertProductDTOtoProduct(Objects.requireNonNull(fakeStoreProductResponseDto));
    }

    @Override
    public void deleteProduct(Long id) {
        restTemplate.delete(
                "https://fakestoreapi.com/products/" + id
        );
    }

    public Product convertProductDTOtoProduct(FakeStoreProductResponseDto productDTO){
        Product product = new Product();
        Category category = new Category();
        category.setCategoryName(productDTO.getCategory());
        product.setId(productDTO.getId());
        product.setTitle(productDTO.getTitle());
        product.setPrice(productDTO.getPrice());
        product.setCategory(category);
        product.setDescription(productDTO.getDescription());
        product.setImage(productDTO.getImage());

        return product;
    }
}
