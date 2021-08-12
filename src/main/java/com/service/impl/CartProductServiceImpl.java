package com.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Repository.CartProductRepository;
import com.Repository.ProductToCartRepository;
import com.domain.CartProduct;
import com.domain.Order;
import com.domain.Product;
import com.domain.ProductToCart;
import com.domain.ShoppingCart;
import com.domain.User;
import com.service.CartProductService;

@Service
public class CartProductServiceImpl implements CartProductService {

	@Autowired
	CartProductRepository cartproductrepository;
	@Autowired
	private ProductToCartRepository producttoCartRepository;

	public CartProduct addtoCart(Product product, User user, int quantity) {
		List<CartProduct> cartProductList = findByShoppingCart(user.getShoppingcart());
		for (CartProduct cartProduct : cartProductList) {
			if (product.getId() == cartProduct.getProduct().getId()) {
				cartProduct.setSubtotal(new BigDecimal(product.getCurrentprice()).multiply(new BigDecimal(quantity)));
				cartProduct.setQuantity((cartProduct.getQuantity() + quantity));
				cartproductrepository.save(cartProduct);
				return cartProduct;
			}
		}
		CartProduct cartProduct = new CartProduct();
		cartProduct.setShoppingCart(user.getShoppingcart());
		cartProduct.setSubtotal(new BigDecimal(product.getCurrentprice()).multiply(new BigDecimal(quantity)));
		cartProduct.setQuantity(quantity);
		cartProduct.setProduct(product);
		// cartProduct.setId(product.getId());
		cartProduct = cartproductrepository.save(cartProduct);
		//////
		ProductToCart producttoCart = new ProductToCart();

		producttoCart.setCartProduct(cartProduct);
		producttoCart.setProduct(product);
		producttoCartRepository.save(producttoCart);
		return cartProduct;
	}

	public List<CartProduct> findByShoppingCart(ShoppingCart shoppingCart) {
		return cartproductrepository.findByShoppingCart(shoppingCart);
	}

	public void deleteCartProduct(CartProduct product) {
		producttoCartRepository.deleteByCartProduct(product);
		cartproductrepository.delete(product);
	}

	public CartProduct findById(Long id) {
		return cartproductrepository.getById(id);
	}

	@Override
	public CartProduct updateCartProduct(CartProduct cartProduct) {
		System.out.println("quantity from the method"+cartProduct.getQuantity());
		BigDecimal bigDecimal = new BigDecimal(cartProduct.getProduct().getCurrentprice())
				.multiply(new BigDecimal(cartProduct.getQuantity()));
		bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
		cartProduct.setSubtotal(bigDecimal);
		return cartproductrepository.save(cartProduct);
	}

	@Override
	public CartProduct save(CartProduct cartProduct) {
		
		return cartproductrepository.save(cartProduct);
		
	}
	@Override
	public  List<CartProduct> findByOrder(Order order) {
		
		return cartproductrepository.findAll();
	}

}
