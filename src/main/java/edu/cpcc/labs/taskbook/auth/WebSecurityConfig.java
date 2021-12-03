package edu.cpcc.labs.taskbook.auth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserSecureCredentialsService userSecureCredentialsService;
	
	// NOTE: this is needed to ensure that the password is not stored in clear text - uses passsword encoding.
	// So...you need to create this bean and spring will use it at startup.
	@Bean
    public PasswordEncoder passwordEncoder() {		
		// NOTE: Do not do this....this means that the password is not encrypted.
		// return NoOpPasswordEncoder.getInstance();		
        return new BCryptPasswordEncoder();
    }
	
	// NOTE: AUTHENTICATION - WHO AM I ?
	// You could also do this..
	// @Override
	// public void configure(AuthenticationManagerBuilder auth) throws Exception {
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {		
		
		// NOTE: need to register the user secure credentials service which implements a user details service interface.
		auth
         .userDetailsService(userSecureCredentialsService)
         .passwordEncoder(passwordEncoder());
        
        // NOTE: 
        // you can also do an in-memory authentication with a test user name and password as so...
		//        auth
		//		.inMemoryAuthentication()
		//			.withUser("user").password("password").roles("USER");
        
    }
	
	
	// NOTE: AUTHORIZATION - WHAT CAN I DO ?
	// Overrides the method from the base class.
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Watch this video:  cross origin requests enabled - https://www.youtube.com/watch?v=tcLW5d0KAYE
	    http.cors() 
	        .and()
	        // CSRF= Cross Site Request Forgery - Watch this video: https://www.youtube.com/watch?v=5cWSdMUsy0o - enabled by default in a spring boot app
	        .csrf().disable() 
	        // Here are the request patterns that are we authorizing to go thru....
	        .authorizeRequests().antMatchers(HttpMethod.GET, "/**").permitAll()  //#1 - Allow any GET end point pattern to go thru --- no bearer token required in request
            	// .antMatchers(HttpMethod.PUT, "/**").permitAll()               //#2 - Any PUT end point pattern must be authenticated - requires bearer token            
            	// .antMatchers(HttpMethod.DELETE, "/**").permitAll()			 //#3 - Any DELETE end point pattern must be authenticated - requires bearer token
            	// .antMatchers(HttpMethod.POST, "/**").permitAll()				 //#4 - Any POST end point pattern must be authenticated - requires bearer token
            	.antMatchers("/",
                AuthConstants.SIGN_IN_URL,
                AuthConstants.SIGN_UP_URL).permitAll()							//#5 - ("/",SIGN_IN_URL,SIGN_UP_URL) - GO IN UNRESTRICTED
            	// Another other request will be authenticated...
            .anyRequest().authenticated()
            .and()
            // And we are adding these filters and ensuring that session management is STATELESS...
            .addFilter(new JWTAuthenticationFilter(authenticationManager())) // this is our AUTHN filter..
            .addFilter(new JWTAuthorizationFilter(authenticationManager()))  // this is our AUTHZ filter....
            // NOTE: this is for Spring Security to not create a session since this is STATELESS
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); 
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
	    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    CorsConfiguration corsConfig = new CorsConfiguration();
	    corsConfig.applyPermitDefaultValues();
	    corsConfig.setExposedHeaders(Arrays.asList("Authorization"));
	    source.registerCorsConfiguration("/**", corsConfig);
	    return source;	 
	}

}

