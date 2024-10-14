package com.example.DrawAwesome2.controllers;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.util.HtmlUtils;

import com.example.DrawAwesome2.models.Message;
import com.example.DrawAwesome2.models.Notify;



@Controller
public class WebSocketController {

	@CrossOrigin(origins = {"https://hrk118cb-8080.use2.devtunnels.ms", "http://127.0.0.1:5500"})
	@MessageMapping("/notify/{id}")
	@SendTo("/group/{id}")
	public Notify notify(@DestinationVariable String id,Message message) throws Exception {
		System.out.println("/topic/"+id);
		// Thread.sleep(1000); // simulated delay
		return new Notify(id,HtmlUtils.htmlEscape(message.message()));
	}
	
}

