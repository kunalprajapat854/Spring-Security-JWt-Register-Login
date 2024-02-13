package in.kunal.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import in.kunal.Service.JwtService;
import in.kunal.Service.MyUserdetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class AppFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtService jwtservice;
	
	@Autowired
	private MyUserdetails info;


	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		      String authheader = request.getHeader("Authorization");
		      String token = null;
		      String username = null;
		      
		      if(authheader !=null && authheader.startsWith("Bearer ")) {
		             token = authheader.substring(7);
		          username = jwtservice.extractUsername(token);
		      }
	
	      
	         if(username !=null && SecurityContextHolder.getContext().getAuthentication()==null) {
	        	UserDetails details =   info.loadUserByUsername(username);
	        	if(jwtservice.validateToken(token, details)) {
	        		UsernamePasswordAuthenticationToken passwordAuthenticationToken = new  UsernamePasswordAuthenticationToken(username, null, details.getAuthorities());
	        		passwordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	        		SecurityContextHolder.getContext().setAuthentication(passwordAuthenticationToken);
	       
	        	}
	         }
	         
	         filterChain.doFilter(request, response);
		             
		
	}
}


