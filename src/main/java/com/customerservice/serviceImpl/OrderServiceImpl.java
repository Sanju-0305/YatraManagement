package com.customerservice.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.catalina.filters.AddDefaultCharsetFilter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.customerservice.dto.CompleteOrderResponse;
import com.customerservice.dto.OrderDto;
import com.customerservice.dto.PageDetailsDto;
import com.customerservice.entity.Order;
import com.customerservice.repository.OrderRepo;
import com.customerservice.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderRepo orderRepo;
	
	@Autowired 
	private ModelMapper modelMapper;
	
//	@Override
//	public void createOrders(OrderDto orderDto) {
//		Order order = new Order();
//		order.setFirstName(orderDto.getFirstName());
//		order.setLlstName(orderDto.getLlstName());
//		order.setDob(orderDto.getDob());
//		order.setPhoneNo(orderDto.getPhoneNo());
//		order.setGender(orderDto.getGender());
//		orderRepo.save(order);
//	}
	
//	@Override
//	public void createOrders(List<OrderDto> orderDTOs) { 
//	List<Order> orders = orderDTOs.stream() .
//			map(orderDTO -> modelMapper.map(orderDTO, Order.class)) 
//			.collect(Collectors.toList()); 
//	orderRepo.saveAll(orders);
//	}
	
	@Override
	public void createOrders(List<OrderDto> orderDTOs) {
		List<Order> orders = new ArrayList<>();
		orderDTOs.stream().forEach(orderDto -> {
			Order order = new Order();
			order.setFirstName(orderDto.getFirstName());
			order.setLastName(orderDto.getLastName());
			order.setDob(orderDto.getDob());
			order.setPhoneNo(orderDto.getPhoneNo());
			order.setGender(orderDto.getGender());
			order.setBooking_date(new Date());
			order.setEventId(orderDto.getEventId());
			order.setUserId(orderDto.getUserId());
			order.setUpiLoc(orderDto.getUpiLoc());
			orders.add(order);
		});
		orderRepo.saveAll(orders);
	}

	@Override
	public CompleteOrderResponse getAllOrders(int pageNo,int pageSize,String sortBy,String seq) { 
		
		Sort sortByAndOrder = seq.equalsIgnoreCase("asc")?
				Sort.by(sortBy).ascending():
					Sort.by(sortBy).descending();
		
		Pageable pageDetails = PageRequest.of(pageNo, pageSize,sortByAndOrder);
		Page<Order> orderPage = orderRepo.findAll(pageDetails);
		List<Order> orders = orderPage.getContent();
		
		PageDetailsDto pageDetailsDto = new PageDetailsDto();
		pageDetailsDto.setPageNumber(orderPage.getNumber());
		pageDetailsDto.setPageSize(orderPage.getSize());
		pageDetailsDto.setTotalElements(orderPage.getTotalElements());
		pageDetailsDto.setTotalPages(orderPage.getTotalPages());
		pageDetailsDto.setLastPage(orderPage.isLast());
		
		CompleteOrderResponse completeOrderResponse = new CompleteOrderResponse();
		completeOrderResponse.setPageDetailsDto(pageDetailsDto);
		completeOrderResponse.setOrderDto(orders.stream() 
				.map(order -> modelMapper.map(order, OrderDto.class)) 
				.collect(Collectors.toList()));
		return completeOrderResponse;
	}
	
	@Override
	public List<OrderDto> getOrdersById(long userId) { 
		List<Order> orders = orderRepo.findAllOrderById(userId);
		return orders.stream() 
				.map(order -> modelMapper.map(order, OrderDto.class)) 
				.collect(Collectors.toList());
	}
	
	public void updateOrders(List<OrderDto> orderDtos) {
		orderDtos.forEach(orderDto->{
			orderRepo.getUpdatePnrSeat(orderDto.getOrderId(),orderDto.getPnrNo(),orderDto.getSeatNo());
		});
	}

}
