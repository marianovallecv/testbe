package ar.com.marianovalle.testbe.jwt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * JWT entry point.
 * Verifica si existe un token, sino devuelve un 401.
 * @author Mariano Valle
 * @version 1.0
 *
 */
@Component
public class JWTEntryPoint implements AuthenticationEntryPoint{
	
	//Para identificar la clase que genera error
	private static final Logger logger = LoggerFactory.getLogger(JWTEntryPoint.class);
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

		logger.error("Error en el metodo commence: " + authException.getMessage());
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "no autorizado");
	}
}

