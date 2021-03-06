package com.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="user_order")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Date orderDate;
	private Date shippingDate;
	private String shippingMethod;
	private String orderStatus;
	private BigDecimal orderTotal;
	
	@OneToMany(mappedBy="order",cascade=CascadeType.ALL)
	List<CartProduct> cartProductList;
	@OneToOne
	private User user;
	
	@OneToOne(cascade=CascadeType.ALL)
	private UserPayment payment;
	@OneToOne(cascade=CascadeType.ALL)
	private UserShipping shipping;
	
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public Date getShippingDate() {
		return shippingDate;
	}
	public void setShippingDate(Date shippingDate) {
		this.shippingDate = shippingDate;
	}
	public String getShippingMethod() {
		return shippingMethod;
	}
	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public BigDecimal getOrderTotal() {
		return orderTotal;
	}
	public void setOrderTotal(BigDecimal orderTotal) {
		this.orderTotal = orderTotal;
	}
	public List<CartProduct> getCartProductList() {
		return cartProductList;
	}
	public void setCartProductList(List<CartProduct> cartProductList) {
		this.cartProductList = cartProductList;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public UserPayment getPayment() {
		return payment;
	}
	public void setPayment(UserPayment payment) {
		this.payment = payment;
	}
	public UserShipping getShipping() {
		return shipping;
	}
	public void setShipping(UserShipping shipping) {
		this.shipping = shipping;
	}
	
	
	
	
}
