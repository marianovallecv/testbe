package ar.com.marianovalle.testbe.service.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.marianovalle.testbe.domain.model.MDLUser;
import ar.com.marianovalle.testbe.domain.model.MDLUserDetails;
import ar.com.marianovalle.testbe.service.interfaces.INTUser;
import ar.com.marianovalle.testbe.service.repository.REPUser;

/**
 * Service of user.
 * @author Mariano Valle
 * @version 1.0
 *
 */
@Service
public class SRVUser implements INTUser {
	private static Logger loggerIN = LoggerFactory.getLogger(SRVUser.class);

	@Autowired
	private REPUser repository;

	@Override
	@Transactional
	public MDLUser save(MDLUser user) {
		loggerIN.trace("save");
		return repository.save(user);
	}

	@Override
	@Transactional
	public void deleteById(Integer id) {
		repository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<MDLUser> findById(Integer id) {
		return repository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<MDLUser> findAll() {
		return repository.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<MDLUser> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<MDLUser> findByUsername(String username) {
		return repository.findByUsername(username);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean existsById(int id) {
		return repository.existsById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean existsByUsername(String username) {
		return repository.existsByUsername(username);
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MDLUser mcuUser = repository.findByUsername(username).orElse(null);

		return MDLUserDetails.build(mcuUser);
	}
}
