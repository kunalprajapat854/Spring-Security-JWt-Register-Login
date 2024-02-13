package in.kunal.Service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.kunal.Repo.CustomerRepo;
import in.kunal.entity.Customer;

@Service

public class MyUserdetails implements UserDetailsService {
	
	@Autowired
	private CustomerRepo service;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 Customer  customer =   service.findBycname(username);
		return new User(customer.getCname(), customer.getPwd(), Collections.emptyList());
	}

}
