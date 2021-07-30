package ar.com.marianovalle.testbe.jwt;

import java.util.Date;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import ar.com.marianovalle.testbe.domain.model.MDLUserDetails;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * JWT provider.
 * Genera un token, con metodos de validacion.
 * @author Mariano Valle
 * @version 1.0
 *
 */
@Component
public class JWTProvider {
	private static final Logger loggerIn = LoggerFactory.getLogger(JWTProvider.class);

	@Value("${jwt.secret}")
	private String secret;
	@Value("${jwt.expiration}")
	private int expiration;
	
	public String generateToken(Authentication authentication) {
		MDLUserDetails principalUser = (MDLUserDetails) authentication.getPrincipal();

		return Jwts.builder()
		.setSubject(principalUser.getUsername())
		.setIssuedAt(new Date())
		.setExpiration(new Date(new Date().getTime() + expiration  * 1000))
		.signWith(SignatureAlgorithm.HS512, secret)
		.compact();
	}
	
	public String getUserNameFromToken(String token) {
	
		return Jwts.parser()
		.setSigningKey(secret)
		.parseClaimsJws(token)		
		.getBody()
		.getSubject();
	}
	
	public boolean validateToken(String token) {
		loggerIn.warn("validateToken");
		try {
			Jwts.parser()
			.setSigningKey(secret)
			.parseClaimsJws(token);
			loggerIn.warn("validateToken OK");
			return true;
		} catch (MalformedJwtException e) {
			loggerIn.info("token mal formado: {} ", e.getMessage());
		} catch (UnsupportedJwtException e) {
			loggerIn.error("token no soportado: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			loggerIn.error("token expirado: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			loggerIn.error("token vacio");
		} catch (SignatureException e) {
			loggerIn.error("Error en la firma: {}", e.getMessage());
		}
		loggerIn.warn("validateToken ERR");
		return false;
	}
}