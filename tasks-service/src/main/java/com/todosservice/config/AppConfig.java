package com.todosservice.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class AppConfig {

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource mensaje = new ResourceBundleMessageSource();
		mensaje.setBasename("classpath:excepcionesBundle_es");
		mensaje.setDefaultEncoding("UTF-8");
		return mensaje;
	}
	
}
