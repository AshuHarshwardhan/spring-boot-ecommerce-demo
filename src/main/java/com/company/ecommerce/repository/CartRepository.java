package com.company.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.company.ecommerce.model.Cart;
import com.company.ecommerce.model.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

	List<Cart> findAllByUserOrderByCreatedDateDesc(User user);

	List<Cart> deleteByUser(User user);
}
