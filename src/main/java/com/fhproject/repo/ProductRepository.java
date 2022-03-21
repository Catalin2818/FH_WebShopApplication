package com.fhproject.repo;

import java.util.List;

import com.fhproject.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product,Integer>{
    public Long countById(Integer id);
    public List<Product> findByProductCategory(String category);
}
