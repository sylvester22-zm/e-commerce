package com.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByEmail(String email);

	User findByUsername(String username);




}
