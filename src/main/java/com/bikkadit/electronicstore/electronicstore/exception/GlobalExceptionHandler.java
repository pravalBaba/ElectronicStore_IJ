package com.bikkadit.electronicstore.electronicstore.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.annotations.NotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bikkadit.electronicstore.electronicstore.dtos.ApiResponceMessage;

@RestControllerAdvice
public class GlobalExceptionHandler {

	
	private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponceMessage> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
		
		logger.info("Global Exception handler invoked ");
		ApiResponceMessage apiResponceMessage = ApiResponceMessage.builder().message(ex.getMessage())
		.status(HttpStatus.NOT_FOUND)
		.success(true).build();
		
		return new ResponseEntity<>(apiResponceMessage,HttpStatus.NOT_FOUND);
	}
	
	
	//MethodArgumentNotValid
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,Object>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex){
		
		List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
		
		Map<String,Object> response = new HashMap<>();
		
		allErrors.stream().forEach(objectError->{
			String message = objectError.getDefaultMessage();
			String field = ((FieldError)objectError).getField();
			response.put(field, message);
		});
		
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		
	}
	
	
	
	
	//handle bad api exception
    @ExceptionHandler(BadApiRequestException.class)
    public ResponseEntity<ApiResponceMessage> handleBadApiRequest(BadApiRequestException ex) {
        logger.info("Bad api request");
        ApiResponceMessage response = ApiResponceMessage.builder().message(ex.getMessage()).status(HttpStatus.BAD_REQUEST).success(false).build();
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);

    }

}
