package com.example.DrawAwesome2.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.DrawAwesome2.models.LinkProjects;
import com.example.DrawAwesome2.models.Users;

import jakarta.transaction.Transactional;

@org.springframework.stereotype.Repository
public interface LinkRepository extends JpaRepository<LinkProjects, String> {
	Optional<LinkProjects> findByProjectid(Long id);
	
	@Transactional
	Long deleteByProjectid(Long id);

}