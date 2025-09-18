package com.shiv.electronic.store.repositories;

import com.shiv.electronic.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {

    //Custom Method to find User by using Email
    Optional<User> findByEmail(String email);


    //Search User By Name
    List<User> findByNameContaining(String keyword);
}
