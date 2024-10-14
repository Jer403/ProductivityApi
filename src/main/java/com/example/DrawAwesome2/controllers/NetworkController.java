package com.example.DrawAwesome2.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.DrawAwesome2.Network.NetworkRequest;
import com.example.DrawAwesome2.Network.NetworkResponse;
import com.example.DrawAwesome2.Network.NetworkService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/nw")
@RequiredArgsConstructor
public class NetworkController {

	
	private final NetworkService networkService;
	
	@CrossOrigin(origins = {"https://hrk118cb-8080.use2.devtunnels.ms", "http://127.0.0.1:5500"})
	@PostMapping(value = "create")
	public ResponseEntity<NetworkResponse> create(@RequestBody NetworkRequest request) {
		
		return ResponseEntity.ok(networkService.create(request));
    }

	@CrossOrigin(origins = {"https://hrk118cb-8080.use2.devtunnels.ms", "http://127.0.0.1:5500"})
	@PostMapping(value = "getData")
	public ResponseEntity<NetworkResponse> data(@RequestBody NetworkRequest request) {
		
		return ResponseEntity.ok(networkService.getData(request));
    }
	
	@CrossOrigin(origins = {"https://hrk118cb-8080.use2.devtunnels.ms", "http://127.0.0.1:5500"})
	@PostMapping(value = "getAllProjects")
	public ResponseEntity<NetworkResponse> allData(@RequestBody NetworkRequest request) {
		
		return ResponseEntity.ok(networkService.getAllProjects(request));
    }
	
	@CrossOrigin(origins = {"https://hrk118cb-8080.use2.devtunnels.ms", "http://127.0.0.1:5500"})
	@PostMapping(value = "update")
	public ResponseEntity<NetworkResponse> update(@RequestBody NetworkRequest request) {
		
		return ResponseEntity.ok(networkService.update(request));
	}

	
	@CrossOrigin(origins = {"https://hrk118cb-8080.use2.devtunnels.ms", "http://127.0.0.1:5500"})
	@PostMapping(value = "delete")
	public ResponseEntity<NetworkResponse> delete(@RequestBody NetworkRequest request) {
		
		return ResponseEntity.ok(networkService.delete(request));
	}


	
}
