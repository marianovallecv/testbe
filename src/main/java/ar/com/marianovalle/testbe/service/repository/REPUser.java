package ar.com.marianovalle.testbe.service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.marianovalle.testbe.domain.model.MDLUser;

/**
 * Repository of user.
 * @author Mariano Valle
 * @version 1.0
 *
 */
@Repository
public interface REPUser extends JpaRepository<MDLUser, Integer>{
	Optional <MDLUser> findByUsername(String username);
    boolean existsByUsername(String username);
}
