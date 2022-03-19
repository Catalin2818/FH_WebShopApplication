package com.fhproject.shoppingCart;


import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

import java.util.List;

import com.fhproject.user.User;
import com.fhproject.user.UserDto;

@CrossOrigin
@RestController
@RequestMapping("/cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/getWholeShoppingCart")
    public String showWholeCart() {
        List<ShoppingCart> shoppingCartList = shoppingCartService.listAll();
        return getJsonObject(shoppingCartList);
    }

    @GetMapping("/getWholeShoppingCartOfUser/{id}")
    public ResponseEntity<String> showWholeCartOfUser(@PathVariable("id") int userId) {
        List<ShoppingCart> shoppingCartList = null;
        try {
            shoppingCartList = shoppingCartService.getByUserId(User.of(userId));
        } catch (ShoppingCartNotFoundExeption e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("No shopping cart for userId " + userId + "exists.");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(getJsonObject(shoppingCartList));
    }

    @GetMapping("/getUnfinishedShoppingCartOfUser/{id}")
    public ResponseEntity<String> showUnfinishedCartOfUser(@PathVariable("id") int userId) {
        List<ShoppingCart> shoppingCartList = null;
        try {
            shoppingCartList = shoppingCartService.getUnfinishedCartByUserId(User.of(userId));
        } catch (ShoppingCartNotFoundExeption e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("No unfinished shopping cart for userId " + userId + "exists.");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(getJsonObject(shoppingCartList));
    }

    @GetMapping("/getFinishedShoppingCartOfUser/{id}")
    public ResponseEntity<String> showFinishedCartOfUser(@PathVariable("id") int userId) {
        List<ShoppingCart> shoppingCartList = null;
        try {
            shoppingCartList = shoppingCartService.getFinishedCartByUserId(User.of(userId));
        } catch (ShoppingCartNotFoundExeption e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("No finished shopping cart for userId " + userId + "exists.");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(getJsonObject(shoppingCartList));
    }

    @PostMapping(value = "/addToShoppingCart", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addToCart(@RequestBody ShoppingCartDto shoppingCartDto) {
        ShoppingCart shoppingCart = ShoppingCart.of(shoppingCartDto);
        shoppingCartService.save(shoppingCart);
        return "Added product to shopping cart.";
    }

    @PostMapping(value = "/updateShoppingCart", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String updateCart(@RequestBody ShoppingCartDto shoppingCartDto) {

        try {
            ShoppingCart shoppingCart = ShoppingCart.of(shoppingCartDto);
            ShoppingCart cartUpdate = shoppingCartService.get((int) shoppingCart.getId());
            return getJsonObject(List.of(cartUpdate));

        } catch (ShoppingCartNotFoundExeption e) {
            e.printStackTrace();
            return "Cart update was unseccessful.";
        }
    }

    @GetMapping(value = "/getSpecificCartItem/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String getSpecificItem(@PathVariable("id") int specificId) {
        try {
            ShoppingCart shoppingCart = shoppingCartService.get(specificId);
            return getJsonObject(List.of(shoppingCart));
        } catch (ShoppingCartNotFoundExeption e) {
            e.printStackTrace();
            return "Could not find specific item.";
        }
    }

    @GetMapping("/deleteCartItem/{id}")
    public String deleteCartItem(@PathVariable("id") int deleteId) {
        try {
            shoppingCartService.delete(deleteId);
            return "Deleting item was successful.";
        } catch (ShoppingCartNotFoundExeption e) {
            e.printStackTrace();
            return "Deleting item unseccessful.";
        }
    }

    @NotNull
    private String getJsonObject(List<ShoppingCart> shoppingCartList) {
        if (shoppingCartList == null) {
            return "";
        }
        JSONObject jsonShoppingCartList = new JSONObject();
        JSONArray jsonArray = new JSONArray(shoppingCartList);
        jsonShoppingCartList.put("shoppingCart", jsonArray);

        System.out.println(jsonShoppingCartList.toString());
        return jsonShoppingCartList.toString();
    }

}
