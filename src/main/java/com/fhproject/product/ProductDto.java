package com.fhproject.product;

import javax.validation.constraints.NotNull;

public class ProductDto {

    private int id;
    private String productName;
    private String productOrigin;
    private String productDesc;
    private String productAllergens;
    private double productPrice;
    private int productQuantity;
    private String productCategory;
    private String productPickup;
    private byte[] image;

    private ProductDto(int id, String productName, String productOrigin,
                       String productDesc,String productAllergens, double productPrice,
                       int productQuantity, String productCategory,String productPickup, byte[] image){

                            this.id = id;
                            this.productName = productName;
                            this.productOrigin = productOrigin;
                            this.productDesc = productDesc;
                            this.productAllergens = productAllergens;
                            this.productPrice = productPrice;
                            this.productQuantity = productQuantity;
                            this.productCategory = productCategory;
                            this.productPickup = productPickup;
                            this.image = image;
    }

    public static ProductDto of(@NotNull Product product){
        return new ProductDto(product.getId(), product.getProductName(), product.getProductOrigin(),
                product.getProductDesc(), product.getProductAllergens(), product.getProductPrice(),
                product.getProductQuantity(), product.getProductCategory(), product.getProductPickedUp(), product.getImage());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductOrigin() {
        return productOrigin;
    }

    public void setProductOrigin(String productOrigin) {
        this.productOrigin = productOrigin;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getProductAllergens() {
        return productAllergens;
    }

    public void setProductAllergens(String productAllergens) {
        this.productAllergens = productAllergens;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductPickup() {
        return productPickup;
    }

    public void setProductPickup(String productPickup) {
        this.productPickup = productPickup;
    }

    public byte[] getImage() {return image;}

    public void setImage(byte[] image) {this.image = image;}
}

