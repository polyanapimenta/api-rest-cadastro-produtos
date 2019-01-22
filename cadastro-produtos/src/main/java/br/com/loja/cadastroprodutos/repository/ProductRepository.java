package br.com.loja.cadastroprodutos.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.loja.cadastroprodutos.model.Products;

@Repository
public interface ProductRepository extends CrudRepository<Products, Long> {

}
