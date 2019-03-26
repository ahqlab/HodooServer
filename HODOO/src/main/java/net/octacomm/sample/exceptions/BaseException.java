package net.octacomm.sample.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BaseException extends RuntimeException {

	public BaseException(String message) {
		super(message);
	}

}
