package com.fhproject.dto;

import com.fhproject.entity.User;

import javax.validation.constraints.NotNull;

public class UserDto {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String cart; //ShoppingCart cart;
    private String role;
    boolean enabled;
    boolean active;
    boolean loggedIn;


    public UserDto(int id, String firstName, String lastName, String email, String password, String cart, String role, boolean enabled, boolean active, boolean loggedIn) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.cart = cart;
        this.role = role;
        this.enabled = enabled;
        this.active = active;
        this.loggedIn = loggedIn;
    }

    public static UserDto of(@NotNull User user) {
        return new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getCart(),
                user.getRole(), user.isEnabled(), user.isActive(), user.isLoggedIn());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPasswort(String password) {
        this.password = password;
    }

    public String getCart() {
        return cart;
    }

    public void setCart(String cart) {
        this.cart = cart;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isEnabled() {return enabled;}

    public void setEnabled(boolean enabled) {this.enabled = enabled;}

    public boolean isActive() {return active;}

    public void setActive(boolean active) {this.active = active;}

    public boolean isLoggedIn() {return loggedIn;}

    public void setLoggedIn(boolean loggedIn) {this.loggedIn = loggedIn;}
}

