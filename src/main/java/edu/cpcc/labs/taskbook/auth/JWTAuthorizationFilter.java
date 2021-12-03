package edu.cpcc.labs.taskbook.auth;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

// FIXME: these are for validating JWT tokens...not implemented yet...
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.MalformedJwtException;
//import io.jsonwebtoken.UnsupportedJwtException;
//import io.jsonwebtoken.security.SignatureException;



// NOTE: we want to listen to every request for the Authorization Header to look for the bearer and hence this filter.
// Intercept request
// public class JWTAuthorizationFilter extends OncePerRequestFilter {
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
	
	//	
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
	    super(authenticationManager);	  
	}
	
	// NOTE: Uses the chain of responsibilities pattern...
	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
		// Probes the "Bearer" information in the Authorization Header.
	    String header = req.getHeader(AuthConstants.HEADER_STRING);
	    if (header == null || !header.startsWith(AuthConstants.TOKEN_PREFIX)) {
	      chain.doFilter(req, res);
	      return;
	    }
	    UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
	    SecurityContextHolder.getContext().setAuthentication(authentication);
	    chain.doFilter(req, res);	  
	}
	
	
	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
	    String token = request.getHeader(AuthConstants.HEADER_STRING);
	    if (token != null) {
	    	// HS: validate token - not working as yet...
	    	// validateToken(token, request); 
    		String user = JWT.require(Algorithm.HMAC512(AuthConstants.SECRET.getBytes()))
	    					 .build()
						     .verify(token.replace(AuthConstants.TOKEN_PREFIX, ""))
						     .getSubject();
	      
	  	  	if (user != null) {
	          return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
	  	  	}
	    	return null;
	    }	    
	    return null;    
	}
	
//	// NOTE: 
//	// https://github.com/jwtk/jjwt 
//	private boolean validateToken(String token, HttpServletRequest httpServletRequest){
//	        try {
//	        	Jwts.parserBuilder().setSigningKey(AuthConstants.SECRET).build().parseClaimsJws(token);
//	            return true;
//	        }catch (SignatureException ex){
//	            System.out.println("Invalid JWT Signature");
//	            throw ex;
//	        }catch (MalformedJwtException ex){
//	            System.out.println("Invalid JWT token");
//	            throw ex;
//	        }catch (ExpiredJwtException ex){
//	            System.out.println("Expired JWT token");
//	            httpServletRequest.setAttribute("expired",ex.getMessage());
//	            throw ex;
//	        }catch (UnsupportedJwtException ex){
//	            System.out.println("Unsupported JWT exception");
//	            throw ex;
//	        }catch (IllegalArgumentException ex){
//	            System.out.println("Jwt claims string is empty");
//	            throw ex;
//	        }
//	}

}

