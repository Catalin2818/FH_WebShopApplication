package com.fhproject.cardProduct;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardProductService {

    @Autowired
    private CardProductRepository repo;

    public List<CardProduct> listAll() {
        return (List<CardProduct>) repo.findAll();
    }

    public void saveProducts(List<CardProduct> cardProducts, long shoppingCartId ) {
        if(cardProducts == null) {
            return;
        }
        cardProducts.forEach(cardProd -> save(cardProd, shoppingCartId));
    }
    public void save(CardProduct cardProduct, long shoppingCartId ) {
        cardProduct.setShoppingCart((int) shoppingCartId);
        repo.save(cardProduct);
    }

    public List<CardProduct> updateOrRemoveCardProduct(List<CardProduct> cardProducts) {
        Map<Integer, CardProduct> cardProductsById = cardProducts
                .stream()
                .collect(Collectors.toMap(it -> (int) it.getId(), it -> it));
        cardProducts
                .stream()
                .forEach(it -> repo.save(it));
        return cardProducts;
    }
}
