package com.fhproject.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fhproject.shoppingCart.ShoppingCartDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired private UserService service;

    @RequestMapping(value = "/login")
    public ResponseEntity<String> logIn(@RequestParam(value="email") String email, @RequestParam(value="password") String password){

        Boolean login = false;
        User user = null;

        try {
             user = service.getUserWithEmail(email);
            if (user.getPassword().equals(password)){
                login = true;
            }
        } catch (UserNotFoundExeption e) {
            e.printStackTrace();
        }

        if (!login){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong email or password!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(user.getRole());
    }

    @PostMapping(value="/registration",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registration(@RequestBody @Valid UserDto userDto){
        User user = User.of(userDto);

        try{
            service.getUserWithEmail(user.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email already exists.");

        } catch (UserNotFoundExeption e) {
            service.save(user);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Registration seccessful.");
    }

    @GetMapping("/getAllUsers")
    public String showUserList(){
        List<User> listUsers = service.listAll();

        return getJsonObject(listUsers);
    }

    @PostMapping(value = "/addUsers",consumes = MediaType.APPLICATION_JSON_VALUE)
    public String saveUser(@RequestBody @Valid UserDto userDto){
        User user = User.of(userDto);
        service.save(user);

        return "New User added";
    }

    @PostMapping(value = "/updateUser",consumes = MediaType.APPLICATION_JSON_VALUE)
    public String updateUser(@RequestBody UserDto userDto){
        try {
            User user = User.of(userDto);

            User userUpdate= service.get(user.getId());
            return getJsonObject(List.of(userUpdate));

        } catch (UserNotFoundExeption e) {
            e.printStackTrace();
            return "Update was not successful.";
        }
    }

    @GetMapping(value = "/getSpecificUser{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public String getSpecificUser(@PathVariable("id") int specificId){
        try {
            User user = service.get(specificId);

            return getJsonObject(List.of(user));
        } catch (UserNotFoundExeption e) {
            e.printStackTrace();
            return "Coudn't find specific user.";
        }
    }

    @GetMapping("/getUserWithEmail/{email}")
    public String getUserWithEmail(@PathVariable("email") String email) {
        User user = null;
        String temp = "";
        try {
            user = service.getUserWithEmail(email);
            ObjectMapper objectMapper = new ObjectMapper();
            temp = objectMapper.writeValueAsString(user);
        } catch (UserNotFoundExeption e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return temp;
    }

    @GetMapping("/deleteUser{id}")
    public String deleteUser(@PathVariable("id") int deleteId){
        try {
                service.delete(deleteId);
                return "Delete User was successful.";
        } catch (UserNotFoundExeption e) {
            e.printStackTrace();
            return "Deleting user unseccessful.";
        }
    }

    @NotNull
    private String getJsonObject(List<User> userList) {
        JSONObject jsonUserList = new JSONObject();
        JSONArray jsonArray = new JSONArray(userList);
        jsonUserList.put("users", jsonArray);

        System.out.println(jsonUserList.toString());
        return jsonUserList.toString();
    }

}
