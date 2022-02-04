package com.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.domain.CartProduct;
import com.domain.Order;
import com.domain.Product;
import com.domain.ShoppingCart;
import com.domain.User;
import com.service.CartProductService;
import com.service.OrderService;
import com.service.ProductService;
import com.service.ShoppingCartService;
import com.service.UserService;

@Controller
@RequestMapping("/cart")
public class CartController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private CartProductService cartProductService;
	
	 @Autowired
	private  ShoppingCartService shoppingCartService;
	 
	 @Autowired
	 private ProductService productService;
	 
	@RequestMapping("/add")
	public String AddTocart(@ModelAttribute("product") Product product,@ModelAttribute("quantity") String quantity,Model model,
			Principal principal) {
		System.out.println("the quantity::"+quantity);
		User user=userService.findByUsername(principal.getName());
		product=productService.findOne(product.getId());
		CartProduct cartProduct=cartProductService.addtoCart(product,user,Integer.parseInt(quantity));
		model.addAttribute("cartProduct",cartProduct);
		model.addAttribute("cartAddSuccess",true);
		return "forward:/Detail?id="+product.getId();
	}
	@RequestMapping("/myCart")
	public String Cart(Model model,Principal principal) {
		User user=userService.findByUsername(principal.getName());
		 
	    ShoppingCart myCart=user.getShoppingcart();
		List<CartProduct> productsInCart=cartProductService.findByShoppingCart(myCart);
		shoppingCartService.updateCartProducts(myCart);
		
		model.addAttribute("productsInCart",productsInCart);
		model.addAttribute("myCart", myCart);
		return "myCart";
	}

	@RequestMapping("/deletecart")
	public String delete(@RequestParam("id") Long id,Model model) {
		cartProductService.deleteCartProduct(cartProductService.findById(id));
		return "forward:/cart/myCart";
	}
	@RequestMapping("/updateQuantity")
	public String updateCartQuantity(@ModelAttribute("id") Long product,
			@ModelAttribute("quantity") int updateQuantity ) {
		System.out.println("the quantity been passed"+updateQuantity);
		CartProduct cartProduct=cartProductService.findById(product);
		cartProduct.setQuantity(updateQuantity);
		cartProductService.updateCartProduct(cartProduct);
		return "forward:/cart/myCart";
	}
}
