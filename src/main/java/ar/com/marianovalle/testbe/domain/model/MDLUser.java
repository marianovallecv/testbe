package ar.com.marianovalle.testbe.domain.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import javax.validation.constraints.NotEmpty;

/**
 * Model of user.
 * @author Mariano Valle
 * @version 1.0
 *
 */
@Entity
@Table(name = "users")
public class MDLUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@NotEmpty
	@Size(min = 4, max = 15, message = "The user name must be between 4 and 15 characters.")
	@Column(name = "username", unique = true, length = 15, nullable = false)
	private String username;
	
	@NotEmpty
	@Size(min = 4, max = 60, message = "The password must be between 4 and 15 characters.")
	@Column(name = "password", length = 60, nullable = false)
	private String password;

	@NotNull
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinTable(name = "users_roles", 
	joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"),
	inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id"))
	private Set<MDLRoleUser> roles = new HashSet<>();

	private boolean updPass = false;

	public MDLUser() {
	}
	
	public MDLUser(
			@NotEmpty @Size(min = 4, max = 15, message = "The user name must be between 4 and 15 characters.") String username,
			@NotEmpty @Size(min = 4, max = 60, message = "The password must be between 4 and 15 characters.") String password,
			boolean updPass) {
		super();
		this.username = username;
		this.password = password;
		this.updPass = updPass;
	}

	public Integer getId() {
		return id;
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

	public Set<MDLRoleUser> getRoles() {
		return roles;
	}

	public void setRoles(Set<MDLRoleUser> roles) {
		this.roles = roles;
	}

	public boolean isUpdPass() {
		return updPass;
	}

	public void setUpdPass(boolean updPass) {
		this.updPass = updPass;
	}
	
}
