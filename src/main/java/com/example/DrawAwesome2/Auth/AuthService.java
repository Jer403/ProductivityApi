package com.example.DrawAwesome2.Auth;



import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.DrawAwesome2.Jwt.JwtService;
import com.example.DrawAwesome2.models.Role;
import com.example.DrawAwesome2.models.Users;
import com.example.DrawAwesome2.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	
	private final UserRepository userRepository;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;

	
	public AuthResponse login(LoginRequest request) {

		String error = null;
		
		try {
			userRepository.findByUsername(request.getUsername()).orElseThrow();
		}catch(Exception e){
			error = "usernameNotFound";
			
			return AuthResponse.builder()
					.error(error)
					.build();
		}
		
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		}catch(Exception e){
			error = "unableToAuthenticate";
			
			return AuthResponse.builder()
					.error(error)
					.build();
		}
		
		
		UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
		String token = jwtService.getToken(user, request.getTiempo());
		
		return AuthResponse.builder()
				.token(token)
				.build();
		
	}

	
	
	
	public AuthResponse register(RegisterRequest request) {
		Users user = Users.builder()
				.username(request.getName())
				.password(passwordEncoder.encode(request.getPassword()) )
				.email(request.getEmail())
				.role(Role.USER)
				.build();
		

		String error = null;
		
		
		try {
			userRepository.findByUsername(user.getUsername()).orElseThrow();
		}catch(Exception e){
			
			try {
				userRepository.findByEmail(user.getEmail()).orElseThrow();
			}catch(Exception e2){
				userRepository.save(user);
				
				return AuthResponse.builder()
						.token(jwtService.getToken(user, request.getTiempo()))
						.build();
			}
			
			error = "DuplicatedEmailKey";
			
			return AuthResponse.builder()
					.error(error)
					.build();
		}
		
		
		
		error = "DuplicatedUserKey";
		
		return AuthResponse.builder()
				.error(error)
				.build();
		
	}
	
	
	
	
	
	
	public AuthResponse auth(AuthRequest request) {
		Users user;
		String error = null;

		user = getUser(request);
		if(user == null) {
			error = "userNotFound";
			return AuthResponse
					.builder()
					.error(error)
					.build();
		}
		
		
		
		String token = jwtService.getToken(user, request.getTiempo());
		
		return AuthResponse
				.builder()
				.token(token)
				.build();
	}
	
	public Users getUser( AuthRequest request) {
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
