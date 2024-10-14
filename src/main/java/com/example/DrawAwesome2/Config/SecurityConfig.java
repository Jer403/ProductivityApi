package com.example.DrawAwesome2.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.DrawAwesome2.Jwt.JwtAuthenticationFilter;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final AuthenticationProvider authProvider;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(csrf ->
				  csrf
				  .disable())
				.authorizeHttpRequests(authRequest ->
				  authRequest
				  .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
				    .requestMatchers("/auth/**","/home","/css/**","/js/**","/notify-channels/**", "/group/**", "/", "/auth", "/home", "/taskmanager", "/network", "/projectmanager").permitAll()
				        .anyRequest().authenticated()
					  )
				.sessionManagement(sessionManager->
						sessionManager
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authProvider)
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}

}

/* "/auth/**","/auth/login","/home","/registro","/css/**","/code.js","/js/**","/auth.css","/dashboard.css"
				    				,"/dashboard.js","/notify-channels/**", "api/pm/test", "/group/**", "https://hrk118cb-8080.use2.devtunnels.ms/" */