package com.fhproject.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    public ProductRepository repo;

    public List<Product> listAll(){
        return (List<Product>) repo.findAll();
    }

    public void save(Product product){
        repo.save(product);
    }

    public Product get(Integer id) throws ProductNotFoundExeption{
        Optional<Product> result = repo.findById(id);
        if(result.isPresent()){
            return result.get();
        }
        throw new ProductNotFoundExeption("Could not find any products with ID" + id);
    }

    public void delete(Integer id) throws ProductNotFoundExeption{
        Long count = repo.countById(id);
        if (count == null || count == 0){
            throw new ProductNotFoundExeption("Could not find any products with ID" + id);
        }
        repo.deleteById(id);
    }

    public List<Product> getProductsOfCategory(String category) throws ProductNotFoundExeption {
        List<Product> result = repo.findByProductCategory(category);
        if (!result.isEmpty()) {
            return result;
        }
        throw new ProductNotFoundExeption("Could not find products with category " + category);

    }
}
