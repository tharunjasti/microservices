package com.spring.rest.restfulwebservices.user;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import java.net.URI;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/jpa")
public class UserJPAResource {
	

	
	private static final Logger log=LoggerFactory.getLogger(UserDaoService.class);
	//private static final Logger log=LoggerFactory.getLogger(this.getClass());
	

	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@GetMapping("/users")
	public List<User> retriveAllUsers(){
		return userRepository.findAll();
	}
	
	@GetMapping("/users/{id}")
	public Resource<User> getUserID(@PathVariable int id) {
		Optional<User> user=userRepository.findById(id);
		if(!user.isPresent()){
			throw new UserNotFoundException("Id-"+id);
		}
		
		//HATEOAS
		Resource<User> resource=new Resource<User>(user.get());
	//	ControllerLinkBuilder.linkTo(controller);
		ControllerLinkBuilder linkto=linkTo(methodOn(this.getClass()).retriveAllUsers());
		ControllerLinkBuilder linktoDel=linkTo(methodOn(this.getClass()).getUserID(id));
		resource.add(linkto.withRel("all-users"));
	//	resource.add(linktoDel.withRel("current link of user is:"));
		
		return resource;
	}
	
	@PostMapping("/users")
	public ResponseEntity<Object> craeteUser(@Valid @RequestBody User user) {
		User savedUser=	userRepository.save(user);
		
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
		userRepository.deleteById(id);
	}
	
	@GetMapping("/users/{id}/posts")
	public List<Post> retriveAllUserPosts(@PathVariable int id){
		Optional<User> user=userRepository.findById(id);
		if(!user.isPresent()) {
			throw new UserNotFoundException("id-"+id);
		}
		User u=user.get();
		List<Post> posts=u.getPosts();
		return posts;
	}
	
	@PostMapping("/users/{id}/posts")
	public ResponseEntity<Object> craetePost(@PathVariable int id,@RequestBody Post post) {
		Optional<User> optinal=userRepository.findById(id);
		if(!optinal.isPresent()) {
			throw new UserNotFoundException("id-"+id);
		}
		
		User user=optinal.get();
		post.setUser(user);
		postRepository.save(post);
		
		//getting current request, we are not hardcoding the url and path is append to current uri
		URI location=ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(post.getId())
				.toUri();
		log.info(""+location.toString());
		
	return	ResponseEntity.created(location).build();
	}

}
