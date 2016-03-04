package spring.boot.web.services;

import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import spring.boot.web.entities.User;

@Service
// @Transactional(readOnly = true)
public class AuthenticationService implements UserDetailsService {

	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

		User user = new User();

		if (user != null && "admin".equals(login)) {
			user.setLoginName("admin");
			user.setPassword("admin");
			return new org.springframework.security.core.userdetails.User(user.getLoginName(), user.getPassword(),
					Collections.singleton(createAuthority(user)));
		}

		else {
			throw new UsernameNotFoundException("User not found");
		}
	}

	private GrantedAuthority createAuthority(User user) {
		return new SimpleGrantedAuthority("ROLE_USER");
	}

	// PUBLIC COLLECTION<? EXTENDS GRANTEDAUTHORITY> GETAUTHORITIES(SET<ROLE>
	// ROLES) {
	// LIST<GRANTEDAUTHORITY> AUTHORITIES = NEW ARRAYLIST<GRANTEDAUTHORITY>();
	// FOR (ROLE ROLE : ROLES) {
	// AUTHORITIES.ADD(NEW SIMPLEGRANTEDAUTHORITY(ROLE.NAME()));
	// }
	// RETURN AUTHORITIES;
	// }

}
