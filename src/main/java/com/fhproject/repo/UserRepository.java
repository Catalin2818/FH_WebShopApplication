package com.fhproject.repo;

import com.fhproject.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Integer> {
    public Long countById(Integer id);
    public Optional<User> findByEmail(String email);
}
