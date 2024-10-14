package com.example.DrawAwesome2.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.DrawAwesome2.models.CalendarLists;
import com.example.DrawAwesome2.models.GroupCalendarListProjects;

import jakarta.transaction.Transactional;

@org.springframework.stereotype.Repository
public interface GroupCalendarListRepository extends JpaRepository<GroupCalendarListProjects, String> {

	Optional<GroupCalendarListProjects> findByGroupidAndDate(Long id, String date);
	
	@Transactional
	Long deleteById(Long id);
}
