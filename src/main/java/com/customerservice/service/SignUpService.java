package com.customerservice.service;

import java.util.List;

import com.customerservice.dto.SignUpDto;

public interface SignUpService {

	void save(SignUpDto signUpDto);
	
	List<SignUpDto> getAllUsers();
}
