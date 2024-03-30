package com.spyj.estore.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spyj.estore.DTOs.ProductRequestDto;
import com.spyj.estore.DTOs.ProductResponseDto;
import com.spyj.estore.Exceptions.CategoryNotFoundException;
import com.spyj.estore.Exceptions.ProductNotFoundException;
import com.spyj.estore.Models.Category;
import com.spyj.estore.Models.Product;
import com.spyj.estore.Services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void test_get_product() throws Exception{
        Product product = new Product();
        product.setId(23L);
        product.setTitle("IPhone13");
        product.setDescription("Best Phone ever");
        product.setImage("Phone_Photo");
        Category category = new Category();
        category.setCategoryName("Electronics");
        product.setCategory(category);
        product.setPrice(1000.0);

        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(23L);
        productResponseDto.setTitle("IPhone13");
        productResponseDto.setDescription("Best Phone ever");
        productResponseDto.setImage("Phone_Photo");
        productResponseDto.setCategory("Electronics");
        productResponseDto.setPrice(1000.0);

        when(productService.getProductDetails(23L)).
                thenReturn(product);

        mockMvc.perform(MockMvcRequestBuilders.get("/products/23")).
                andExpect(content().contentType(MediaType.APPLICATION_JSON)).
                andExpect(content().string(objectMapper.writeValueAsString(productResponseDto))).
                andExpect(status().isOk());
    }

    @Test
    public void test_get_product_not_found() throws Exception{
        when(productService.getProductDetails(anyLong())).
                thenThrow(new ProductNotFoundException("Product Not found in the Database"));

        mockMvc.perform(MockMvcRequestBuilders.get("/products/23")).
                andExpect(status().isNotFound());
    }

    @Test
    public void test_get_all_products() throws Exception{
        Product product1 = new Product();
        product1.setId(24L);
        product1.setTitle("IPhone14");
        product1.setDescription("Best Phone ever ever");
        product1.setImage("Phone_Photo2");
        Category category1 = new Category();
        category1.setCategoryName("Electronics");
        product1.setCategory(category1);
        product1.setPrice(10000.0);

        Product product2 = new Product();
        product2.setId(23L);
        product2.setTitle("IPhone13");
        product2.setDescription("Best Phone ever");
        product2.setImage("Phone_Photo");
        Category category2 = new Category();
        category2.setCategoryName("Electronics");
        product2.setCategory(category2);
        product2.setPrice(1000.0);

        ProductResponseDto productResponseDto1 = new ProductResponseDto();
        productResponseDto1.setId(24L);
        productResponseDto1.setTitle("IPhone14");
        productResponseDto1.setDescription("Best Phone ever ever");
        productResponseDto1.setImage("Phone_Photo2");
        productResponseDto1.setCategory("Electronics");
        productResponseDto1.setPrice(10000.0);

        ProductResponseDto productResponseDto2 = new ProductResponseDto();
        productResponseDto2.setId(23L);
        productResponseDto2.setTitle("IPhone13");
        productResponseDto2.setDescription("Best Phone ever");
        productResponseDto2.setImage("Phone_Photo");
        productResponseDto2.setCategory("Electronics");
        productResponseDto2.setPrice(1000.0);

        List<Product> productList = List.of(
                product1, product2
        );

        List<ProductResponseDto> productResponseDtoList = List.of(
                productResponseDto1, productResponseDto2
        );

        when(productService.getAllProducts())
                .thenReturn(productList);

        mockMvc.perform(MockMvcRequestBuilders.get("/products"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(productResponseDtoList)))
                .andExpect(status().isOk());
    }

    @Test
    public void test_get_all_empty_products() throws Exception{
        when(productService.getAllProducts())
                .thenReturn(new ArrayList<>());

        mockMvc.perform(MockMvcRequestBuilders.get("/products"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]"))
                .andExpect(status().isOk());
    }

    @Test
    public void test_create_product() throws Exception{
        Product product = new Product();
        product.setId(23L);
        product.setTitle("IPhone13");
        product.setDescription("Best Phone ever");
        product.setImage("Phone_Photo");
        Category category = new Category();
        category.setCategoryName("Electronics");
        product.setCategory(category);
        product.setPrice(1000.0);

        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(23L);
        productResponseDto.setTitle("IPhone13");
        productResponseDto.setDescription("Best Phone ever");
        productResponseDto.setImage("Phone_Photo");
        productResponseDto.setCategory("Electronics");
        productResponseDto.setPrice(1000.0);

        when(productService.createProduct(any())).
                thenReturn(product);

        mockMvc.perform(post("/products").
                contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString(productResponseDto))).
                andExpect(content().contentType(MediaType.APPLICATION_JSON)).
                andExpect(content().string(objectMapper.writeValueAsString(productResponseDto))).
                andExpect(status().isOk());
    }

    @Test
    public void test_create_product_category_not_found() throws Exception{
        ProductRequestDto productRequestDto = new ProductRequestDto();
        productRequestDto.setTitle("IPhone13");
        productRequestDto.setDescription("Best Phone ever");
        productRequestDto.setImage("Phone_Photo");
        productRequestDto.setPrice(1000.0);

        when(productService.createProduct(any(ProductRequestDto.class))).
                thenThrow(new CategoryNotFoundException("Category Not found in the Database"));

        mockMvc.perform(post("/products").
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(productRequestDto))).
                andExpect(status().isNotFound());
    }


    @Test
    public void test_update_product() throws Exception{
        Product product = new Product();
        product.setId(23L);
        product.setTitle("IPhone13");
        product.setDescription("Best Phone ever");
        product.setImage("Phone_Photo");
        Category category = new Category();
        category.setCategoryName("Electronics");
        product.setCategory(category);
        product.setPrice(1000.0);

        ProductRequestDto productRequestDto = new ProductRequestDto();
        productRequestDto.setTitle("IPhone13");
        productRequestDto.setPrice(1000.0);

        when(productService.updateProductDetails(anyLong(), any(ProductRequestDto.class))).
                thenReturn(product);

        mockMvc.perform(MockMvcRequestBuilders.patch("/products/23").
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(productRequestDto))).
                andExpect(status().isNoContent());
    }

    @Test
    public void test_update_product_not_found() throws Exception{
        ProductRequestDto productRequestDto = new ProductRequestDto();
        productRequestDto.setTitle("IPhone13");
        productRequestDto.setPrice(1000.0);

        when(productService.updateProductDetails(anyLong(), any(ProductRequestDto.class))).
                thenThrow(new ProductNotFoundException("Product Not found in the Database"));

        mockMvc.perform(MockMvcRequestBuilders.patch("/products/23").
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(productRequestDto))).
                andExpect(status().isNotFound());
    }

    @Test
    public void test_update_entire_product() throws Exception{
        Product product = new Product();
        product.setId(23L);
        product.setTitle("IPhone13");
        product.setDescription("Best Phone ever");
        product.setImage("Phone_Photo");
        Category category = new Category();
        category.setCategoryName("Electronics");
        product.setCategory(category);
        product.setPrice(1000.0);

        ProductRequestDto productRequestDto = new ProductRequestDto();
        productRequestDto.setTitle("IPhone13");
        productRequestDto.setDescription("Best Phone ever");
        productRequestDto.setImage("Phone_Photo");
        productRequestDto.setCategory("Electronics");
        productRequestDto.setPrice(1000.0);

        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(23L);
        productResponseDto.setTitle("IPhone13");
        productResponseDto.setDescription("Best Phone ever");
        productResponseDto.setImage("Phone_Photo");
        productResponseDto.setCategory("Electronics");
        productResponseDto.setPrice(1000.0);

        when(productService.updateEntireProduct(anyLong(), any(ProductRequestDto.class))).
                thenReturn(product);

        mockMvc.perform(MockMvcRequestBuilders.put("/products/23").
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(productRequestDto))).
                andExpect(content().contentType(MediaType.APPLICATION_JSON)).
                andExpect(content().string(objectMapper.writeValueAsString(productResponseDto))).
                andExpect(status().isOk());
    }

    @Test
    public void test_delete_product() throws Exception{
        doNothing().when(productService).deleteProduct(anyLong());
        mockMvc.perform(MockMvcRequestBuilders.delete("/products/23")).
                andExpect(status().isNoContent());
    }
}
