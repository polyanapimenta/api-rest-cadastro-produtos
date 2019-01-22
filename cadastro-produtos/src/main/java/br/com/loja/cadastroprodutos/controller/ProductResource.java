package br.com.loja.cadastroprodutos.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.loja.cadastroprodutos.model.Products;
import br.com.loja.cadastroprodutos.repository.ProductRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/products", produces = APPLICATION_JSON_VALUE)
@Api(description = "Gerenciamento de produtos")
public class ProductResource {

	@Autowired
	private ProductRepository repository;

	@PostMapping
	@ApiOperation(value = "Efetua o cadastro de um produto")
	@ApiImplicitParams({ 
		@ApiImplicitParam(
				name = "product", 
				value = "Estrutura para criação de produto.", 
				dataType = "Cadastrar Produto", required = true
				)})
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Produto Criado com Sucesso!")})
	public Products addProduct(@Valid @RequestBody Products product) {
		return repository.save(product);
	}

	@GetMapping
	@ApiOperation(value = "Lista os porodutos cadastrados")
	public ResponseEntity<Iterable<Products>> listAllProducts() {
		return ResponseEntity.ok(repository.findAll());
	}

	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Retorna o poroduto com o 'id' informado")
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "id", 
					value = "Identificador do produto", 
					dataType = "Long", required = true) 
			})
	@ApiResponses(value = {@ApiResponse(code = 404, message = "Produto Não Encontrado!")})
	public ResponseEntity<Products> searchById(@Valid @PathVariable Long id) {
		Products products = repository.findById(id).orElse(null);

		if (products != null) {
			return ResponseEntity.ok(products);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping(value = "/{id}")
	@ApiOperation(value = "Altera o estoque do produto com o 'id' informado", notes = "Aumenta ou diminui a quantidade de estoque de um produto.")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "id", 
				value = "Identificador do produto", 
				dataType = "Long", required = true),
		@ApiImplicitParam(
				name = "quantity", 
				value = "Quantidade disponível em estoque", 
				dataType = "int", required = true)
		})
	@ApiResponses(value = {@ApiResponse(code = 404, message = "Produto Não Encontrado!")})
	public ResponseEntity<Products> updateProduct(@PathVariable Long id, @Valid @RequestParam int quantity) {
		Products products = repository.findById(id).orElse(null);

		if (products != null) {
			products.setQuantity(quantity);
			products = repository.save(products);
			return ResponseEntity.ok(products);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping(value = "/{id}")
	@ApiOperation(value = "Deleta o produto com o 'id' informado", notes = "O produto deixara de existir permanentemente.")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "id", 
				value = "Identificador do produto", 
				dataType = "Long", required = true)
		})
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "Produto Excluído com Sucesso!"),
			@ApiResponse(code = 404, message = "Produto Não Encontrado!")})
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
		Products products = repository.findById(id).orElse(null);

		if (products != null) {
			repository.deleteById(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
