package br.com.loja.cadastroprodutos.service;

import org.springframework.data.domain.Pageable;

import br.com.loja.cadastroprodutos.model.Products;
import javassist.NotFoundException;

public interface ProductsService {
	
	public Products createProduct(Products product);
	
	public Object listAllProducts(Pageable page);
	
	public Products searchProductById(Long id) throws NotFoundException ;
	
	public Products updateProduct(Long id, Integer quantity) throws NotFoundException;
	
	public void deleteProduct(Long id) throws NotFoundException;
}
