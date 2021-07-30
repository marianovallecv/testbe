package ar.com.marianovalle.testbe.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import ar.com.marianovalle.testbe.service.interfaces.INTUser;

/**
 * JWT token filter.
 * Genera un token, con metodos de validacion.
 * @author Mariano Valle
 * @version 1.0
 *
 */
public class JWTTokenFilter extends OncePerRequestFilter{
	private static final Logger loggerIn = LoggerFactory.getLogger(JWTTokenFilter.class);

	@Autowired
	JWTProvider jwtProvider;
	
	@Autowired
	INTUser ssiUser;
	
	//Verifica el token y genera la autenticacion
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String token = getToken(request);
			loggerIn.warn(token);
			if(token != null && jwtProvider.validateToken(token)) {
				String userName = jwtProvider.getUserNameFromToken(token);
				
				UserDetails userDetails = ssiUser.loadUserByUsername(userName);
				
				UsernamePasswordAuthenticationToken authentication =
				new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				
				//Asignamos el usuario al contexto de autebticacion
				SecurityContextHolder.getContext().setAuthentication(authentication);
				loggerIn.warn("doFilterInternal OK");
			}
		} catch (Exception e) {
			loggerIn.error("Error en el metodo doFilterInternal {}", e.getMessage());
		}
		
		filterChain.doFilter(request, response);
		loggerIn.warn("doFilterInternal FIN");
	}
	
	private String getToken(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		loggerIn.warn(header);
		if(header != null && header.startsWith("Bearer")) {
			loggerIn.warn("getToken");
			return header.replace("Bearer ", "");
		}else {
			return null;
		}
	}
}
