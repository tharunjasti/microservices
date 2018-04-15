package com.spring.rest.restfulwebservices.helloworld;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
	
	@GetMapping(path="/hello-world")
	//@RequestMapping(method=RequestMethod.GET,path="/hello")
	public String helloWorld() {
		return "Hello World";
	}
	
	@GetMapping(path="/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello world");
	}
	
	@GetMapping(path="/internationalization")
	public String helloWorldPath( 
			/*@RequestHeader(name="Accept-Language", required=false) Locale locale*/) {

		return messageSource.getMessage("good.morning.message", null,LocaleContextHolder.getLocale());
	}
}
