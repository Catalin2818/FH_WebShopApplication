package com.fhproject.product;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Arrays;


@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(nullable = false, name = "product_name", length = 50)
    private String productName;

    @NotBlank
    @Column(nullable = false, name = "product_origin", length = 80)
    private String productOrigin;

    @NotBlank
    @Column(nullable = false, name = "product_desc", length = 200)
    private String productDesc;

    @NotBlank
    @Column(nullable = false, name = "product_allergens", length = 30)
    private String productAllergens;

    @Positive
    @Column(nullable = false, name = "product_price", length = 150)
    private double productPrice;

    @Positive
    @Column(nullable = false, name = "product_quantity", length = 250)
    private int productQuantity;

    @NotBlank
    @Column(nullable = false, name = "product_category", length = 80)
    private String productCategory;

    @NotBlank
    @Column(nullable = false, name = "product_pickedup", length = 5)
    private String productPickedUp;

    @Column(name = "product_img")
    private String image;


    public Product() {
    }

    //Private Constructor for security reasons / Validation of Object is in Method of() with @NotNull
    private Product(int id, String productName, String productOrigin, String productDesc,
                    String productAllergens, double productPrice, int productQuantity, String productCategory,
                    String productPickedUp, String image) {

                        this.id = id;
                        this.productName = productName;
                        this.productOrigin = productOrigin;
                        this.productDesc = productDesc;
                        this.productAllergens = productAllergens;
                        this.productPrice = productPrice;
                        this.productQuantity = productQuantity;
                        this.productCategory = productCategory;
                        this.productPickedUp = productPickedUp;
                        this.image = image;
    }

    public static Product of(@NotNull ProductDto productDto) {
        return new Product(productDto.getId(), productDto.getProductName(), productDto.getProductOrigin(),
                productDto.getProductDesc(), productDto.getProductAllergens(), productDto.getProductPrice(),
                productDto.getProductQuantity(), productDto.getProductCategory(), productDto.getProductPickup(), productDto.getImage());
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public void setProductPrice(double productPrice) {
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

    public String getProductPickedUp() {
        return productPickedUp;
    }

    public void setProductPickedUp(String productPickedUp) {
        this.productPickedUp = productPickedUp;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", productOrigin='" + productOrigin + '\'' +
                ", productDesc='" + productDesc + '\'' +
                ", productAllergens='" + productAllergens + '\'' +
                ", productPrice=" + productPrice +
                ", productQuantity=" + productQuantity +
                ", productCategory='" + productCategory + '\'' +
                ", productPickedUp='" + productPickedUp + '\'' +
                ", image=" + image +
                '}';
    }
}
