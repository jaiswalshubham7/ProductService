package com.spyj.estore.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product extends BaseModel{
    private String title;
    private Double price;
    @ManyToOne
    private Category category;
    private String description;
    private String image;
}
