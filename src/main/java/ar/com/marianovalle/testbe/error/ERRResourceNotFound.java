package ar.com.marianovalle.testbe.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ERRResourceNotFound extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ERRResourceNotFound(String message){
		super(message);
	}
}

@ControllerAdvice
@ResponseStatus(HttpStatus.NOT_FOUND)
class ResourceNotFoundAdvice {

  @ResponseBody
  @ExceptionHandler(ERRResourceNotFound.class)
  String notFoundHandler(ERRResourceNotFound ex) {
    return ex.getMessage();
  }
}
