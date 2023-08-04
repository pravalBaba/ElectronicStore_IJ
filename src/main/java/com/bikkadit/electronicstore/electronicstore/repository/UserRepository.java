package com.bikkadit.electronicstore.electronicstore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bikkadit.electronicstore.electronicstore.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    Optional<User> findByEmail(String email);


    Optional<User> findByEmailAndPassword(String email,String password);

   // List<User> findByNamecontaining(String keyword);

}
