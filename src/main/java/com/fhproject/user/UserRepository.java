package com.fhproject.user;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Integer> {
    public Long countById(Integer id);
    public Optional<User> findByEmail(String email);
}
