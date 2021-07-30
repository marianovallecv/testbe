package ar.com.marianovalle.testbe.domain.projection;

import org.springframework.stereotype.Component;

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