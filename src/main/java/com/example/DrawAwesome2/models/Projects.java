package com.example.DrawAwesome2.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Projects{
	
	NodeProjects nodeProject;
	LinkProjects linkProject;
	
}

