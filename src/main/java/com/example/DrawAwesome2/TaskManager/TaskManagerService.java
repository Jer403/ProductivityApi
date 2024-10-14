package com.example.DrawAwesome2.TaskManager;


import java.util.List;

import org.springframework.stereotype.Service;

import com.example.DrawAwesome2.Jwt.JwtService;
import com.example.DrawAwesome2.ProjectManager.ProjectManagerResponse;
import com.example.DrawAwesome2.models.CalendarLists;
import com.example.DrawAwesome2.models.ListProjects;
import com.example.DrawAwesome2.models.Users;
import com.example.DrawAwesome2.repositories.CalendarListRepository;
import com.example.DrawAwesome2.repositories.ListRepository;
import com.example.DrawAwesome2.repositories.UserRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class TaskManagerService {
	
	private final UserRepository userRepository;
	private final ListRepository listRepository;
	private final CalendarListRepository calendarListRepository;
	private final JwtService jwtService;
	
	
	
	
	

	
	public TaskManagerResponse createNewList(TaskManagerRequest request) {
		String error = null;
		String ListName = "Untitled List";
		Users user;
		
		if(request.getLang().equals("es")) {
			System.out.println("Entro");
			ListName = "Lista sin nombre";
		}

		user = getUser(request);
		

		ListProjects lists = ListProjects.builder()
							.userid(user.getId())
							.projectname(ListName)
							.project(null)
						    .build();
		

		try {
			listRepository.save(lists);
		}catch(Exception e) {
			error = "repositoryCouldNotSave";
			return TaskManagerResponse.builder().error(error).build();
		}
		
		
		return TaskManagerResponse
				.builder()
				.projects(lists.getId().toString())
				.error(error)
				.build();
	}
	
	
	
	
	
	
	public TaskManagerResponse getListData(TaskManagerRequest request) {
		String error = null;
		Long id = request.getListId();
		ListProjects list;
		
		
		
		try {
			list = listRepository.findById(id).orElseThrow();
		}catch(Exception e) {
			error = "repositoryCouldNotFound";
			return TaskManagerResponse.builder().error(error).build();
		}
		
		
		
		
		return TaskManagerResponse
				.builder()
				.listProject(list.getProject())
				.listProjectName(list.getProjectname())
				.build();
		
	}
	
	
	
	
	
	
	
	public TaskManagerResponse updateList(TaskManagerRequest request) {
		String error = null;
		Long id = request.getListId();
		String listProject = request.getListProject(); 
		ListProjects list;
		
		
		try {
			list = listRepository.findById(id).orElseThrow();
		}catch(Exception e) {
			error = "listProjectNotFound";
			return TaskManagerResponse.builder().error(error).build();
		}
		
		
		

		list.setProject(listProject);
		list.setProjectname(request.getListProjectName());

		try {
			listRepository.save(list);
		}catch(Exception e) {
			error = "repositoryCouldNotSave";
			return TaskManagerResponse.builder().error(error).build();
		}
		
		
		return TaskManagerResponse
				.builder()
				.error(error)
				.build();
		
	}
	
	
	
	
	
	
	public TaskManagerResponse deleteList(TaskManagerRequest request) {
		String error = null;
		Long id = request.getListId();
		ListProjects list;
		Users user;
		
		

		user = getUser(request);
		
		
		
		try {
			list = listRepository.findById(id).orElseThrow();
		}catch(Exception e) {
			error = "repositoryCouldNotFind";
			return TaskManagerResponse
					.builder()
					.error(error)
					.build();
		}
		
		if(list.getUserid() == user.getId()) {
			try {
				listRepository.deleteById(id);
			}catch(Exception e) {
				error = "repositoryCouldNotDelete";
				return TaskManagerResponse
						.builder()
						.error(error)
						.build();
			}
		}
		
		
		
		
		return TaskManagerResponse
				.builder()
				.error(error)
				.build();
	}
	
	
	
	
	
	
	public TaskManagerResponse getAllLists(TaskManagerRequest request) {
		String error = null;
		String ids = "";
		String names = "";
		int count = 0;
		Users user;
		
		user = getUser(request);
			
		List<ListProjects> allLists = listRepository.findByUserid(user.getId());
		
		
		
		for (ListProjects list: allLists){
			if(count == 0) {
				ids = ""+list.getId();
				names = ""+list.getProjectname();
				count++;
			}
			else{
				ids += "/"+list.getId();
				names += "/"+list.getProjectname();
				count++;
			}
		}
		
		
		String token = jwtService.getToken(user, request.getTiempo());
		
		return TaskManagerResponse
				.builder()
				.projects(ids)
				.listProjectName(names)
				.error(error)
				.token(token)
				.build();
	}
	
	
	
	
	
	
	
	
	
	
	public TaskManagerResponse updateCalendarDate(TaskManagerRequest request) {
		String error = null;
		String listProject = request.getListProject(); 
		String date = request.getDate(); 
		CalendarLists list;
		Users user;	
		

		user = getUser(request);
		
		try {
			list = calendarListRepository.findByUseridAndDate(user.getId(),date).orElseThrow();
		}catch(Exception e) {
			CalendarLists newList = CalendarLists.builder()
					.project(request.getListProject())
					.userid(user.getId())
					.date(date)
					.build();
			
			calendarListRepository.save(newList);
			
			return TaskManagerResponse.builder().error(error).build();
		}
		
		
		

		list.setProject(listProject);
		calendarListRepository.save(list);
		
		
		return TaskManagerResponse
				.builder()
				.error(error)
				.build();
		
	}
	
	
	
	
	
	
	public TaskManagerResponse getCalendarDate(TaskManagerRequest request) {
		String error = null;
		String date = request.getDate();
		CalendarLists list;
		Users user;
		

		user = getUser(request);
		
		try {
			list = calendarListRepository.findByUseridAndDate(user.getId(),date).orElseThrow();
		}catch(Exception e) {
			error = "couldNotFoundAListForTheSpecifiedDate";
			return TaskManagerResponse.builder().error(error).build();
		}
		
		return TaskManagerResponse
				.builder()
				.listProject(list.getProject())
				.build();
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Users getUser( TaskManagerRequest request) {
		Users user = null;
		String username = null;
		
		try {
			username = jwtService.getUsernameFromToken(request.getToken());
		}catch(Exception e) {
			return user;
		}
		
		
		try {
			user = userRepository.findByUsername(username).orElseThrow();
		}catch(Exception e) {
			return user;
		}

		return user;
	}
}

