package com.spyj.estore.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDto {
    private Long id;
    private String title;
    private Double price;
    private String category;
    private String description;
    private String image;
}
