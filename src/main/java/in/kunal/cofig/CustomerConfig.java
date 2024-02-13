package in.kunal.cofig;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import in.kunal.Service.MyUserdetails;
import in.kunal.filter.AppFilter;

@Configuration
@EnableWebSecurity
public class CustomerConfig {
	
	@Autowired
	private MyUserdetails userinfo;
	
	@Autowired
	private AppFilter filter;
	
	@Bean
	public PasswordEncoder encoder () {
		return new BCryptPasswordEncoder();
	}
	
	  
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
	      authenticationProvider.setUserDetailsService(userinfo);
	      authenticationProvider.setPasswordEncoder(encoder());
	      return authenticationProvider;
	}
	
	@Bean
	public AuthenticationManager auth (AuthenticationConfiguration config) throws Exception{
		return config.getAuthenticationManager();
	}
	
	 @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        return http.csrf().disable()
	                .authorizeHttpRequests()
	                .requestMatchers("/login","/register").permitAll()
	                .and()
	                .authorizeHttpRequests().requestMatchers("/**")
	                .authenticated()
	                .and()
	                .sessionManagement()
	                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	                .and()
	                .authenticationProvider(authenticationProvider())
	                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class).build();

	    }
	
	
	
		
	}
