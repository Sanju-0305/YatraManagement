package com.customerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.customerservice.entity.EventsData;

@Repository
public interface EventsRepo extends JpaRepository<EventsData, Long>{

}
