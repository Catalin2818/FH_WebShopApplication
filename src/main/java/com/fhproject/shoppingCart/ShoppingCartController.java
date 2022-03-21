package com.fhproject.shoppingCart;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

import java.util.List;
import java.util.stream.Collectors;

import com.fhproject.user.User;

@CrossOrigin
@RestController
@RequestMapping("/cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/getWholeShoppingCart")
    public String showWholeCart() throws JsonProcessingException {
        List<ShoppingCart> shoppingCartList = shoppingCartService.listCompleteShoppingCart();
        System.out.println(shoppingCartList);
        List<ShoppingCartDto> dto = shoppingCartList.stream().map(cart -> ShoppingCartDto.of(cart)).collect(Collectors.toList());
        ObjectMapper objectMapper = new ObjectMapper();
        String temp = objectMapper.writeValueAsString(dto);
        System.out.println(temp);


        return temp;
    }

    @GetMapping("/getWholeShoppingCartOfUser/{id}")
    public ResponseEntity<String> showWholeCartOfUser(@PathVariable("id") int userId) {
        List<ShoppingCart> shoppingCartList = null;
        try {
            shoppingCartList = shoppingCartService.getByUserId(User.of(userId));
        } catch (ShoppingCartNotFoundExeption e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
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
                    .status(HttpStatus.NO_CONTENT)
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
                    .status(HttpStatus.NO_CONTENT)
                    .body("No finished shopping cart for userId " + userId + "exists.");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(getJsonObject(shoppingCartList));
    }

    @GetMapping("/setShoppingCartFinished/{id}")
    public ResponseEntity.BodyBuilder setFinishedCartOfUser(@PathVariable("id") int shoppingCartId) {
        try {
            ShoppingCart shoppingCart = shoppingCartService.get(shoppingCartId);
            shoppingCart.setFinished(false);
            shoppingCartService.save(shoppingCart);
        } catch (ShoppingCartNotFoundExeption e) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN);
        }
        return ResponseEntity
                .status(HttpStatus.OK);
    }

    @PostMapping(value = "/addToShoppingCart", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addToCart(@RequestBody ShoppingCartDto shoppingCartDto) {
        ShoppingCart shoppingCart = ShoppingCart.of(shoppingCartDto);
        shoppingCartService.save(shoppingCart);
        return "Added product to shopping cart.";
    }

    @PostMapping(value = "/updateShoppingCart", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateCart(@RequestBody ShoppingCartDto shoppingCartDto) {

        try {
            shoppingCartService.updateShoppingCart(ShoppingCart.of(shoppingCartDto));
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Shopping cart update was successful");

        } catch (ShoppingCartNotFoundExeption e) {
            //e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body("Shopping cart update was not successful");
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

        //System.out.println(jsonShoppingCartList.toString());
        return jsonShoppingCartList.toString();
    }

}
