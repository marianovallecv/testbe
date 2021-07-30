package ar.com.marianovalle.testbe.service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.marianovalle.testbe.domain.model.MDLRoleUser;
import ar.com.marianovalle.testbe.util.enums.ENURoleUserName;

/**
 * Repository of role.
 * @author Mariano Valle
 * @version 1.0
 *
 */
@Repository
public interface REPRoleUser extends JpaRepository<MDLRoleUser, Integer> {
	Optional <MDLRoleUser> findByRoleName(ENURoleUserName roleName);
	boolean existsByRoleName(String roleName);
}
