package com.customerservice.controller;

import com.customerservice.dto.EventsDto;
import com.customerservice.dto.SignUpDto;
import com.customerservice.entity.SignUp;
import com.customerservice.service.EventsService;
import com.customerservice.service.OrderService;
import com.customerservice.service.SignUpService;
import com.customerservice.serviceImpl.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/common")
@CrossOrigin(origins = "*")
public class CommonController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private EmailServiceImpl emailServiceImpl;

    @Autowired
    private SignUpService signUpservice;

    @Autowired
    private EventsService eventsService;

    @GetMapping("/getAllEvents")
    public ResponseEntity<List<EventsDto>> getAllEvents() {
        List<EventsDto> allEvents = eventsService.getAllEvents();
        return new ResponseEntity<>(allEvents, HttpStatus.OK);
    }


}
