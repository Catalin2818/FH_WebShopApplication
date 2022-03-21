package com.fhproject.repo;

import com.fhproject.entity.CardProduct;
import org.springframework.data.repository.CrudRepository;

public interface CardProductRepository extends CrudRepository<CardProduct, Integer> {
}
