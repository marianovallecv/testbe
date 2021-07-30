package ar.com.marianovalle.testbe.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import ar.com.marianovalle.testbe.util.enums.ENURoleUserName;

/**
 * Model of role.
 * @author Mariano Valle
 * @version 1.0
 *
 */
@Entity
@Table(name = "user_roles")
public class MDLRoleUser {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@NotEmpty
	@Size(min = 4, max = 20, message = "The role name must be between 4 and 15 characters.")
	@Enumerated(EnumType.STRING)
	private ENURoleUserName roleName;

	public MDLRoleUser() {
		super();
	}

	public MDLRoleUser(
			@NotEmpty @Size(min = 4, max = 20, message = "The role name must be between 4 and 15 characters.") ENURoleUserName roleName) {
		super();
		this.roleName = roleName;
	}

	public Integer getId() {
		return id;
	}

	public ENURoleUserName getRoleName() {
		return roleName;
	}

	public void setRoleName(ENURoleUserName roleName) {
		this.roleName = roleName;
	}
	
}
