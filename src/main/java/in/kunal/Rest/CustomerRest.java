package in.kunal.Rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.kunal.Repo.CustomerRepo;
import in.kunal.Service.JwtService;
import in.kunal.entity.Customer;


@RestController
public class CustomerRest {
	
	@Autowired
	private PasswordEncoder encoder ;
	
	@Autowired
	private CustomerRepo service;
	
	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private JwtService jwtservice;
	
	
	@PostMapping("/register")
	public String register(@RequestBody Customer customer) {
	  String encoded =	encoder.encode(customer.getPwd());
		  customer.setPwd(encoded);
		  service.save(customer);
		  return "Registration Successful";
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login (@RequestBody Customer customer){
		UsernamePasswordAuthenticationToken token  = new UsernamePasswordAuthenticationToken(customer.getCname(), customer.getPwd());
		try {
			Authentication authentication =  manager.authenticate(token);
			
            if (authentication.isAuthenticated()) {
			    String jwttoken = jwtservice.generateToken(customer.getCname());
			    return new ResponseEntity<>(jwttoken, HttpStatus.OK);
			    
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		return new ResponseEntity<>("Invalid Creditials",HttpStatus.BAD_REQUEST);
	}  
	
	
	@GetMapping("/welcome")
	public String getmsg() {
		return "Protected url method only used with jwt token";
	}

}
