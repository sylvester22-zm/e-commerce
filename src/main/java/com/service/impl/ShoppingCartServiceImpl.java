package com.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Repository.CartProductRepository;
import com.Repository.ShoppingCartRepository;
import com.domain.CartProduct;
import com.domain.ShoppingCart;
import com.service.CartProductService;
import com.service.ShoppingCartService;
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService{
     
	@Autowired
	private ShoppingCartRepository shoppingCartRepository;
	@Autowired
	private CartProductRepository cartProductsRepository;
	@Autowired
	private CartProductService cartProductService;
	@Override
	public List<ShoppingCart> findAll() {
		return shoppingCartRepository.findAll();
	}
  
	@Override
	public ShoppingCart updateCartProducts(ShoppingCart myCart) {
	       BigDecimal myCartTotal=new BigDecimal(0);
       List<CartProduct> cartProducts=cartProductsRepository.findByShoppingCart(myCart);
       for(CartProduct cartProduct:cartProducts) {
    	   myCartTotal=myCartTotal.add(cartProduct.getSubtotal());
       }
       myCart.setGrandTotal(myCartTotal);
       shoppingCartRepository.save(myCart);
		return myCart;
	}
	@Override
	public void clearcart(ShoppingCart cart) {
		List<CartProduct>cartProductList=cartProductService.findByShoppingCart(cart);
		
		for(CartProduct cartProduct:cartProductList) {
			cartProduct.setShoppingCart(null);
			cartProductService.save(cartProduct);
		}
		cart.setGrandTotal(new BigDecimal(0));
		shoppingCartRepository.save(cart);
	}
}
