package com.customerservice.service;

import com.customerservice.dto.EventsDto;

import java.util.List;

public interface EventsService {

	void addEvents(EventsDto eventsDto);

	List<EventsDto> getAllEvents();
}
