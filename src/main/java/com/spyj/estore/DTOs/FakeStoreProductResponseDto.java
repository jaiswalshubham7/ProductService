package com.spyj.estore.DTOs;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FakeStoreProductResponseDto {
    private Long id;
    private String title;
    private Double price;
    private String category;
    private String description;
    private String image;
}
