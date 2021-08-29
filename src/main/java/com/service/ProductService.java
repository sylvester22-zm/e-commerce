package com.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.domain.Product;
import com.domain.User;

public interface ProductService {
	
	  List<Product>findAll();
	 
  Product edit(Long product); void delete(Long product);
	  
	 Product saveProduct(Product product,MultipartFile file); //this is just for
		/*
		 * develping purposes it must be removed
		 */	 
	  List<Product> findByProduct(String product);
	  
	  
	 
	Product findOne(Long id);


}
