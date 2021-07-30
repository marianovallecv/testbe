package ar.com.marianovalle.testbe.service.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ar.com.marianovalle.testbe.domain.model.MDLUser;

/**
 * Interface of user.
 * @author Mariano Valle
 * @version 1.0
 *
 */
@Service
public interface INTUser extends UserDetailsService{
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;	
 	MDLUser save(MDLUser user);
	void deleteById(Integer id);
	Optional <MDLUser> findById(Integer id);
	Optional <MDLUser> findByUsername(String username);
	boolean existsById(int id);
	boolean existsByUsername(String username);
	Page<MDLUser> findAll(Pageable pageable);
	List<MDLUser> findAll();
}
