package com.spring.rest.restfulwebservices.exception;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.spring.rest.restfulwebservices.user.UserDaoService;
import com.spring.rest.restfulwebservices.user.UserNotFoundException;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler 
	extends ResponseEntityExceptionHandler{

	private static final Logger log=LoggerFactory.getLogger(CustomizedResponseEntityExceptionHandler.class);

	//default exception handler
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request){
	log.info("handleAllException method is called");
		ExceptionResponse exceptionResponse	=new ExceptionResponse(new Date(), ex.getMessage(),
																request.getDescription(false));
		System.out.println("HttpStatus.INTERNAL_SERVER_ERROR");
		return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	//default exception handler
		@ExceptionHandler(UserNotFoundException.class)
		public final ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request){
			log.info("handleUserNotFoundException method is called");
			ExceptionResponse exceptionResponse	=new ExceptionResponse(new Date(), ex.getMessage(),
																	request.getDescription(false));
			
			return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
		}
		
		@Override
		protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
				HttpHeaders headers, HttpStatus status, WebRequest request) {
			ExceptionResponse exceptionResponse	=new ExceptionResponse(new Date(), /*ex.getMessage()*/ "Validation failed",
					ex.getBindingResult().toString());
			log.info("handleMethodArgumentNotValid is called:: invalid arguments,please enter valid arguments");
			return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
		}
}
