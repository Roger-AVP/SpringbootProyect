package com.todosservice.exceptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.todosservice.enums.ExeptionMessageEnum;


@JsonInclude(Include.NON_NULL)
public class ErrorMessages implements Serializable {

	private Integer httpStatus;
	private String messages;
	private String urlRequest;
	private String messageDeveloper;
	
	private static final long serialVersionUID = 5318063708359922770L;

	public ErrorMessages() {
	}
	
	public ErrorMessages(Integer httpStatus, String messages, String urlRequest) {
		super();
		this.httpStatus = httpStatus;
		this.messages = messages;
		this.urlRequest = urlRequest;
	}

	public List<String> obtenerMensajesPersonalizadosSql(String mensaje) {
		String auxiliar[] = mensaje.split("\\.");
		String error = "";
		List<String> listaMensajes = new ArrayList<>();
		if(mensaje.contains(ExeptionMessageEnum.UNIQUE_KEY.getName())) {
			error = ExeptionMessageEnum.UNIQUE_KEY.toString().concat("=").concat(auxiliar[auxiliar.length -1]);
			listaMensajes.add(error);
		}else if(mensaje.contains(ExeptionMessageEnum.NULL_COLUMNA.getName())) {
			error = ExeptionMessageEnum.NULL_COLUMNA.toString().concat("=").concat(auxiliar[0].substring(0, auxiliar[0].indexOf(",") )).concat(auxiliar[auxiliar.length -1]);
			listaMensajes.add(error);
		}else if(mensaje.contains(ExeptionMessageEnum.VALIDATION_FAILDER.getName())) {
			error = formarMensajeTipoValidationFailder(auxiliar, mensaje);
			listaMensajes.add(error);
		}
		return listaMensajes;
	}
	
	public List<String> obtenerMensajesPersonalizadosExcel(String mensaje) {
		String error = "";
		List<String> listaMensajes = new ArrayList<>();
		if(mensaje.contains(ExeptionMessageEnum.CANNOT_STRING_NUMERIC.getName())) {
			//UNIQUE_KEY=Cannot get a STRING value from a NUMERIC cell
			error = ExeptionMessageEnum.CANNOT_STRING_NUMERIC.toString().concat("=").concat("No se puede obtener el valor String de una Celda Numerica");
			listaMensajes.add(error);
		}
		return listaMensajes;
	}
	
	public List<String> obtenerMensajeHttpPersonalizados(String mensaje) {
		String error = "";
		List<String> listaMensajes = new ArrayList<>();
		if(mensaje.contains(ExeptionMessageEnum.REQUIRED_REQUEST_BODY_IS_MISSING.getName())) {
			error = ExeptionMessageEnum.REQUIRED_REQUEST_BODY_IS_MISSING.toString().concat("=")
					.concat(mensaje.substring(mensaje.indexOf(">")+1, mensaje.indexOf("throws")));
			listaMensajes.add(error);
		}
		return listaMensajes;
	}
	
	public List<String> obtenerMensajesPersonalizadosPsql(String mensaje) {
		String auxiliar[] = mensaje.split("\\.");
		String error = "";
		List<String> listaMensajes = new ArrayList<>();
		if(mensaje.contains(ExeptionMessageEnum.LLAVE_DUPLICADA.getName())) {
			error = ExeptionMessageEnum.LLAVE_DUPLICADA.toString().concat("=").concat(auxiliar[0].substring(auxiliar[0].indexOf("("), auxiliar[0].length()));
			listaMensajes.add(error);
		}else if(mensaje.contains(ExeptionMessageEnum.NULL_COLUMNA_PSQL.getName())) {
			error = ExeptionMessageEnum.NULL_COLUMNA_PSQL.toString().concat("=").concat(auxiliar[0].substring(auxiliar[0].indexOf("«") + 1, auxiliar[0].indexOf("»") ));
			listaMensajes.add(error);
		}else if(mensaje.contains(ExeptionMessageEnum.VALIDATION_FAILDER.getName())) {
			error = formarMensajeTipoValidationFailder(auxiliar, mensaje);
			listaMensajes.add(error);
		}
		return listaMensajes;
	}
	
	private String formarMensajeTipoValidationFailder(String auxiliar[], String mensaje) {
		String campo = "";
		String error = "";
		String mensajeEspecifico = "";
		auxiliar = mensaje.split("\\;");
		error = auxiliar[auxiliar.length -2];
		campo = error.substring(error.indexOf("[") + 1, error.indexOf("]"));
		mensajeEspecifico = auxiliar[auxiliar.length -1].substring(auxiliar[auxiliar.length -1].indexOf("[") + 1, auxiliar[auxiliar.length -1].indexOf("]")); 
		error = ExeptionMessageEnum.VALIDATION_FAILDER.toString().concat("=").concat(campo).concat(" ").concat(mensajeEspecifico);
		return error;
	}
	
	
	public Integer getHttpStatus() {
		return httpStatus;
	}
	public void setHttpStatus(Integer httpStatus) {
		this.httpStatus = httpStatus;
	}
	public String getMessages() {
		return messages;
	}
	public void setMessages(String messages) {
		this.messages = messages;
	}
	public String getUrlRequest() {
		return urlRequest;
	}
	public void setUrlRequest(String urlRequest) {
		this.urlRequest = urlRequest;
	}
	public String getMessageDeveloper() {
		return messageDeveloper;
	}
	public void setMessageDeveloper(String messageDeveloper) {
		this.messageDeveloper = messageDeveloper;
	}

	
}
