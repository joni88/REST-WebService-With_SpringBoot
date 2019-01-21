package com.in28minutes.rest.webservices.restfulwebservicess.user;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*; 
import java.net.URI;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserResource {

	@Autowired
	private UserDaoService service;
	
	@GetMapping(path="/users")
	public List<User> returnAllUsers(){
		return service.findAll();
	}
	
	@GetMapping(path = "/users/{id}")
	public Resource<User> findUser(@PathVariable int id){
		User user = service.findOnt(id);
		if(user == null){
			throw new UserNotFoundException("id-" +id);
		}
		
		Resource<User> resource = new Resource<User>(user);
		
	    ControllerLinkBuilder linkTo = ControllerLinkBuilder.linkTo(methodOn(this.getClass()).returnAllUsers());
		
	    resource.add(linkTo.withRel("All-Users"));
	    
	    return resource;
	}
	
	@DeleteMapping(path = "/users/{id}")
	public void deleteUser(@PathVariable int id){
		User user = service.delete(id);
		if(user == null){
			throw new UserNotFoundException("id-" +id);
		}		
	}
	
	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
		User saved = service.save(user);
		
		//returns the uri of the created user
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
						.path("/{id}")
						.buildAndExpand(saved.getId())
						.toUri();
		
	  return ResponseEntity.created(location).build();
	}
	
	
	
}
