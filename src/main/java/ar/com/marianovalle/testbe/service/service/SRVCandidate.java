package ar.com.marianovalle.testbe.service.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.marianovalle.testbe.domain.model.MDLCandidate;
import ar.com.marianovalle.testbe.service.interfaces.INTCandidate;
import ar.com.marianovalle.testbe.service.repository.REPCandidate;

/**
 * Service of candidate.
 * @author Mariano Valle
 * @version 1.0
 *
 */
@Service
public class SRVCandidate implements INTCandidate {
	  
	@Override
	public Page<Object> findAllCandidatesWithPagination(Pageable pageable) {
		return repository.findAllCandidatesWithPagination(pageable);
	}
	
	@Override
	public Page<Object> findByFullName(String fullName, Pageable pageable) {
		return repository.findByFullName(fullName, pageable);
	}
	
	@Override
	public Page<Object> findByDocument(String document, Pageable pageable) {
		return repository.findByDocument(document, pageable);
	}

	private static Logger loggerIN = LoggerFactory.getLogger(SRVCandidate.class);

	@Autowired
	private REPCandidate repository;

	@Override
	@Transactional
	public MDLCandidate save(MDLCandidate candidate) {
		loggerIN.trace("save");
		return repository.save(candidate);
	}

	@Override
	@Transactional
	public void deleteById(Integer id) {
		repository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<MDLCandidate> findById(Integer id) {
		return repository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<MDLCandidate> findAll() {
		return repository.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<MDLCandidate> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<MDLCandidate> findByDocument(Integer document) {
		return repository.findByDocument(document);
	}	
	
	@Override
	@Transactional(readOnly = true)
	public Optional<MDLCandidate> findByFullName(String fullName) {
		return repository.findByFullName(fullName);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<MDLCandidate> findByEmail(String email) {
		return repository.findByEmail(email);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean existsById(int id) {
		return repository.existsById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean existsByDocument(Integer document) {
		return repository.existsByDocument(document);
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean existsByFullName(String fullName) {
		return repository.existsByFullName(fullName);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean existsByEmail(String email) {
		return repository.existsByEmail(email);
	}

}
