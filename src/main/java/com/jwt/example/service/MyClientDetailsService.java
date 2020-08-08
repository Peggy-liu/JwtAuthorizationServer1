package com.jwt.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import com.jwt.example.entity.MyClientDetails;
import com.jwt.example.repository.ClientRepository;


@Service
public class MyClientDetailsService implements ClientDetailsService {

	@Autowired
	private ClientRepository repo;
	
	
	@Override
	public ClientDetails loadClientByClientId(String clientId)  {
		return repo.findClientByClientId(clientId)
				.map(c -> new MyClientDetails(c))
				.orElseThrow(()->new ClientRegistrationException("error"));
				
				
				
	}

}
