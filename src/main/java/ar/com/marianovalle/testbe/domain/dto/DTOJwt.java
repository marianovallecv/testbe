package ar.com.marianovalle.testbe.domain.dto;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

/**
 * DTO of JWT.
 * Se usa al hacer login y devuelve un Json Web Token
 * @author Mariano Valle
 * @version 1.0
 *
 */
public class DTOJwt implements Serializable{

  private static final long serialVersionUID = 1L;
	
	private String token;
	private String bearer = "Bearer";
	private String userName;
	private Collection<? extends GrantedAuthority> authorities;
	
	public DTOJwt(String token, String userName, Collection<? extends GrantedAuthority> authorities) {
		super();
		this.token = token;
		this.userName = userName;
		this.authorities = authorities;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getBearer() {
		return bearer;
	}
	public void setBearer(String bearer) {
		this.bearer = bearer;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
	
}