package com.customerservice.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class EventsData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long eventId;
	private String event_name;
	private Date start_date;
	private Date end_date;
	private int cost_per_person;
	private String image_date;
	public long getEventId() {
		return eventId;
	}
	public void setEventId(long eventId) {
		this.eventId = eventId;
	}
	public String getEvent_name() {
		return event_name;
	}
	public void setEvent_name(String event_name) {
		this.event_name = event_name;
	}
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	public int getCost_per_person() {
		return cost_per_person;
	}
	public void setCost_per_person(int cost_per_person) {
		this.cost_per_person = cost_per_person;
	}
	public String getImage_date() {
		return image_date;
	}
	public void setImage_date(String image_date) {
		this.image_date = image_date;
	}
	
	
}
