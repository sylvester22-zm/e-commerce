package com.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.domain.CartProduct;
import com.domain.ShoppingCart;
@Transactional
public interface CartProductRepository extends JpaRepository<CartProduct, Long>{

	List<CartProduct> findByShoppingCart(ShoppingCart shoppingCart);

}
