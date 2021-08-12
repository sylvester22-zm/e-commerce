package com.controller;

import java.security.Principal;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.domain.CartProduct;
import com.domain.Order;
import com.domain.ShoppingCart;
import com.domain.User;
import com.domain.UserPayment;
import com.domain.UserShipping;
import com.service.CartProductService;
import com.service.OrderService;
import com.service.ShoppingCartService;
import com.service.UserService;

@Controller
public class CheckOutController {
    @Autowired
   private  OrderService orderService;
	@Autowired
	private UserService userService;
	@Autowired
	private CartProductService cartProductService;
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	
	@RequestMapping("/checkout")
	public String checkout(@RequestParam("id") Long cartId,Principal principal,Model model ){
		User user=userService.findByUsername(principal.getName());
		
		if(cartId!=user.getShoppingcart().getId()) {
			
			return "forward:/badRequest";
			
		}
		List<CartProduct>productsInCart=cartProductService.findByShoppingCart(user.getShoppingcart());
	
		if(productsInCart.isEmpty()) {
			model.addAttribute("emptyCart",true);
			return "forward:/cart/myCart";
		}
		
		if(user.getShipping()!=null||user.getPayment()!=null) {
			UserShipping shipping=user.getShipping();
			UserPayment payment=user.getPayment();
			System.out.println("the shipping"+shipping);
			model.addAttribute("shipping",shipping);
			model.addAttribute("payment", payment);
		}
	
		UserShipping shipping=new UserShipping();
		UserPayment payment=new UserPayment();
		model.addAttribute("shipping",shipping);
		model.addAttribute("payment",payment);
		model.addAttribute("productsInCart",productsInCart);
		return "checkout";

}      
	@RequestMapping(value="checkout",method=RequestMethod.POST)
	public String checkoutPost(@ModelAttribute("payment") UserPayment payment,
			@ModelAttribute("shipping") UserShipping shipping,Model model,Principal principal) {
		 ShoppingCart shoppingCart=userService.findByUsername(principal.getName()).getShoppingcart();
		 
	
		List<CartProduct>cartProductList=cartProductService.findByShoppingCart(shoppingCart);
		model.addAttribute("cartProductList",cartProductList);
		 User user=userService.findByUsername(principal.getName());
		 Order order=orderService.createOrder(shoppingCart,shipping,payment,user);
		 
		 shoppingCartService.clearcart(shoppingCart);
		model.addAttribute("order",order);
		model.addAttribute("orderSuccess",true);
		return "orderSuccess";
	} 
	
	@RequestMapping("/myOrders")
	public String myOrders(
			Principal principal,Model model) {
		
		  User user=userService.findByUsername(principal.getName());
		 // Order order=orderService.findByOrderId(cartProductId);
		 // Order order=orderService.findByOrderId(null)
		//  if(order.getUser().getId()!=user.getId()) { return "forward:/badRequest"; }
		//  List<CartProduct>cartProductList=cartProductService.findByOrder(order);
		 // model.addAttribute("cartProductList", cartProductList);
		//  model.addAttribute("user",user); model.addAttribute("order",order);
		  model.addAttribute("payment",user.getPayment());
		  model.addAttribute("shipping",user.getShipping());
		  model.addAttribute("orders",user.getOrderList());
		 
		
		return "orders";
	}
}
