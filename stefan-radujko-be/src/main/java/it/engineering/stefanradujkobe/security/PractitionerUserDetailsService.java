package it.engineering.stefanradujkobe.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.engineering.stefanradujkobe.dao.PractitionerDao;
import it.engineering.stefanradujkobe.entity.PractitionerEntity;

@Service
public class PractitionerUserDetailsService implements UserDetailsService {
	@Autowired
	private PractitionerDao practitionerDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		System.out.println("=================== loadUserByUsername(String username) =================");
//		System.out.println(username);
		Optional<PractitionerEntity> user = practitionerDao.findByUsername(username);
//		System.out.println("user: " + user);
		user.orElseThrow(()->new UsernameNotFoundException("User " + username+ " does not exist!"));
//		System.out.println(user.get());
		return new PractitionerUserDetails(user.get().getUsername(), user.get().getPassword(),user.get().getName(), user.get().getSurname());
	}
}
