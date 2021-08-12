package com.service.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Repository.CartProductRepository;
import com.Repository.OrderRepository;
import com.domain.CartProduct;
import com.domain.Order;
import com.domain.ShoppingCart;
import com.domain.User;
import com.domain.UserPayment;
import com.domain.UserShipping;
import com.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private  CartProductRepository cartProductRepository;
    public synchronized Order createOrder(ShoppingCart shoppingCart,UserShipping shipping,UserPayment payment,User user) {
    	
    	Order order=new Order();
    	order.setPayment(payment);
    	order.setShipping(shipping);
    	order.setOrderDate(Calendar.getInstance().getTime());
    	order.setOrderStatus("Created");    	
    	List<CartProduct>cartProductList=cartProductRepository.findByShoppingCart(shoppingCart);
  	for(CartProduct cartProduct:cartProductList)
    		cartProduct.setOrder(order);
    	order.setOrderTotal(shoppingCart.getGrandTotal());
    	shipping.setOrder(order);
    	shipping.setUser(user);
    	payment.setOrder(order);
    	payment.setUser(user);
    	order.setUser(user);
    	return orderRepository.save(order);
    
    }
    public Order findByOrderId(Long id) {
    	return orderRepository.getById(id);
    }
    }
