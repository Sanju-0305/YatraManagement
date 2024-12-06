package com.customerservice.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.customerservice.entity.SignUp;
import com.customerservice.repository.SignUpRepo;
import com.customerservice.service.LoginService;

@Service
public class LoginServiceImpl implements UserDetailsService,LoginService{
	
	@Autowired
	private SignUpRepo signUpRepo;
	
	@Override
	public boolean hasUserWithEmail(String email) {
		int userCount = signUpRepo.findbyEmail(email);
			
		return userCount>0?true:false;
	}

	@Override
	public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
		
		SignUp user = new SignUp();
		try {
         user = signUpRepo.getByEmailId(emailId);
         if(null==user) {
        	 throw new UsernameNotFoundException("User Not Found");
         }
         
        }
		catch (Exception e) {
			throw new UsernameNotFoundException(e.getMessage());
		}
        return user;
	}

}
