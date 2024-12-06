package com.customerservice.service;

import java.util.List;

import com.customerservice.dto.CompleteOrderResponse;
import com.customerservice.dto.OrderDto;
import com.customerservice.entity.Order;

public interface OrderService {

	void createOrders(List<OrderDto> orderDTOs);

	CompleteOrderResponse getAllOrders(int pageNo, int pageSize , String sortBy,String seq);

	void updateOrders(List<OrderDto> orderDtos);

	List<OrderDto> getOrdersById(long userId);
}
