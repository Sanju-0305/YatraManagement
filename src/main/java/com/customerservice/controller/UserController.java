package com.customerservice.controller;

import java.util.List;
import java.util.UUID;

import com.customerservice.dto.UpdatePasswordDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import com.customerservice.dto.LoginRequestDto;
import com.customerservice.dto.OrderDto;
import com.customerservice.dto.SignUpDto;
import com.customerservice.entity.SignUp;
import com.customerservice.model.JwtResponse;
import com.customerservice.repository.SignUpRepo;
import com.customerservice.service.LoginService;
import com.customerservice.service.OrderService;
import com.customerservice.service.SignUpService;
import com.customerservice.serviceImpl.EmailServiceImpl;
import com.customerservice.utils.HelperUtils;
import com.customerservice.utils.JwtHelper;
import com.customerservice.utils.UserSession;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private SignUpService SignUpservice;

	@Autowired
	private UserSession userSession;

	@Autowired
	private JwtHelper helper;

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private EmailServiceImpl emailService;

	@Lazy
	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private SignUpRepo signUpRepo;
	
	@Autowired
	private HelperUtils helperUtils;

	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto request) {
		this.doAuthenticate(request.getEmailId(), request.getPassword());
		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmailId());
		Object[] role = userDetails.getAuthorities().toArray();
		String token = this.helper.generateToken(userDetails,role[0].toString());
		if(signUpRepo.getByAvticeEmailId(request.getEmailId())<1) {
			return new ResponseEntity<>("User Not Verified Yet", HttpStatus.UNAUTHORIZED);
        }
//		userSession.setUserName(this.helper.getUsernameFromToken(token));
//		userSession.setId(1L);
		JwtResponse response = new JwtResponse(token, role[0].toString());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> addUser(@Valid  @RequestBody SignUpDto user) {
		if (loginService.hasUserWithEmail(user.getEmail_Id())) {
			return new ResponseEntity<>("User already exists with this email", HttpStatus.NOT_ACCEPTABLE);
		}
		String emailToken = UUID.randomUUID().toString();
		user.setEmailToken(emailToken);
		SignUpservice.save(user);
		String verificationLink = helperUtils.getLinkForVerification(user.getEmail_Id(), emailToken);
		try {
			String verificationMail = helperUtils.htmlMailDetails(verificationLink, user.getFirstName());
			emailService.sendHtmlEmail(user.getEmail_Id(), "Verifiy Account : Kartik Yatra", verificationMail);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<SignUpDto>(user, HttpStatus.CREATED);
	}

	@PostMapping("/order")
	public ResponseEntity<String> createOrders(@Valid  @RequestBody List<OrderDto> orderDTOs) {
		orderService.createOrders(orderDTOs);
		return new ResponseEntity<>("Orders created successfully!", HttpStatus.CREATED);
	}
	
	@PutMapping("/UpdateOrder")
	public ResponseEntity<String> updateOrders(@Valid @RequestBody List<OrderDto> orderDTOs) {
		orderService.updateOrders(orderDTOs);
		return new ResponseEntity<>( HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/getOrdersById/{id}")
	public ResponseEntity<List<OrderDto>> getOrdersById(@PathVariable long id){
		List<OrderDto> orderDTOs = orderService.getOrdersById(id);
		return new ResponseEntity<>(orderDTOs, HttpStatus.CREATED);
	}
	
	@GetMapping("/verifyUser/{id}")
	public ResponseEntity<String> verifyAccount(@PathVariable String id){
		String [] details = id.split("~");
		if(details.length>=2);{
		SignUp signUp = signUpRepo.getByEmailId(details[0]);
		if(signUp.getEmailToken().equalsIgnoreCase(details[1])) {
			signUpRepo.updateActiveStatus(signUp.getId());
			return new ResponseEntity<>("User Validated successfully", HttpStatus.CREATED);
		}
		}
		return new ResponseEntity<>("User not Validated successfully", HttpStatus.FAILED_DEPENDENCY);
	}

	@PutMapping("/updatePassword")
	public ResponseEntity<String> resetPassword(@RequestBody UpdatePasswordDto updatePasswordData){


			SignUp signUp = signUpRepo.getByEmailId(updatePasswordData.getEmail());
			if(signUp.getEmailToken().equalsIgnoreCase(updatePasswordData.getOtp())) {
				SignUpservice.updatePassword(signUp.getId(), updatePasswordData.getNewPassword());
				return new ResponseEntity<>("Password Updated successfully", HttpStatus.CREATED);
			}
		return new ResponseEntity<>("Invalid Email or Wrong Otp", HttpStatus.FORBIDDEN);
	}
	@PostMapping("/getUpdatePasswordOtp")
	public ResponseEntity<String> resetPasswordOtp(@RequestBody UpdatePasswordDto updatePasswordData){
		if (loginService.hasUserWithEmail(updatePasswordData.getEmail())) {
			String otp = String.valueOf(helperUtils.generateOtp());
			SignUpservice.updateEmailToken(updatePasswordData.getEmail(), otp);
			emailService.sendSimpleMessage(updatePasswordData.getEmail(), "Reset Password Otp : Kartik Yatra", "Otp to reset the password "+otp);
			return new ResponseEntity<>("Otp Sent Successfully", HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>("Invalid Email", HttpStatus.FORBIDDEN);
	}

	private void doAuthenticate(String email, String password) {

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
		try {
			manager.authenticate(authentication);
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException(" Invalid Username or Password!!");
		}

	}

}
