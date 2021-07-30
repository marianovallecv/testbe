package ar.com.marianovalle.testbe.domain.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Model of user login.
 * @author Mariano Valle
 * @version 1.0
 *
 */
public class MDLUserDetails implements UserDetails, Serializable{

    private static final long serialVersionUID = 1L;
	
	private String userName;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	
	public MDLUserDetails(String userName, String password, Collection<? extends GrantedAuthority> authorities) {
		this.userName = userName;
		this.password = password;
		this.authorities = authorities;
	}

	public static MDLUserDetails build(MDLUser user) {
		List<GrantedAuthority> authorities =
		user.getRoles().stream()
		.map(role -> new SimpleGrantedAuthority(role.getRoleName().name())).collect(Collectors.toList());
	
		return new MDLUserDetails(user.getUsername(), user.getPassword(), authorities);
	}
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	@Override
	public String getPassword() {
		return password;
	}
	@Override
	public String getUsername() {
		return userName;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
}
