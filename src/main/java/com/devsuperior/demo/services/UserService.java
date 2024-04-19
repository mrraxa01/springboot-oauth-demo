package com.devsuperior.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.devsuperior.demo.entities.Role;
import com.devsuperior.demo.entities.User;
import com.devsuperior.demo.projections.UserDetailsProjection;
import com.devsuperior.demo.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		List<UserDetailsProjection> result= userRepository.searchUserAndRolesByEmail(username); 
		if(result.isEmpty())
			throw new UsernameNotFoundException("Usuário Não encontrado!");
		User user = new User();
		user.setEmail(username);
		user.setPassword(result.get(0).getPassword());
		result.forEach( usr -> user.addRole(
					new Role(usr.getRoleId(),usr.getAuthority())));
		
		return user;
	}

}
