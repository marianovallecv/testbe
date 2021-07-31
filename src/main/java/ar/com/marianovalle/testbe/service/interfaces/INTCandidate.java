package ar.com.marianovalle.testbe.service.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ar.com.marianovalle.testbe.domain.model.MDLCandidate;

/**
 * Interface of candidate.
 * @author Mariano Valle
 * @version 1.0
 *
 */
@Service
public interface INTCandidate{
	
	MDLCandidate save(MDLCandidate candidate);
	void deleteById(Integer id);
	Optional <MDLCandidate> findById(Integer id);
	Optional <MDLCandidate> findByDocument(Integer document);
	Optional <MDLCandidate> findByFullName(String fullName);
	Optional <MDLCandidate> findByEmail(String email);
	boolean existsById(int id);
	boolean existsByDocument(Integer document);
	boolean existsByFullName(String fullName);
	boolean existsByEmail(String email);
	Page<MDLCandidate> findAll(Pageable pageable);
	<T>Page<T> findAllCandidatesWithPagination(Pageable pageable);	
	<T>Page<T> findByDocument(String document, Pageable pageable);
	<T>Page<T> findByFullName(String fullName, Pageable pageable);
	List<MDLCandidate> findAll();
}
