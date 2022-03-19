package com.fhproject.shoppingCart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShoppingCartService {
    @Autowired
    public ShoppingCartRepository repo;

    public List<ShoppingCart> listAll(){return (List<ShoppingCart>) repo.findAll();}

    public void save(ShoppingCart shoppingCart){repo.save(shoppingCart);}

    public List<ShoppingCart> getByUserId(int userId) throws ShoppingCartNotFoundExeption {
        Optional<ShoppingCart> result = repo.findByUser(userId);
        return result.stream().collect(Collectors.toList());
    }

    public ShoppingCart get(Integer id) throws ShoppingCartNotFoundExeption{
        Optional<ShoppingCart> result = repo.findById(id);
        if (result.isPresent()){
            return result.get();
        }
        throw new ShoppingCartNotFoundExeption("Could not find anything ind cart with ID" +id);
    }

    public void delete(Integer id) throws ShoppingCartNotFoundExeption{
        Long count = repo.countById(id);
        if(count == null||count==0){
            throw new ShoppingCartNotFoundExeption("Could not find anything in cart with ID"+id);
        }
        repo.deleteById(id);
    }

    public List<ShoppingCart> getUnfinishedCartByUserId(int userId) throws ShoppingCartNotFoundExeption {
        Optional<ShoppingCart> result = repo.findByUserAndFinished(userId, false);
        if(result.isPresent()) {
            return result.stream().collect(Collectors.toList());
        }
        throw new ShoppingCartNotFoundExeption("Could not find unfinished cart with userId " + userId);
    }

    public List<ShoppingCart> getFinishedCartByUserId(int userId) throws ShoppingCartNotFoundExeption {
        Optional<ShoppingCart> result = repo.findByUserAndFinished(userId, true);
        if(result.isPresent()) {
            return result.stream().collect(Collectors.toList());
        }
        throw new ShoppingCartNotFoundExeption("Could not find finished cart with userId " + userId);
    }
}
