package ar.com.marianovalle.testbe.service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.marianovalle.testbe.domain.model.MDLRoleUser;
import ar.com.marianovalle.testbe.service.interfaces.INTRoleUser;
import ar.com.marianovalle.testbe.service.repository.REPRoleUser;
import ar.com.marianovalle.testbe.util.enums.ENURoleUserName;

/**
 * Service of role.
 * @author Mariano Valle
 * @version 1.0
 *
 */
@Service
public class SRVRoleUser implements INTRoleUser{

	@Autowired
	private REPRoleUser repository;
	
	@Override
	@Transactional
	public MDLRoleUser save(MDLRoleUser smcRole) {
		return repository.save(smcRole);
	}

	@Override
	@Transactional	
	public void deleteById(Integer id) {
		repository.deleteById(id);		
	}

	@Override
    @Transactional(readOnly = true)	
	public Optional<MDLRoleUser> findById(Integer id) {
        return repository.findById(id);
	}

    @Override
    @Transactional(readOnly = true)
	public List<MDLRoleUser> findAll() {
		return repository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<MDLRoleUser> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }	

	@Override
    @Transactional(readOnly = true)
	public Optional<MDLRoleUser> findByRoleName(ENURoleUserName roleName) {
		return repository.findByRoleName(roleName);
	}	
	
	@Override
    @Transactional(readOnly = true)
	public boolean existsById(int id) {
		return false;
	}
	
	@Override
    @Transactional(readOnly = true)
	public boolean existsByRoleName(ENURoleUserName roleName) {
		return false;
	}
}
