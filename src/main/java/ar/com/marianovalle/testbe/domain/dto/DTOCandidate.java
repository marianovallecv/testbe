package ar.com.marianovalle.testbe.domain.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO of candidate.
 * @author Mariano Valle
 * @version 1.0
 *
 */
public class DTOCandidate implements Serializable{

    private static final long serialVersionUID = 1L;

	private Integer id;

	private String fullName;

	private Integer document;
	
	private Date birth;
    
	private String address;
	
	private String phone;

	private String email;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Integer getDocument() {
		return document;
	}

	public void setDocument(Integer document) {
		this.document = document;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
