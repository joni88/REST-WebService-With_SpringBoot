package com.in28minutes.rest.webservices.restfulwebservicess.helloWorld;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping( path="/helloWorld")
	public String HelloWorld(){
		return "Hello World";
	}
	
	@GetMapping(path="/hello-world-bean")
	public HelloWorldBean helloWorldBean(){
		return new HelloWorldBean("Hello World");
	}
	
	@GetMapping(path="/hello-world/path-variable/{name}")
	public HelloWorldBean helloWorldBean(@PathVariable String name){
		return new HelloWorldBean(String.format("Hello World, %s",name));
	}
	
	@GetMapping(path="/helloWorld-internationalized")
	public String helloWorldInternationalized(@RequestHeader(name="Accept-Language", required=false) Locale locale){
		return messageSource.getMessage("good.morning.message",null,locale);
	}
	
}