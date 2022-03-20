package com.fhproject.product;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product,Integer>{
    public Long countById(Integer id);
    public Optional<Product> findByProductCategory(String category);
}
