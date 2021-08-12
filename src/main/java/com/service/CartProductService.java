package com.service;

import java.util.List;

import com.domain.CartProduct;
import com.domain.Order;
import com.domain.Product;
import com.domain.ShoppingCart;
import com.domain.User;

public interface CartProductService {

	  CartProduct addtoCart( Product product,User user,int quantity);
	  List<CartProduct> findByShoppingCart(ShoppingCart shoppingCart);
	 void deleteCartProduct(CartProduct cartproduct);
	 //the method responsible for deleting an item from cart
	
	CartProduct findById(Long product);
	CartProduct updateCartProduct(CartProduct cartProduct);
	CartProduct save(CartProduct cartProduct);
	List<CartProduct> findByOrder(Order order); 
}
