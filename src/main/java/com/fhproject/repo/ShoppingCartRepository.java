package com.fhproject.repo;

import java.util.Optional;

import com.fhproject.entity.ShoppingCart;
import org.springframework.data.repository.CrudRepository;

import com.fhproject.entity.User;

public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Integer> {
    public Long countById(long id);
    public Optional<ShoppingCart> findByUser(User user);
    public Optional<ShoppingCart> findByUserAndFinished(User user, boolean finished);
}
