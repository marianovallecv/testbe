package ar.com.marianovalle.testbe.service.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ar.com.marianovalle.testbe.domain.model.MDLRoleUser;
import ar.com.marianovalle.testbe.util.enums.ENURoleUserName;

/**
 * Interface of role.
 * @author Mariano Valle
 * @version 1.0
 *
 */
@Service
public interface INTRoleUser {
	MDLRoleUser save(MDLRoleUser smcRole);
	void deleteById(Integer id);
	Optional <MDLRoleUser> findById(Integer id);
	Optional <MDLRoleUser> findByRoleName(ENURoleUserName roleName);
	Page<MDLRoleUser> findAll(Pageable pageable);
	List<MDLRoleUser> findAll();	
    boolean existsById(int id);
    boolean existsByRoleName(ENURoleUserName roleName);
}
