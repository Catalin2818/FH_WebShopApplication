package com.fhproject.shoppingCart;

import org.springframework.data.repository.CrudRepository;

public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Integer> {
    public Long countById(long id);
}
