package com.jwt.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jwt.example.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Integer> {
	Optional<Client> findClientByClientId(String clientId);
}
