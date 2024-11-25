package com.example.blog.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.blog.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
   

	Optional<User> findByEmail(String email);
}

