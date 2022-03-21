package com.fhproject.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "cardProduct")
public class CardProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @Positive
    private int productQuantity;

    @Column
    private int shoppingCart;

    @Column(name = "product_id")
    //@JsonBackReference
    private int productNumber;

    public CardProduct() {
    }

    public CardProduct(long id, int productQuantity, int shoppingCart, int productId) {
        this.id = id;
        this.productQuantity = productQuantity;
        this.shoppingCart = shoppingCart;
        this.productNumber = productId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public int getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(int shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public int getProductId() {
        return productNumber;
    }

    public void setProductId(int productId) {
        this.productNumber = productId;
    }

    @Override
    public String toString() {
        return "CardProduct{" +
                "id=" + id +
                ", productQuantity=" + productQuantity +
                ", shoppingCart=" + shoppingCart +
                ", productId=" + productNumber +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CardProduct that = (CardProduct) o;
        return id == that.id && productQuantity == that.productQuantity && shoppingCart == that.shoppingCart
               && productNumber == that.productNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productQuantity, shoppingCart, productNumber);
    }


}
