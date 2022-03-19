package com.fhproject.shoppingCart;

import com.fhproject.product.Product;
import com.fhproject.user.User;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;


@Entity
@Table(name="shoppingcart")
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /*@OneToOne
    @JoinColumn(name ="id",insertable = false,updatable = false)
    @Column
    private User user;*/

    @ManyToOne
    //@Column(name="userId", nullable = false, updatable = false)
    private User user;

    @OneToMany
    private List<Product> products;

    @Positive
    @Min(1)
    private int productQuantity;

    @Column
    private boolean finished;


    public ShoppingCart(){}

    private ShoppingCart(long id, User user, List<Product> products, int productQuantity,boolean finished){
        this.id = id;
        this.user = user;
        this.products = products;
        this.productQuantity = productQuantity;
        this.finished = finished;
    }

    public static ShoppingCart of(@NotNull ShoppingCartDto shoppingCartDto){
        return new ShoppingCart(shoppingCartDto.getId(), shoppingCartDto.getUser(),shoppingCartDto.getProducts(),
                shoppingCartDto.getProductQuantity(),shoppingCartDto.isFinished());
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }


    @Override
    public String toString() {
        return "ShoppingCart{" +
                "id=" + id +
                ", user=" + user +
                ", products=" + products +
                ", productQuantity=" + productQuantity +
                ", finished=" + finished +
                '}';
    }
}
