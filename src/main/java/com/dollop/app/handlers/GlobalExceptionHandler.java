package com.dollop.app.handlers;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.dollop.app.dtos.ApiResponseMessage;
import com.dollop.app.dtos.BadApiRequestException;
import com.dollop.app.exceptions.ResourceNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private Logger logger=LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	
	//1. Resource Not Found Exception
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponseMessage> resourceNotFoundException(ResourceNotFoundException ex){
	      logger.info("Exception Handler is Line :");
	      ApiResponseMessage response=ApiResponseMessage.builder()
			                    .message(ex.getMessage())
			                    .status(HttpStatus.NOT_FOUND)
			                    .success(true).build();
	      return new ResponseEntity<ApiResponseMessage>(response,HttpStatus.NOT_FOUND);
	}
	
	//2. Bad Request Excepiton
	@ExceptionHandler(BadApiRequestException.class)
	public ResponseEntity<ApiResponseMessage> badApiRequestException(BadApiRequestException ex){
	      logger.info("Exception Handler is Line :");
	      ApiResponseMessage response=ApiResponseMessage.builder()
			                    .message(ex.getMessage())
			                    .status(HttpStatus.BAD_REQUEST)
			                    .success(true).build();
	return new ResponseEntity<ApiResponseMessage>(response,HttpStatus.BAD_REQUEST);
	}
	
	//3.Method Argument Not Valid Exception
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
		List<ObjectError> allErrors=ex.getBindingResult().getAllErrors();
		Map<String,Object> response=new HashMap<>();
		allErrors.stream().forEach(objectError->{
			String message=objectError.getDefaultMessage();
			String field=((FieldError) objectError).getField();
			response.put(field, message);
		});
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
	}
	
}
