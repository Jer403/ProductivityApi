package com.example.DrawAwesome2.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.DrawAwesome2.models.CalendarLists;
import com.example.DrawAwesome2.models.ListProjects;

import jakarta.transaction.Transactional;

@org.springframework.stereotype.Repository
public interface CalendarListRepository extends JpaRepository<CalendarLists, String> {

	Optional<CalendarLists> findById(Long id);
	
	Optional<CalendarLists> findByUseridAndDate(Long id, String date);
	
	@Transactional
	Long deleteById(Long id);
	
}
