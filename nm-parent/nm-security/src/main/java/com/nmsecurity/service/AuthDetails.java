package com.nmsecurity.service;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.nmsecurity.model.AuthRequest;

public class AuthDetails implements UserDetails{

	private static final long serialVersionUID = 9216668572855249766L;			
	private AuthRequest authRequest;	
	
	public AuthDetails(AuthRequest authRequest) {
		this.authRequest = authRequest;		
	}			
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {		
		return Arrays.asList(new SimpleGrantedAuthority(authRequest.getRole()));
	}

	@Override
	public String getPassword() {		
		return authRequest.getPassword();
	}

	@Override
	public String getUsername() {		
		return authRequest.getUserName();
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
		return authRequest.isRoleEnable();
	}

	public AuthRequest getAuthRequest() {
		return authRequest;
	}

}
