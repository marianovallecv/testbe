package ar.com.marianovalle.testbe.domain.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Model of candidate.
 * @author Mariano Valle
 * @version 1.0
 *
 */
@Entity
@Table(name = "candidates")
public class MDLCandidate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@NotEmpty
	@Size(min = 4, max = 20, message = "The full name must be between 4 and 15 characters.")
	@Column(name = "full_name", length = 20, nullable = false)
	private String fullName;

	@NotNull
	@Column(name = "document", unique = true, length = 8, nullable = false)
	private Integer document;
	
	@NotNull
    @Column(name = "birth", nullable = false)
	private Date birth;
    
	@Column(name = "address", length = 50)
	private String address;
	
	@Column(name = "phone", length = 15)
	private String phone;

	@NotEmpty
	@Email
	@Column(name = "email", unique = true, length = 100)
	private String email;

	public MDLCandidate() {
	}

	public MDLCandidate(
			@NotEmpty @Size(min = 4, max = 20, message = "The full name must be between 4 and 15 characters.") String fullName,
			@NotNull Integer document,
			@NotNull Date birth, String address, String phone, @NotEmpty @Email String email) {
		super();
		this.fullName = fullName;
		this.document = document;
		this.birth = birth;
		this.address = address;
		this.phone = phone;
		this.email = email;
	}

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
