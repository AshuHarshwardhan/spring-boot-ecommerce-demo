package com.company.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.ecommerce.model.User;
import com.company.ecommerce.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository repository;

	public List<User> listUsers() {
		return repository.findAll();
	}

	public Optional<User> findById(Integer id) {
		return repository.findById(id);
	}

	public User findByEmail(String email) {
		return repository.findByEmail(email);
	}

	public User createUser(User user) {
		return repository.save(user);
	}
}
