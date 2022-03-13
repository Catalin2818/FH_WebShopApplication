package com.fhproject.category;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(nullable = false, name = "category_name", length = 100)
    private String categoryName;

    public Category(){}

    private Category(int id, String categoryName){
        this.id = id;
        this.categoryName = categoryName;
    }

    public static Category of(@NotNull CategoryDto categoryDto){
        return new Category(categoryDto.getId(),categoryDto.getCategoryName());
    }

    public Integer getID() {return id;}

    public void setID(Integer ID) {this.id = id;}

    public String getCategoryName() {return categoryName;}

    public void setCategoryName(String categoryName) {this.categoryName = categoryName;}

    @Override
    public String toString() {
        return "Category{" +
                "ID=" + id +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
