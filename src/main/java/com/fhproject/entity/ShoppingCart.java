package com.fhproject.entity;

import com.fhproject.dto.ShoppingCartDto;

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
    //@JsonManagedReference
    //@Column(name="userId", nullable = false, updatable = false)
    private User user;

    @OneToMany(mappedBy = "shoppingCart")
    //@JsonManagedReference
    private List<CardProduct> cardProducts;

    @Positive
    @Min(1)
    private int productQuantity;

    @Column
    private boolean finished;


    public ShoppingCart(){}

    private ShoppingCart(long id, User user, List<CardProduct> cardProducts, int productQuantity,boolean finished){
        this.id = id;
        this.user = user;
        this.cardProducts = cardProducts;
        this.productQuantity = productQuantity;
        this.finished = finished;
    }

    public static ShoppingCart of(@NotNull ShoppingCartDto shoppingCartDto){
        return new ShoppingCart(shoppingCartDto.getId(), shoppingCartDto.getUser(),shoppingCartDto.getCardProducts(),
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

    public List<CardProduct> getCardProducts() {
        return cardProducts;
    }

    public void setCardProducts(List<CardProduct> cardProducts) {
        this.cardProducts = cardProducts;
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
                ", cardProducts=" + cardProducts +
                ", productQuantity=" + productQuantity +
                ", finished=" + finished +
                '}';
    }
}
