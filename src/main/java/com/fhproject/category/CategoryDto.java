package com.fhproject.category;

import javax.validation.constraints.NotNull;

public class CategoryDto {

    private int id;
    private String categoryName;


    private CategoryDto(int id, String categoryName){
        this.id = id;
        this.categoryName = categoryName;
    }

    public static CategoryDto of(@NotNull Category category){
        return new CategoryDto(category.getID(), category.getCategoryName());
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getCategoryName() {return categoryName;}

    public void setCategoryName(String categoryName) {this.categoryName = categoryName;}


}
