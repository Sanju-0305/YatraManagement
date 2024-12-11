package com.customerservice.serviceImpl;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.customerservice.dto.SignUpDto;
import com.customerservice.entity.SignUp;
import com.customerservice.repository.SignUpRepo;
import com.customerservice.service.SignUpService;

@Service
public class SignUpServiceImpl implements SignUpService{

	@Autowired
	private SignUpRepo signUpRepo;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	private ModelMapper modelMapper;
	
	@Override
	public void save(SignUpDto signUpDto) {
		SignUp signUp = new SignUp();
		signUp.setEmail_Id(signUpDto.getEmail_Id());
		signUp.setFirstName(signUpDto.getFirstName());
		signUp.setLastName(signUpDto.getLastName());
		signUp.setGender(signUpDto.getGender());
		signUp.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
		signUp.setPhoneNo(signUpDto.getPhoneNo());
		signUp.setDob(signUpDto.getDob());
		signUp.setEmailToken(signUpDto.getEmailToken());
		signUp.setActive("N");
		signUp.setRole("USER");
		signUpRepo.save(signUp);
	}

	@Override
	public List<SignUpDto> getAllUsers() {
		List<SignUp> signUps = signUpRepo.findAll();
		return signUps.stream()
				.map(users ->modelMapper.map(users, SignUpDto.class))
				.collect(Collectors.toList());
	}
	
	public SignUp getUserByEmail(String email) {
		SignUp signUp = signUpRepo.getByEmailId(email);
		return signUp;
	}

	@Override
	public void updatePassword(long user_id, String password) {
		signUpRepo.updatePassword(user_id, passwordEncoder.encode(password));
	}

	@Override
	public void updateEmailToken(String email_id, String emailToken) {
		signUpRepo.updateEmailToken(email_id, emailToken);
	}

}
