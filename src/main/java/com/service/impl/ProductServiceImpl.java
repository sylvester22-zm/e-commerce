package com.service.impl;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Repository.ProductRepository;
import com.domain.Product;
import com.domain.User;
import com.service.ProductService;

@Service
public  class ProductServiceImpl implements ProductService{

	
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	ProductService productService;
	@Override
	public List<Product> findAll() {
		// TODO Auto-generated method stub
		return (List<Product>) productRepository.findAll();
	}

	
	  public Product findOne(Long id)
	  { return productRepository.getOne(id);
	  }
	 

	@Override
	public  Product saveProduct(Product product,MultipartFile image) {
		
		try {
			product.setProductImage(Base64.getEncoder().encodeToString(image.getBytes()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return productRepository.save(product);
	}

	@Override
	public Product edit(Long product) {
		return productRepository.getById(product);
	}

	@Override
	public void delete(Long product) {
	 productRepository.deleteById(product);;
	}

   @Override
   public List<Product> findByProduct(String product) {
	   return productRepository.findByproductnameContaining(product);
   }

}
