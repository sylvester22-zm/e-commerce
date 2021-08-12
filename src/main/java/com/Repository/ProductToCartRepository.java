package com.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.domain.CartProduct;
import com.domain.ProductToCart;

@Transactional
public interface ProductToCartRepository extends JpaRepository<ProductToCart, Long>{

	void deleteByCartProduct(CartProduct cartproduct);

	

	

}
