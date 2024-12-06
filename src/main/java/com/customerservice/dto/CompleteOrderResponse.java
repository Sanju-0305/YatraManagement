package com.customerservice.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CompleteOrderResponse {

	@JsonProperty("orderDetails")
	private List<OrderDto> orderDto;
	
	@JsonProperty("pageDetails")
	private PageDetailsDto pageDetailsDto;

	public List<OrderDto> getOrderDto() {
		return orderDto;
	}

	public void setOrderDto(List<OrderDto> orderDto) {
		this.orderDto = orderDto;
	}

	public PageDetailsDto getPageDetailsDto() {
		return pageDetailsDto;
	}

	public void setPageDetailsDto(PageDetailsDto pageDetailsDto) {
		this.pageDetailsDto = pageDetailsDto;
	}
	
	
}
