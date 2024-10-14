package com.example.DrawAwesome2.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.DrawAwesome2.models.LinkProjects;
import com.example.DrawAwesome2.models.NodeProjects;

import jakarta.transaction.Transactional;


@org.springframework.stereotype.Repository
public interface NodeRepository extends JpaRepository<NodeProjects, String> {

	Optional<NodeProjects> findById(Long id);
	
	List<NodeProjects> findByUserid(Long id);
	
	@Transactional
	Long deleteById(Long id);
	
}
