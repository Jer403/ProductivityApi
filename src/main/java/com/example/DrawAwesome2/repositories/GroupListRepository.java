package com.example.DrawAwesome2.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.DrawAwesome2.models.GroupListProjects;
import com.example.DrawAwesome2.models.ListProjects;

import jakarta.transaction.Transactional;

@org.springframework.stereotype.Repository
public interface GroupListRepository extends JpaRepository<GroupListProjects, String> {

	List<GroupListProjects> findByGroupid(Long id);
	
	
	@Transactional
	Long deleteById(Long id);
}
