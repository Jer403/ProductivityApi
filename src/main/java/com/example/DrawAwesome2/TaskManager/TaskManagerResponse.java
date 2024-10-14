package com.example.DrawAwesome2.TaskManager;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskManagerResponse {
	String projects;
	String listProject;
	String listProjectName;
	String error;
	String token;

}
