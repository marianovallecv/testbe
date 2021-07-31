package ar.com.marianovalle.testbe.service.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.com.marianovalle.testbe.domain.model.MDLCandidate;

/**
 * Repository of candidate.
 * @author Mariano Valle
 * @version 1.0
 *
 */
@Repository
public interface REPCandidate extends JpaRepository<MDLCandidate, Integer>{
	
	public static final String FIND_CANDIDATES = "SELECT full_name, document FROM candidates";
	public static final String FIND_CANDIDATES_FILTER_BY_DOCUMENT = "SELECT full_name, document FROM candidates where document = ?1";
	public static final String FIND_CANDIDATES_FILTER_BY_FULL_NAME = "SELECT full_name, document FROM candidates where full_name = ?1";
	
	Optional <MDLCandidate> findByDocument(Integer document);
	Optional <MDLCandidate> findByFullName(String fullName);
	Optional <MDLCandidate> findByEmail(String email);
	@Query(value = FIND_CANDIDATES, nativeQuery = true)
	<T>Page<T> findAllCandidatesWithPagination(Pageable pageable);
	@Query(value = FIND_CANDIDATES_FILTER_BY_FULL_NAME, nativeQuery = true)
	<T>Page<T> findByFullName(String fullName, Pageable pageable);
	@Query(value = FIND_CANDIDATES_FILTER_BY_DOCUMENT, nativeQuery = true)
	<T>Page<T> findByDocument(String document, Pageable pageable);	
	boolean existsByDocument(Integer document);
    boolean existsByFullName(String fullName);
	boolean existsByEmail(String email);
}
