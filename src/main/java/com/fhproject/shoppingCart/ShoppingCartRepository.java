package com.fhproject.shoppingCart;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.fhproject.user.User;

public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Integer> {
    public Long countById(long id);
    public Optional<ShoppingCart> findByUser(User user);
    public Optional<ShoppingCart> findByUserAndFinished(User user, boolean finished);
}
