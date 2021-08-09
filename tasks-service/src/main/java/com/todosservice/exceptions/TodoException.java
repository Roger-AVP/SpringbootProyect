package com.todosservice.exceptions;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpStatus;

import com.todosservice.config.AppConfig;


public class TodoException extends Exception {

	private static final long serialVersionUID = 1L;

	private String exceptionCode;
	private String message;
	private HttpStatus httpResponse;
	private List<String> messagesList = new ArrayList<>();

	public TodoException(String exceptionCode, String... argumentsMessages) {
		this(getMensajeCommitSolu(exceptionCode, argumentsMessages));
		this.exceptionCode = exceptionCode;
	}

	public TodoException(String message) {
		super(message);
		this.message = message;
		if (message != null && !message.equals("")) {
			messagesList.add(message);
		}
	}

	public TodoException(HttpStatus http, String message) {
		super(message);
		this.message = message;
		if (message != null && !message.equals("")) {
			messagesList.add(message);
		}
		this.httpResponse = http;
	}

	public static TodoException addMessage(TodoException exception, String exceptionCode,
			String... argumentsMessages) {
		if (exception == null) {
			exception = new TodoException(exceptionCode, argumentsMessages);
		} else {
			exception.getMessagesList().add(getMensajeCommitSolu(exceptionCode, argumentsMessages));
		}
		return exception;
	}

	private static String getMensajeCommitSolu(String exceptionCode, String... argumentsMessages) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(AppConfig.class);
		ctx.refresh();
		MessageSource resources = ctx.getBean(MessageSource.class);
		String mensaje = resources.getMessage(exceptionCode, null, "Default", new Locale("es"));
		if (argumentsMessages != null) {
			MessageFormat formater = new MessageFormat("");
			formater.applyPattern(mensaje);
			mensaje = formater.format(argumentsMessages);
		}
		return mensaje;
	}

	public String getExceptionCode() {
		return exceptionCode;
	}

	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getHttpResponse() {
		return httpResponse;
	}

	public void setHttpResponse(HttpStatus httpResponse) {
		this.httpResponse = httpResponse;
	}

	public List<String> getMessagesList() {
		return messagesList;
	}

	public void setMessagesList(List<String> messagesList) {
		this.messagesList = messagesList;
	}

}
