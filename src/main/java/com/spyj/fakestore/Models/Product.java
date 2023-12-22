package com.spyj.fakestore.Models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private Long id;
    private String title;
    private Double price;
    private ProductCategory category;
    private String description;
    private String image;
}
