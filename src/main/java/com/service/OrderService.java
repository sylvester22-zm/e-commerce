package com.service;

import com.domain.Order;
import com.domain.ShoppingCart;
import com.domain.User;
import com.domain.UserPayment;
import com.domain.UserShipping;

public interface OrderService {
   Order createOrder(ShoppingCart shoppingCart,UserShipping shipping,UserPayment payment,User user);
   Order findByOrderId(Long id);
}
