package com.customerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.customerservice.entity.SignUp;

import jakarta.transaction.Transactional;

@Repository
public interface SignUpRepo extends JpaRepository<SignUp, Long>{

	@Query(value = "SELECT * FROM sign_up where email_id = :emailId",nativeQuery=true)
	SignUp getByEmailId(String emailId);

	@Query(value = "SELECT count(*) FROM sign_up where email_id = :emailId",nativeQuery=true)
	int findbyEmail(String emailId);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE  sign_up set active = 'Y' where user_id = :user_id",nativeQuery=true)
	void updateActiveStatus(long user_id);
	
	@Query(value = "SELECT count(*) FROM sign_up where email_id = :emailId and active = 'Y'",nativeQuery=true)
	int getByAvticeEmailId(String emailId);

}
