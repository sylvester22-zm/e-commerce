package com.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.domain.ShoppingCart;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long>{

}
