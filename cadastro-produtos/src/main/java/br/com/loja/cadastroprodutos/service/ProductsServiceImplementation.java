package br.com.loja.cadastroprodutos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;

import br.com.loja.cadastroprodutos.model.Products;
import br.com.loja.cadastroprodutos.repository.ProductRepository;
import br.com.loja.cadastroprodutos.service.ProductsService;
import javassist.NotFoundException;

@Service
public class ProductsServiceImplementation implements ProductsService{
	@Autowired
	private ProductRepository repository;
	
	@Override
	public Products createProduct(Products product) {
		Products products = repository.save(product);
		return products;
	}

	@Override
	public Products searchProductById(Long id) throws NotFoundException{
		Products products = idExists(id);
		return products;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = MethodArgumentNotValidException.class)
	public Products updateProduct(Long id, Integer quantity) throws NotFoundException {
		Products products = idExists(id);
		products.setQuantity(quantity);
		products = repository.save(products);
		return products;
	}
	
	@Override
	public void deleteProduct(Long id) throws NotFoundException {
		idExists(id);
		repository.deleteById(id);
	}
	
	private Products idExists(Long id) throws NotFoundException  {
		Products products = repository.findById(id).orElse(null);
		if(products == null) {
			throw new NotFoundException("Não foi possível encontrar o produto com id: " + id);
		}
		return products;
	}

	@Override
	public Object listAllProducts(Pageable page) {
		return repository.findAll(page);
	}
}
