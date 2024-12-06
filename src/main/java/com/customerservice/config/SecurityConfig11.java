package com.customerservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.oauth2.sdk.Request;


public class SecurityConfig11 {

	public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception{
		security.cors(AbstractHttpConfigurer::disable);
		security.csrf(AbstractHttpConfigurer::disable);
		security.authorizeHttpRequests(request->request.anyRequest().permitAll());
		security.oauth2ResourceServer(authServer->authServer.jwt(Customizer.withDefaults()));
		return security.build();
		
	}
}
