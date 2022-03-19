package com.fhproject.cardProduct;

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

    @Column
    private int productId;

    public CardProduct(long id, int productQuantity, int shoppingCart, int productId) {
        this.id = id;
        this.productQuantity = productQuantity;
        this.shoppingCart = shoppingCart;
        this.productId = productId;
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
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CardProduct that = (CardProduct) o;
        return id == that.id && productQuantity == that.productQuantity && shoppingCart == that.shoppingCart
               && productId == that.productId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productQuantity, shoppingCart, productId);
    }


}
