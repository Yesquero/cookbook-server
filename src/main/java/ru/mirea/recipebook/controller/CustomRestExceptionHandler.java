package ru.mirea.recipebook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.mirea.recipebook.controller.dto.ExceptionResponse;
import ru.mirea.recipebook.utility.DomainLogicException;
import ru.mirea.recipebook.utility.ResourceConflictException;
import ru.mirea.recipebook.utility.ResourceNotFoundException;

@RestControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomRestExceptionHandler.class);

	@ExceptionHandler(value = {ResourceNotFoundException.class})
	protected ResponseEntity<Object> handleResourceNotFound(RuntimeException ex, WebRequest request) {
		LOGGER.error(ex.getMessage());
		LOGGER.trace(ex.getMessage(), ex);

		ExceptionResponse bodyOfResponse = new ExceptionResponse();

		bodyOfResponse.setResponseCode(HttpStatus.NOT_FOUND.value());
		bodyOfResponse.setResponseMessage(ex.getMessage());

		return handleExceptionInternal(ex, bodyOfResponse,
			new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(value = {ResourceConflictException.class})
	protected ResponseEntity<Object> handeResourceConflict(RuntimeException ex, WebRequest request) {
		LOGGER.error(ex.getMessage());
		LOGGER.trace(ex.getMessage(), ex);

		ExceptionResponse bodyOfResponse = new ExceptionResponse();

		bodyOfResponse.setResponseCode(HttpStatus.CONFLICT.value());
		bodyOfResponse.setResponseMessage(ex.getMessage());

		return handleExceptionInternal(ex, bodyOfResponse,
			new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

	@ExceptionHandler(value = {DomainLogicException.class})
	protected ResponseEntity<Object> handleDomainLogicException(RuntimeException ex, WebRequest request) {
		LOGGER.error(ex.getMessage());
		LOGGER.trace(ex.getMessage(), ex);

		ExceptionResponse bodyOfResponse = new ExceptionResponse();

		bodyOfResponse.setResponseCode(HttpStatus.BAD_REQUEST.value());
		bodyOfResponse.setResponseMessage(ex.getMessage());

		return handleExceptionInternal(ex, bodyOfResponse,
			new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

}
