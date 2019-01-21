package com.in28minutes.rest.webservices.restfulwebservicess.user;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserJpaResource {

	@Autowired
	private PostRepository postRepo;
	
	@Autowired
	private UserRepository repository;
	
	@GetMapping(path="/jpa/users")
	public List<User> returnAllUsers(){
		return repository.findAll();
	}
	
	@GetMapping(path = "/jpa/users/{id}")
	public Resource<User> findUser(@PathVariable int id){
		Optional<User> user = repository.findById(id);
		
		if(!user.isPresent()){
			throw new UserNotFoundException("id-" +id);
		}
		
		Resource<User> resource = new Resource<User>(user.get());
		
	    ControllerLinkBuilder linkTo = ControllerLinkBuilder.linkTo(methodOn(this.getClass()).returnAllUsers());
		
	    resource.add(linkTo.withRel("All-Users"));
	    
	    return resource;
	}
	
	@DeleteMapping(path = "/jpa/users/{id}")
	public void deleteUser(@PathVariable int id){
		 repository.deleteById(id);;    	
	}
	
	@PostMapping("/jpa/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
		User saved = repository.save(user);
		
		//returns the uri of the created user
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
						.path("/{id}")
						.buildAndExpand(saved.getId())
						.toUri();
		
	  return ResponseEntity.created(location).build();
	}
	
	@GetMapping(path="/jpa/users/{id}/posts")
	public List<Post> returnAllUserPosts(@PathVariable Integer id){
		Optional<User> optionalUser  = repository.findById(id);
		if(!optionalUser.isPresent()){
			throw new UserNotFoundException("id--" + id);
		}
		//optionalUser.get().getPost();
		
		return optionalUser.get().getPost();
	}
	
	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<Object> createPost(@PathVariable Integer id, @RequestBody Post post){
		
		Optional<User> optionalUser  = repository.findById(id);
		if(!optionalUser.isPresent()){
			throw new UserNotFoundException("id--" + id);
		}
		User user = optionalUser.get();
		
		post.setUser(user);
		
		postRepo.save(post);
		
		//returns the uri of the created user
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
						.path("/{id}")
						.buildAndExpand(post.getId())
						.toUri();
		
	  return ResponseEntity.created(location).build();
	}
	
	
	
}
