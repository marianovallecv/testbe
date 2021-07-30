package ar.com.marianovalle.testbe.domain.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * DTO of user.
 * @author Mariano Valle
 * @version 1.0
 *
 */
public class DTOUserDetails implements Serializable{

    private static final long serialVersionUID = 1L;
    
	Integer id;
	
	@NotBlank
	private String username;
	
	@NotBlank
	private String password;
	
	@NotNull
	private Set<String> roles = new HashSet<>();
	
	private boolean updPass = false;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Set<String> getRoles() {
		return roles;
	}
	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
	public boolean isUpdPass() {
		return updPass;
	}
	public void setUpdPass(boolean updPass) {
		this.updPass = updPass;
	}
}
