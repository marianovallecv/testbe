package ar.com.marianovalle.testbe.service.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.com.marianovalle.testbe.domain.model.MDLCandidate;
import ar.com.marianovalle.testbe.domain.projection.PRJCandidate;

/**
 * Repository of candidate.
 * @author Mariano Valle
 * @version 1.0
 *
 */
@Repository
public interface REPCandidate extends JpaRepository<MDLCandidate, Integer>{
	
	public static final String FIND_CANDIDATES = "SELECT full_name, document FROM candidates";
	
	Optional <MDLCandidate> findByDocument(Integer document);
	Optional <MDLCandidate> findByFullName(String fullName);
	Optional <MDLCandidate> findByEmail(String email);
	@Query(value = FIND_CANDIDATES, nativeQuery = true)
	<T>Page<T> findAllCandidatesWithPagination(Pageable pageable);
	boolean existsByDocument(Integer document);
    boolean existsByFullName(String fullName);
	boolean existsByEmail(String email);
}
