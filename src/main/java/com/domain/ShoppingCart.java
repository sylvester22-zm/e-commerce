package com.domain;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ShoppingCart {
     @Id
     @GeneratedValue(strategy=GenerationType.AUTO)
     Long id;
     private BigDecimal GrandTotal;
     
     @OneToMany(mappedBy="shoppingCart", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
 	@JsonIgnore
 	private List<CartProduct> cartproductList;
     
     @OneToOne(cascade = CascadeType.ALL)
  	 private User user;
 	
 	public List<CartProduct> getCartproductList() {
		return cartproductList;
	}

	public void setCartproductList(List<CartProduct> cartroductList) {
		this.cartproductList = cartroductList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getGrandTotal() {
		return GrandTotal;
	}

	public void setGrandTotal(BigDecimal grandTotal) {
		GrandTotal = grandTotal;
	}

	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	

	
}
