package com.spring.rest.restfulwebservices.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.stereotype.Component;

@Component
public class UserDaoService {
	
  
    private static final Logger log=LoggerFactory.getLogger(UserDaoService.class);
	
	private static List<User> users=new ArrayList<>();
	
	private static int usersCount=4;
	
	static {
		users.add(new User(1, "jack", new Date()));
		users.add(new User(2, "john", new Date()));
		users.add(new User(3, "daniel", new Date()));
		users.add(new User(4, "webber", new Date()));
		log.info("Users are added in static block");
	}
	
	public List<User> findAll(){
		log.info("List users");
		return users;
	}
	
	public User save(User user) {
		if(user.getId()==null /*&& user.getId() <= usersCount*/) {
			user.setId(++usersCount);
		}
		users.add(user);
		return user;
	}
	
	public User findOne(int id) {
		for(User u:users) {
			if(u.getId()==id) {
				return u;
			}
		}
		return null;
	}

	public User DeleteById(int id) {
		Iterator<User> itr=users.iterator();
		while(itr.hasNext()) {
			User user=itr.next();
			if(user.getId()==id) {
				itr.remove();
				return user;
			}
		}
		return null;
	}
}
