package com.fhproject.service;

import com.fhproject.entity.Product;
import com.fhproject.errorHandling.ProductNotFoundExeption;
import com.fhproject.errorHandling.StorageFileNotFoundException;
import com.fhproject.repo.ProductRepository;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {


    private final String LOCATION = "G:\\CAT\\desktop\\BackendImages\\";

    @Autowired
    public ProductRepository repo;

    public List<Product> listAll(){
        return (List<Product>) repo.findAll();
    }

    public void save(Product product){
        repo.save(product);
    }

    public Product get(Integer id) throws ProductNotFoundExeption {
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

    public Resource loadAsResource(String filename) {
        try {
            Path file = Paths.get(LOCATION).resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    public String encodeBase64(String filename){
        byte[] fileContent = new byte[0];
        try {
            fileContent = FileUtils.readFileToByteArray(new File(filename));
        } catch (IOException e) {
             return "";
        }
        return Base64.getEncoder().encodeToString(fileContent);


    }

}
