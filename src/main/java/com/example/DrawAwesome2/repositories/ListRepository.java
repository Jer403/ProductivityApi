package com.example.DrawAwesome2.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.DrawAwesome2.models.ListProjects;

import jakarta.transaction.Transactional;

@org.springframework.stereotype.Repository
public interface ListRepository extends JpaRepository<ListProjects, String> {

	Optional<ListProjects> findById(Long id);
	
	List<ListProjects> findByUserid(Long id);
	
	@Transactional
	Long deleteById(Long id);
	
}
