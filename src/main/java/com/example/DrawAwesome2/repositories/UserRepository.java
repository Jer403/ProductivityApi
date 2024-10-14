package com.example.DrawAwesome2.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.DrawAwesome2.models.Users;

@org.springframework.stereotype.Repository
public interface UserRepository extends JpaRepository<Users, String> {
	Optional<Users> findByUsername(String user_name);

	Optional<Users> findByEmail(String email);

}