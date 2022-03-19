package com.fhproject.user;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Email
    @NotBlank
    @Column(nullable = false, unique = true, length = 45)
    private String email;

    @Column(length = 15, nullable = false)
    private String password;

    @Column(length = 45, nullable = false, name="first_name")
    private String firstName;

    @Column(length = 45, nullable = false, name="last_name")
    private String lastName;

    @Column
    private String cart;

    @Column(length = 50, nullable = false, name="user_role")
    private String role;

    private boolean enabled;

    private boolean active;

    private boolean loggedIn;



    public User(){}

    private User(int id, String email, String password, String firstName, String lastName, String cart, String role, boolean enabled, boolean active,boolean loggedIn) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cart = cart;
        this.role = role;
        this.enabled = enabled;
        this.active = active;
        this.loggedIn = loggedIn;
    }

    private User(int id) {
        this.id = id;
    }

    public static User of(@NotNull UserDto userDto){
        return new User(userDto.getId(), userDto.getEmail(), userDto.getPassword(), userDto.getFirstName(),
                userDto.getLastName(), userDto.getCart(), userDto.getRole(), userDto.isEnabled(), userDto.isActive(), userDto.isLoggedIn());
    }

    public static User of(int id) {
        return new User(id);
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {return email;}

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isActive() {return active;}

    public void setActive(boolean active) {this.active = active;}

    public boolean isLoggedIn() {return loggedIn;}

    public void setLoggedIn(boolean loggedIn) {this.loggedIn = loggedIn;}

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", cart='" + cart + '\'' +
                ", role='" + role + '\'' +
                ", enabled=" + enabled +
                ", active=" + active +
                ", loggedIn=" + loggedIn +
                '}';
    }


}
