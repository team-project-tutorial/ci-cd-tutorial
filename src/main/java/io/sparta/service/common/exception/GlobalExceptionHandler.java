package io.sparta.service.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(IllegalArgumentException.class)
	ResponseEntity<?> handleRuntimeException(RuntimeException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}
}
