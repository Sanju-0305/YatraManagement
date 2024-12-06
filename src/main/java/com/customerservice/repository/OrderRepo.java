package com.customerservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.customerservice.entity.Order;
import com.customerservice.entity.SignUp;

import jakarta.transaction.Transactional;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long>{
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE order_details set pnr_no =:pnr_no,seat_no =:seat_no where order_id =:order_id",nativeQuery=true)
	void getUpdatePnrSeat(long order_id,String pnr_no,String seat_no);

	@Query(value = "SELECT * FROM order_details where user_id = :user_id",nativeQuery=true)
	List<Order> findAllOrderById(long user_id);
}

