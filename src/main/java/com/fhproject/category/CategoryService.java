package com.fhproject.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    public CategoryRepository repo;

    public List<Category> listAll(){return (List<Category>) repo.findAll();}

    public void save(Category category){repo.save(category);}

    public Category get(Integer id) throws  CategoryNotFoundExeption{
        Optional<Category> result = repo.findById(id);
        if (result.isPresent()){
            return result.get();
        }
        throw new CategoryNotFoundExeption("Could not find any Categories wit ID" +id);
    }

    public void delete(Integer id) throws CategoryNotFoundExeption{
        Long count = repo.countById(id);
        if (count == null || count == 0){
            throw new CategoryNotFoundExeption("Could not find any Categories wit ID" +id);
        }
        repo.deleteById(id);
    }
}
