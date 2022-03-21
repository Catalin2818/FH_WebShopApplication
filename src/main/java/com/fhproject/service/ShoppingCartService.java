package com.fhproject.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fhproject.entity.ShoppingCart;
import com.fhproject.errorHandling.ShoppingCartNotFoundExeption;
import com.fhproject.repo.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fhproject.entity.User;

@Service
public class ShoppingCartService {
    @Autowired
    public ShoppingCartRepository repo;

    @Autowired
    public CardProductService cardProductService;

    public List<ShoppingCart> listCompleteShoppingCart() {
        List<ShoppingCart> shoppingCartList = listAll();
        return shoppingCartList;
    }

    public List<ShoppingCart> listAll() {
        return (List<ShoppingCart>) repo.findAll();
    }

    public void save(ShoppingCart shoppingCart) {

        repo.save(shoppingCart);
        cardProductService.saveProducts(shoppingCart.getCardProducts(), shoppingCart.getId());
    }

    public List<ShoppingCart> getByUserId(User user) throws ShoppingCartNotFoundExeption {
        Optional<ShoppingCart> result = repo.findByUser(user);
        return result
                .stream()
                .collect(Collectors.toList());
    }

    public ShoppingCart get(Integer id) throws ShoppingCartNotFoundExeption {
        Optional<ShoppingCart> result = repo.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new ShoppingCartNotFoundExeption("Could not find anything ind cart with ID" + id);
    }

    public void delete(Integer id) throws ShoppingCartNotFoundExeption {
        Long count = repo.countById(id);
        if (count == null || count == 0) {
            throw new ShoppingCartNotFoundExeption("Could not find anything in cart with ID" + id);
        }
        repo.deleteById(id);
    }

    public List<ShoppingCart> getUnfinishedCartByUserId(User user) throws ShoppingCartNotFoundExeption {
        Optional<ShoppingCart> result = repo.findByUserAndFinished(user, false);
        if (result.isPresent()) {
            return result
                    .stream()
                    .collect(Collectors.toList());
        }
        throw new ShoppingCartNotFoundExeption("Could not find unfinished cart with userId " + user.getId());
    }

    public List<ShoppingCart> getFinishedCartByUserId(User user) throws ShoppingCartNotFoundExeption {
        Optional<ShoppingCart> result = repo.findByUserAndFinished(user, true);
        if (result.isPresent()) {
            return result
                    .stream()
                    .collect(Collectors.toList());
        }
        throw new ShoppingCartNotFoundExeption("Could not find finished cart with userId " + user.getId());
    }

    public void updateShoppingCart(ShoppingCart shoppingCart) throws ShoppingCartNotFoundExeption {
        Optional<ShoppingCart> result = repo.findById((int) shoppingCart.getId());
        if (result.isPresent()) {
            ShoppingCart cart = result.get();
            cart.setCardProducts(cardProductService.updateOrRemoveCardProduct(shoppingCart.getCardProducts()));
            repo.save(cart);
        }
        throw new ShoppingCartNotFoundExeption("Could not find any cart for shoppingCartId " + shoppingCart.getId());
    }


}
