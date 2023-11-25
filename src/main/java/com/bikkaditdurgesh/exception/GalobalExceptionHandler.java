package com.bikkaditdurgesh.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bikkaditdurgesh.payload.ApiResponse;

@RestControllerAdvice
public class GalobalExceptionHandler {
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFound(ResourceNotFoundException ex){
	  String massage=ex.getMessage();  
	  ApiResponse apiResponse =new ApiResponse(massage,false);
	  
	  
	  return new ResponseEntity<ApiResponse> (apiResponse,HttpStatus.NOT_FOUND);
	  
  }
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String , String>> handlMethodArgumentNotValidException(MethodArgumentNotValidException ex){
	  Map<String , String> res=new HashMap<>();
	  ex.getBindingResult().getAllErrors().forEach((error)->{
		  String fieldName = ((FieldError)error).getField();
		  String message = error.getDefaultMessage();
		  res.put(fieldName, message);
	  });
	  return new ResponseEntity<Map<String , String>>(res,HttpStatus.BAD_REQUEST);
	  
  }
  
  @ExceptionHandler(ApiException.class)
	public ResponseEntity<ApiResponse> handleApiException(ApiException ex){
	  String massage=ex.getMessage();  
	  ApiResponse apiResponse =new ApiResponse(massage,true);
	  
	  
	  return new ResponseEntity<ApiResponse> (apiResponse,HttpStatus.BAD_REQUEST);
	  
}
}
