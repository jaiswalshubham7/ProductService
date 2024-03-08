package com.spyj.estore.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequestDto {
    private Long id;
    private String categoryName;
    private String description;
}
