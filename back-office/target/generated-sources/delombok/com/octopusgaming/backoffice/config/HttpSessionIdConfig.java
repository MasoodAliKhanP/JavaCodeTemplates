package com.octopusgaming.backoffice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

@Configuration
public class HttpSessionIdConfig {
	@Bean
	public HttpSessionIdResolver httpSessionIdResolver() {
	    return HeaderHttpSessionIdResolver.xAuthToken(); 
	}
}
