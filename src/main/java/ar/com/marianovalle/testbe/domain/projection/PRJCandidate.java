package ar.com.marianovalle.testbe.domain.projection;

import org.springframework.stereotype.Component;

import ar.com.marianovalle.testbe.domain.model.MDLCandidate;

/**
 * Projection of candidate.
 * Se usa para devolver campos específicos.
 * @author Mariano Valle
 * @version 1.0
 *
 */
@Component
public interface PRJCandidate{
	
	String getFullName();
	String getDocument();

}