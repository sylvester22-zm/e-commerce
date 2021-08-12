package com.service;

import java.util.List;


import com.domain.ShoppingCart;



public interface ShoppingCartService {
	List<ShoppingCart> findAll();
	ShoppingCart updateCartProducts(ShoppingCart myCart);
		
	void clearcart(ShoppingCart cart);
	
}
