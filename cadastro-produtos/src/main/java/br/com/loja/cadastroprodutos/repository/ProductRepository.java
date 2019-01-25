package br.com.loja.cadastroprodutos.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.loja.cadastroprodutos.model.Products;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Products, Long> {

}
