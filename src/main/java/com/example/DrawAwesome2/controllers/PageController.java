package com.example.DrawAwesome2.controllers;


import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class PageController {


	@GetMapping("/")
    @ResponseBody
    public ModelAndView empty(){
	    ModelAndView modelAndView = new ModelAndView();
	    modelAndView.setViewName("home");
	    return modelAndView;
    }
	@GetMapping("/home")
    @ResponseBody
    public ModelAndView home(){
	    ModelAndView modelAndView = new ModelAndView();
	    modelAndView.setViewName("home");
	    return modelAndView;
    }
	
	@GetMapping("/auth")
    @ResponseBody
    public ModelAndView auth(){
	    ModelAndView modelAndView = new ModelAndView();
	    modelAndView.setViewName("auth");
	    return modelAndView;
    }
	
	@GetMapping("/taskmanager")
    @ResponseBody
    public ModelAndView taskmanager(){
	    ModelAndView modelAndView = new ModelAndView();
	    modelAndView.setViewName("todo");
	    return modelAndView;
    }
	
	@GetMapping("/network")
    @ResponseBody
    public ModelAndView network(){
	    ModelAndView modelAndView = new ModelAndView();
	    modelAndView.setViewName("dashboard");
	    return modelAndView;
    }
	
	@GetMapping("/projectmanager")
    @ResponseBody
    public ModelAndView projectmanager(){
	    ModelAndView modelAndView = new ModelAndView();
	    modelAndView.setViewName("projectmanager");
	    return modelAndView;
    }
	
	
	
}