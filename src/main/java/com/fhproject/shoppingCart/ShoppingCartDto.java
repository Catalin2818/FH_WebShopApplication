package com.fhproject.shoppingCart;

import com.fhproject.cardProduct.CardProduct;
import com.fhproject.product.Product;
import com.fhproject.user.User;

import javax.validation.constraints.NotNull;
import java.util.List;

public class ShoppingCartDto {

   private long id;
   private User user;
   private List<CardProduct> products;
   private int productQuantity;
   private boolean finished;

   private ShoppingCartDto(long id,User user, List<CardProduct> products,int productQuantity,  boolean finished){
       this.id = id;
       this.user = user;
       this.products = products;
       this.productQuantity = productQuantity;
       this.finished = finished;
   }

   public static ShoppingCartDto of(@NotNull ShoppingCart shoppingCart){
       return new ShoppingCartDto(shoppingCart.getId(), shoppingCart.getUser(), shoppingCart.getProducts(),
               shoppingCart.getProductQuantity(), shoppingCart.isFinished());
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

    public List<CardProduct> getProducts() {
        return products;
    }

    public void setProducts(List<CardProduct> products) {
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
}
