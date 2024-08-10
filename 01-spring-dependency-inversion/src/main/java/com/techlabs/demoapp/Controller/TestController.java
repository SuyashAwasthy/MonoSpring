package com.techlabs.demoapp.Controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.demoapp.model.Instructor;

@RestController
@RequestMapping("/test")
public class TestController {

//	private Instructor instructor1;
//	private Instructor instructor2;
//	
//	public TestController(Instructor instructor1, Instructor instructor2) {
//		super();
//		this.instructor1 = instructor1;
//		this.instructor2 = instructor2;
//	}
	
//	@GetMapping("/hello")
//	public String getMessage() {
//		return "Is the been singleton: "+instructor1.equals(instructor2);
//	}
	
	@GetMapping("/hello")
	public String getMessage() {
		return "hello";
	}

	@GetMapping("/greet")
	public String getGreetings() {
		return "Greetings";
	}
}
