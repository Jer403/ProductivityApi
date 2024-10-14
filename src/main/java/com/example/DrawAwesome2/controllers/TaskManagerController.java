package com.example.DrawAwesome2.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.DrawAwesome2.TaskManager.TaskManagerRequest;
import com.example.DrawAwesome2.TaskManager.TaskManagerResponse;
import com.example.DrawAwesome2.TaskManager.TaskManagerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tm")
@RequiredArgsConstructor
public class TaskManagerController {

	
	private final TaskManagerService taskManagerService;
	
	
	@CrossOrigin(origins = {"https://hrk118cb-8080.use2.devtunnels.ms", "http://127.0.0.1:5500"})
	@PostMapping(value = "createList")
	public ResponseEntity<TaskManagerResponse> createlist(@RequestBody TaskManagerRequest request) {
		
		return ResponseEntity.ok(taskManagerService.createNewList(request));
    }

	@CrossOrigin(origins = {"https://hrk118cb-8080.use2.devtunnels.ms", "http://127.0.0.1:5500"})
	@PostMapping(value = "getListData")
	public ResponseEntity<TaskManagerResponse> listData(@RequestBody TaskManagerRequest request) {
		
		return ResponseEntity.ok(taskManagerService.getListData(request));
    }
	
	@CrossOrigin(origins = {"https://hrk118cb-8080.use2.devtunnels.ms", "http://127.0.0.1:5500"})
	@PostMapping(value = "getAllLists")
	public ResponseEntity<TaskManagerResponse> allListas(@RequestBody TaskManagerRequest request) {
		
		return ResponseEntity.ok(taskManagerService.getAllLists(request));
    }
	
	@CrossOrigin(origins = {"https://hrk118cb-8080.use2.devtunnels.ms", "http://127.0.0.1:5500"})
	@PostMapping(value = "updateList")
	public ResponseEntity<TaskManagerResponse> updateList(@RequestBody TaskManagerRequest request) {
		
		return ResponseEntity.ok(taskManagerService.updateList(request));
	}

	
	@CrossOrigin(origins = {"https://hrk118cb-8080.use2.devtunnels.ms", "http://127.0.0.1:5500"})
	@PostMapping(value = "deleteList")
	public ResponseEntity<TaskManagerResponse> deleteList(@RequestBody TaskManagerRequest request) {
		
		return ResponseEntity.ok(taskManagerService.deleteList(request));
	}

	
	@CrossOrigin(origins = {"https://hrk118cb-8080.use2.devtunnels.ms", "http://127.0.0.1:5500"})
	@PostMapping(value = "getCalendarDate")
	public ResponseEntity<TaskManagerResponse> getCalendarDate(@RequestBody TaskManagerRequest request) {
		
		return ResponseEntity.ok(taskManagerService.getCalendarDate(request));
    }
	
	@CrossOrigin(origins = {"https://hrk118cb-8080.use2.devtunnels.ms", "http://127.0.0.1:5500"})
	@PostMapping(value = "updateCalendarDate")
	public ResponseEntity<TaskManagerResponse> updateCalendarDate(@RequestBody TaskManagerRequest request) {
		
		return ResponseEntity.ok(taskManagerService.updateCalendarDate(request));
	}
}