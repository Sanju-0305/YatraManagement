package com.customerservice.service;

import java.util.List;

import com.customerservice.dto.SignUpDto;

public interface SignUpService {

	void save(SignUpDto signUpDto);
	
	List<SignUpDto> getAllUsers();
	void updatePassword(long user_id, String password);
	void updateEmailToken(String user_id, String emailToken);
}
