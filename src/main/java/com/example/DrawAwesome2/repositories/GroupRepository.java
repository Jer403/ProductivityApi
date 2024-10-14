package com.example.DrawAwesome2.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.DrawAwesome2.models.Groups;
import com.example.DrawAwesome2.models.ListProjects;
import com.example.DrawAwesome2.models.Users;

@org.springframework.stereotype.Repository
public interface GroupRepository extends JpaRepository<Groups, String> {

	Optional<Groups> findByJoinlink(String link);
	List<Groups> findByUseradmin(Long id);
}
