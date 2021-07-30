package ar.com.marianovalle.testbe.domain.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

/**
 * DTO of Login.
 * Se usa al hacer login.
 * @author Mariano Valle
 * @version 1.0
 *
 */
public class DTOLoginUser implements Serializable{

    private static final long serialVersionUID = 1L;

	@NotBlank
	private String userName;
	@NotBlank
	private String password;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
