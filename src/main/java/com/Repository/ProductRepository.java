package com.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.domain.Product;
import com.domain.User;

public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findByproductnameContaining(String product);

	/*
	 * @Query("SELECT t.title FROM Todo t where t.id = :id") Optional<Book>
	 * findById(@Param("id") Long id);
	 */

	
}
