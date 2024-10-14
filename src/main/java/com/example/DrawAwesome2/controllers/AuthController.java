package com.example.DrawAwesome2.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.DrawAwesome2.Auth.AuthRequest;
import com.example.DrawAwesome2.Auth.AuthResponse;
import com.example.DrawAwesome2.Auth.AuthService;
import com.example.DrawAwesome2.Auth.LoginRequest;
import com.example.DrawAwesome2.Auth.RegisterRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@CrossOrigin(origins = {"https://hrk118cb-8080.use2.devtunnels.ms", "http://127.0.0.1:5500"})
	@PostMapping(value = "login")
	public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
		
		return ResponseEntity.ok(authService.login(request));
	}

	@CrossOrigin(origins = {"https://hrk118cb-8080.use2.devtunnels.ms", "http://127.0.0.1:5500"})
	@PostMapping(value = "register")
	public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
		
		return ResponseEntity.ok(authService.register(request));
    }
	
	
	
	@CrossOrigin(origins = {"https://hrk118cb-8080.use2.devtunnels.ms", "http://127.0.0.1:5500"})
	@PostMapping(value = "authenticate")
	public ResponseEntity<AuthResponse> auth(@RequestBody AuthRequest request) {
		
		return ResponseEntity.ok(authService.auth(request));
    }
	

	
	@GetMapping("/login")
    @ResponseBody
    public String login() {
        return "Login Page";
    }
	
	@GetMapping("/register")
    @ResponseBody
    public String register() {
        return "Register Page";
    }
	
	
}
