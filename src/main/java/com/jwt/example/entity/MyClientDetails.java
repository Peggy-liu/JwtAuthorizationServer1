package com.jwt.example.entity;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

public class MyClientDetails implements ClientDetails {

	private final Client client;

	public MyClientDetails(Client client) {
		this.client = client;
	}

	@Override
	public String getClientId() {
		return client.getClient_id();
	}

	@Override
	public Set<String> getResourceIds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSecretRequired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getClientSecret() {
		// TODO Auto-generated method stub
		return client.getSecret();
	}

	@Override
	public boolean isScoped() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Set<String> getScope() {
		Set<String> result = new HashSet<String>();
		result.add(client.getScope());
		return result;
	}

	@Override
	public Set<String> getAuthorizedGrantTypes() {
		Set<String> result = new HashSet<String>();
		result.add(client.getGrant_type());
		return result;
	}

	@Override
	public Set<String> getRegisteredRedirectUri() {
		Set<String> result = new HashSet<String>();
		result.add("http://localhost:8081/home");
		return result;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return Collections.singleton(new SimpleGrantedAuthority(client.getScope()));
	}

	@Override
	public Integer getAccessTokenValiditySeconds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getRefreshTokenValiditySeconds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAutoApprove(String scope) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Map<String, Object> getAdditionalInformation() {
		// TODO Auto-generated method stub
		return null;
	}

}
