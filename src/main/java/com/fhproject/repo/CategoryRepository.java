package com.fhproject.repo;

import com.fhproject.entity.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category,Integer> {
    public Long countById(Integer id);
}
