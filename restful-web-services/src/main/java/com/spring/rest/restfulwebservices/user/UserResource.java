package com.spring.rest.restfulwebservices.user;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
public class UserResource {
	

	
	private static final Logger log=LoggerFactory.getLogger(UserDaoService.class);
	//private static final Logger log=LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserDaoService service;
	
	@GetMapping("/users")
	public List<User> retriveAllUsers(){
		return service.findAll();
	}
	
	@GetMapping("/users/{id}")
	public Resource<User> getUserID(@PathVariable int id) {
		User user=service.findOne(id);
		if(user==null){
			throw new UserNotFoundException("Id-"+id);
		}
		
		//HATEOAS
		Resource<User> resource=new Resource<User>(user);
	//	ControllerLinkBuilder.linkTo(controller);
		ControllerLinkBuilder linkto=linkTo(methodOn(this.getClass()).retriveAllUsers());
		ControllerLinkBuilder linktoDel=linkTo(methodOn(this.getClass()).getUserID(id));
		resource.add(linkto.withRel("all-users"));
	//	resource.add(linktoDel.withRel("current link of user is:"));
		
		return resource;
	}
	
	@PostMapping("/users")
	public ResponseEntity<Object> craeteUser(@Valid @RequestBody User user) {
		User savedUser=	service.save(user);
		
		//getting current request, we are not hardcoding the url and path is append to current uri
		URI location=ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedUser.getId())
				.toUri();
		log.info(""+location.toString());
		
	return	ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) {
		User user=service.DeleteById(id);
		if(user==null){
			throw new UserNotFoundException("Id-"+id);
			//throw new Exception();
		}
	}
	

}
