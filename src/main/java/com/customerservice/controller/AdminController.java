package com.customerservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.customerservice.dto.CompleteOrderResponse;
import com.customerservice.dto.EventsDto;
import com.customerservice.dto.OrderDto;
import com.customerservice.dto.SignUpDto;
import com.customerservice.service.EventsService;
import com.customerservice.service.OrderService;
import com.customerservice.service.SignUpService;
import com.customerservice.serviceImpl.EmailServiceImpl;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private EmailServiceImpl emailServiceImpl;
	
	@Autowired
	private SignUpService signUpservice;
	
	@Autowired
	private EventsService eventsService;
	
	@GetMapping ("/allOrder")
	public ResponseEntity<CompleteOrderResponse> getAllOrders(@RequestParam(name = "pageNo",defaultValue = "0",required = false) int pageNo,
			@RequestParam(name = "pageSize",defaultValue = "10",required = false) int pageSize,
			@RequestParam(name = "sortBy",defaultValue = "orderId",required = false) String sortBy,
			@RequestParam(name = "seq",defaultValue = "asc",required = false) String seq) { 
		CompleteOrderResponse  allOrders = orderService.getAllOrders(pageNo,pageSize,sortBy,seq); 
		return new ResponseEntity<>(allOrders, HttpStatus.OK);
	}
	
	@PostMapping("/sendMail")
	public ResponseEntity<String> sendMail() {
		emailServiceImpl.sendSimpleMessage("sanjay.maurya17@gmail.com", "test", "this is test email");
		return new ResponseEntity<>("Mail sent successfully", HttpStatus.OK);
	}
	
	@PostMapping("/getAllUser")
	public ResponseEntity<List<SignUpDto>> getAllUsers() { 
		List<SignUpDto> allUsers = signUpservice.getAllUsers(); 
		return new ResponseEntity<>(allUsers, HttpStatus.OK);
	}
	
	@PostMapping("/addEvent")
	public ResponseEntity<?> getAllUsers(@RequestBody EventsDto request) { 
		eventsService.addEvents(request);
		return new ResponseEntity<>("Events has been added successfully", HttpStatus.OK);
	}

}
