package edu.cpcc.labs.taskbook.auth;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;

// NOTE: For this filter to kick in for a headless web service call you need to use the /login relative URL
// Check this out (great discussion) -> https://stackoverflow.com/questions/50609233/custom-spring-security-filter-not-called-at-runtime/56055014
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	  private AuthenticationManager authenticationManager;
	  
	  public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
	      this.authenticationManager = authenticationManager;
	      // NOTE: important step - the default used by the  base class is /login...but you can override it.
	      // described here: https://stackoverflow.com/questions/50609233/custom-spring-security-filter-not-called-at-runtime/56055014
	      this.setFilterProcessesUrl(AuthConstants.SIGN_IN_URL);
	  }

	  @Override
	  public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
		  
		  try {
		      edu.cpcc.labs.taskbook.model.User creds = new ObjectMapper().readValue(req.getInputStream(),  edu.cpcc.labs.taskbook.model.User.class);
		      
		      Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getUserId(),
                                                                                         creds.getPassword(),
                                                                                         new ArrayList<>()));	
		      	
		      return authentication;
		      
	      } 
		  catch (IOException e) {
	    	  e.printStackTrace();
	    	  throw new RuntimeException(e);
	      }
		  catch (AuthenticationException ex) {
			  ex.printStackTrace();
			  throw ex;
		  }
	  }

	  // NOTE: Create a signed JWT  upon successful authentication
	  @Override
	  protected void successfulAuthentication(HttpServletRequest req, 
			                                  HttpServletResponse res, 
			                                  FilterChain chain, 
			                                  Authentication auth) throws IOException, ServletException {
		  // NOTE: upon successful authentication creates a signed JWT - you can also set the expiry of the token - the default is an hour..
		  String token = JWT.create()
				  			// .withSubject(((User) auth.getPrincipal()).getUsername())
				  			// NOTE: had to qualify this user as the Spring user details user.
				            .withSubject(((org.springframework.security.core.userdetails.User)auth.getPrincipal()).getUsername())
				  			.withExpiresAt(new Date(System.currentTimeMillis() + AuthConstants.EXPIRATION_TIME))
				  			.sign(HMAC512(AuthConstants.SECRET.getBytes()));
	    
		  // sending the JWT in the response header...
		  res.addHeader(AuthConstants.HEADER_STRING, 
				        AuthConstants.TOKEN_PREFIX + token);
		  
		  // sending the JWT in the response body...
		  // NOTE: https://stackoverflow.com/questions/51999591/sending-jwt-token-in-the-body-of-response-java-spring
		  res.setContentType("application/json");
		  res.setCharacterEncoding("UTF-8");
		  res.getWriter().write("{\"" + AuthConstants.HEADER_STRING + "\":\"" + AuthConstants.TOKEN_PREFIX + token + "\"}");
		  
	  }

	  
	  @Override
	  protected void unsuccessfulAuthentication(HttpServletRequest req, HttpServletResponse res, AuthenticationException failed) throws IOException, ServletException {
	      super.unsuccessfulAuthentication(req, res, failed);	    
	  }

}
