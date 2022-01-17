package com.olx.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(value = { InvalidStatusIdException.class, InvalidCategoryIdException.class,
			InvalidAuthTokenException.class, InvalidAdvertiseIdException.class, InvalidOnDateException.class,
			InvalidFromDateException.class, InvalidStartIndexException.class, InvalidRecoedNumberException.class,
			InvalidDateConditionException.class, InvalidDateFormatException.class, ServiceNotAvailableException.class })
	public ResponseEntity<Object> handleInvalidError(RuntimeException exception, WebRequest request) {
		return handleExceptionInternal(exception, exception.toString(), new HttpHeaders(), HttpStatus.BAD_REQUEST,
				request);

	}

	public ResponseEntity<Object> invalidAdvertiseDataException(RuntimeException exception, WebRequest request) {

		return handleExceptionInternal(exception, exception.toString(), new HttpHeaders(), HttpStatus.BAD_REQUEST,
				request);
	}

	public ResponseEntity<Object> invalidAdvertiseIdException(RuntimeException exception, WebRequest request) {

		return handleExceptionInternal(exception, exception.toString(), new HttpHeaders(), HttpStatus.BAD_REQUEST,
				request);
	}

	public ResponseEntity<Object> invalidAuthTokenException(RuntimeException exception, WebRequest request) {

		return handleExceptionInternal(exception, exception.toString(), new HttpHeaders(), HttpStatus.BAD_REQUEST,
				request);
	}

	public ResponseEntity<Object> invalidCategoryIdException(RuntimeException exception, WebRequest request) {

		return handleExceptionInternal(exception, exception.toString(), new HttpHeaders(), HttpStatus.BAD_REQUEST,
				request);
	}

	public ResponseEntity<Object> invalidDateConditionException(RuntimeException exception, WebRequest request) {

		return handleExceptionInternal(exception, exception.toString(), new HttpHeaders(), HttpStatus.BAD_REQUEST,
				request);
	}

	public ResponseEntity<Object> invalidDateException(RuntimeException exception, WebRequest request) {

		return handleExceptionInternal(exception, exception.toString(), new HttpHeaders(), HttpStatus.BAD_REQUEST,
				request);
	}

	public ResponseEntity<Object> invalidRecordNoException(RuntimeException exception, WebRequest request) {

		return handleExceptionInternal(exception, exception.toString(), new HttpHeaders(), HttpStatus.BAD_REQUEST,
				request);
	}

	public ResponseEntity<Object> invalidStartIndexException(RuntimeException exception, WebRequest request) {

		return handleExceptionInternal(exception, exception.toString(), new HttpHeaders(), HttpStatus.BAD_REQUEST,
				request);
	}

	public ResponseEntity<Object> invalidStatusIdException(RuntimeException exception, WebRequest request) {

		return handleExceptionInternal(exception, exception.toString(), new HttpHeaders(), HttpStatus.BAD_REQUEST,
				request);
	}

}
