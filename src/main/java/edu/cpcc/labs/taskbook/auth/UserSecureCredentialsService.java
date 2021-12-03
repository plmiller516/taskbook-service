package edu.cpcc.labs.taskbook.auth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.cpcc.labs.taskbook.dao.UserRepository;
import edu.cpcc.labs.taskbook.model.User;




// NOTE: the UserDetailsService is spring security interface.
// NOTE: you can also use @Component or @Repository - in all these cases it will create a single instance of this class. 
@Service 
public class UserSecureCredentialsService implements UserDetailsService {
		
	// Stitching the user repository to this service instance...
	@Autowired
	private UserRepository userRepository;

	// Stitching the password encoder to this service instance...
	@Autowired
	private PasswordEncoder passwordEncoder;

	// NOTE: This method is called by the Spring Security AuthenticationProvider - this method looks up the user using JPA.
	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUserId(), 
				                                                      user.getPassword(), 
				                                                      getAuthorities());
	}

	// NOTE: Typically this method should query a database and return the ACL - access level list.
	private List<SimpleGrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authList = new ArrayList<>();
		authList.add(new SimpleGrantedAuthority("ROLE_USER"));
		return authList;
	}
	
	
	// NOTE: User Details is an interface. 
	// This method is used to save the user for the first time or for a profile update...supports user registration
	public UserDetails save(User newUser) {
		// NOTE: an instance of password encoder is used to encrypt the password when it is stored in the database...
		newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
		User savedUser = userRepository.save(newUser);
		// NOTE: the spring security User class implements a UserDetails interface...
		return new org.springframework.security.core.userdetails.User(savedUser.getUserId(), 
				                                                      savedUser.getPassword(), 
				                                                      getAuthorities());
	}

}
