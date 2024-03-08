package com.spyj.estore.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Category extends BaseModel{
    private String categoryName;
    private String description;
    @OneToMany(mappedBy = "category")
    private List<Product> products;
//    private String categoryType;
}
