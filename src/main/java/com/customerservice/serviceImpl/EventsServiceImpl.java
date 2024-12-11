package com.customerservice.serviceImpl;

import com.customerservice.dto.SignUpDto;
import com.customerservice.entity.SignUp;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.customerservice.dto.EventsDto;
import com.customerservice.entity.EventsData;
import com.customerservice.repository.EventsRepo;
import com.customerservice.service.EventsService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventsServiceImpl implements EventsService {

	@Autowired
	private EventsRepo eventsRepo;

	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public void addEvents(EventsDto eventsDto) {
		EventsData eventsData = new EventsData();
		eventsData.setEvent_name(eventsDto.getEvent_name());
		eventsData.setStart_date(eventsDto.getStart_date());
		eventsData.setEnd_date(eventsDto.getEnd_date());
		eventsData.setCost_per_person(eventsDto.getCost_per_person());
		eventsData.setImage_date(eventsDto.getImage_date());
		eventsRepo.save(eventsData);
	}

	@Override
	public List<EventsDto> getAllEvents() {
		List<EventsData> allData = eventsRepo.findAll();
		return allData.stream()
				.map(events->modelMapper.map(events, EventsDto.class))
				.collect(Collectors.toList());
	}


}
