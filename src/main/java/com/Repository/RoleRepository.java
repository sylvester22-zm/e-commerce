package com.Repository;

import org.springframework.data.repository.CrudRepository;

import com.domain.security.Role;



public interface RoleRepository extends CrudRepository<Role, Long> {
	Role findByname(String name);
}
