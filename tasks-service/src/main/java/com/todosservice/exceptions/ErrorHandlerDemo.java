package com.todosservice.exceptions;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



@ControllerAdvice
public class ErrorHandlerDemo {

	@Autowired
	private Environment env;

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorMessages> methodArgumentNotValidException(HttpServletRequest request,
			MethodArgumentNotValidException e) {
		e.printStackTrace();
		Throwable errorEspecifico = NestedExceptionUtils.getMostSpecificCause(e);
		errorEspecifico.printStackTrace();
		ErrorMessages err = new ErrorMessages();
		err.setMessages(errorEspecifico.getMessage());
		err.setHttpStatus(HttpStatus.BAD_REQUEST.value());
		err.setUrlRequest(request.getRequestURI());
		List<String> listaErroresPersonalizado = err.obtenerMensajesPersonalizadosSql(errorEspecifico.getMessage());
		if (!listaErroresPersonalizado.isEmpty()) {
			err.setMessageDeveloper(String.join("-", listaErroresPersonalizado));
		}
		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<ErrorMessages> illegalStateException(HttpServletRequest request, IllegalStateException e) {
		e.printStackTrace();
		Throwable errorEspecifico = NestedExceptionUtils.getMostSpecificCause(e);
		errorEspecifico.printStackTrace();
		ErrorMessages err = new ErrorMessages();
		err.setMessages(errorEspecifico.getMessage());
		err.setHttpStatus(HttpStatus.BAD_REQUEST.value());
		err.setUrlRequest(request.getRequestURI());
		List<String> listaErroresPersonalizado = err.obtenerMensajesPersonalizadosExcel(errorEspecifico.getMessage());
		if (!listaErroresPersonalizado.isEmpty()) {
			err.setMessageDeveloper(String.join("-", listaErroresPersonalizado));
		}
		// java.lang.IllegalStateException: Cannot get a STRING value from a NUMERIC
		// cell
		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
	}

	// IllegalStateException
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorMessages> constraintViolationException(HttpServletRequest request,
			MethodArgumentNotValidException e) {

		// Obtengo los errores de Spring
		BindingResult result = e.getBindingResult();
		List<FieldError> fieldErros = result.getFieldErrors();
		// COnvertir los errores en string
		StringBuilder errorMensaje = new StringBuilder();
		fieldErros.forEach(fe -> fe.getField().concat(" ").concat(fe.getDefaultMessage().concat(" ")));
		// Retornamos los errores en formato json
		ErrorMessages err = new ErrorMessages(HttpStatus.BAD_REQUEST.value(), errorMensaje.toString(),
				request.getRequestURI());
		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorMessages> runtimeException(RuntimeException e, HttpServletRequest request) {
		e.printStackTrace();
		Throwable errorEspecifico = NestedExceptionUtils.getMostSpecificCause(e);
		errorEspecifico.printStackTrace();
		ErrorMessages err = new ErrorMessages();
		err.setMessages(e.getMessage());
		err.setHttpStatus(HttpStatus.BAD_REQUEST.value());
		err.setMessageDeveloper(errorEspecifico.getMessage());
		err.setUrlRequest(request.getRequestURI());
		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorMessages> HttpMessageNotReadableException(RuntimeException e,
			HttpServletRequest request) {
		e.printStackTrace();
		Throwable errorEspecifico = NestedExceptionUtils.getMostSpecificCause(e);
		errorEspecifico.printStackTrace();
		ErrorMessages err = new ErrorMessages();
		err.setMessages(e.getMessage());
		err.setHttpStatus(HttpStatus.BAD_REQUEST.value());
		err.setUrlRequest(request.getRequestURI());
		List<String> listaErroresPersonalizado = err.obtenerMensajeHttpPersonalizados(errorEspecifico.getMessage());
		if (!listaErroresPersonalizado.isEmpty()) {
			err.setMessageDeveloper(String.join("-", listaErroresPersonalizado));
		}
		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErrorMessages> dataIntegrityViolationException(DataIntegrityViolationException e,
			HttpServletRequest request) {
		Throwable errorEspecifico = NestedExceptionUtils.getMostSpecificCause(e);
		errorEspecifico.printStackTrace();
		ErrorMessages err = new ErrorMessages();
		err.setMessages(errorEspecifico.getMessage());
		err.setHttpStatus(HttpStatus.BAD_REQUEST.value());
		err.setUrlRequest(request.getRequestURI());
		List<String> listaErroresPersonalizado = new ArrayList<>();
		if (obtenerAplicationProperties("spring.jpa.properties.hibernate.dialect").contains("PostgreSQLDialect")) {
			listaErroresPersonalizado = err.obtenerMensajesPersonalizadosPsql(errorEspecifico.getMessage());
		} else {
			listaErroresPersonalizado = err.obtenerMensajesPersonalizadosSql(errorEspecifico.getMessage());
		}
		if (!listaErroresPersonalizado.isEmpty()) {
			err.setMessageDeveloper(String.join("-", listaErroresPersonalizado));
		}
		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(SQLException.class)
	public ResponseEntity<ErrorMessages> sqlException(SQLException e) {
		e.printStackTrace();
		ErrorMessages err = new ErrorMessages();
		err.setMessages(e.getMessage());
		return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(TodoException.class)
	public ResponseEntity<ErrorMessages> exception(TodoException e, HttpServletRequest request) {
		ErrorMessages err = new ErrorMessages();
		err.setMessages(e.getMessage());
		err.setHttpStatus(e.getHttpResponse().value());
		err.setUrlRequest(request.getRequestURI());
		return new ResponseEntity<>(err, new HttpHeaders(), e.getHttpResponse());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessages> exception(Exception e) {
		ErrorMessages err = new ErrorMessages();
		err.setMessages(e.getMessage());
		e.printStackTrace();

		return new ResponseEntity<>(err, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private String obtenerAplicationProperties(String key) {
		return env.getProperty(key);
	}

}
