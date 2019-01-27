package br.com.loja.cadastroprodutos;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.loja.cadastroprodutos.model.Products;
import br.com.loja.cadastroprodutos.repository.ProductRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductsEndpointTest {
	
	@Autowired
	private TestRestTemplate rest;
	@MockBean
	private ProductRepository repository;
	private Products product = new Products("FIFA 2032", 25.80, 33, "Ut sapien lorem, tempus a dignissim id, sodales ac turpis.");
	
	@Test
	public void getAllProducts() {
		List<Products> products = new ArrayList<>();
		products.add(new Products ("Jogo da XUXA", 1.99, 13, "Ut sapien lorem, tempus a dignissim id, sodales ac turpis."));
		products.add(new Products ("Counter Strike", 199.99, 10, "Ut sapien lorem, tempus a dignissim id, sodales ac turpis."));
		
		BDDMockito.when(repository.findAll()).thenReturn(products);
		ResponseEntity<String> response = rest.getForEntity("/products", String.class); 
		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
	}

	@Test
	public void getProductById() {
		repository.save(product);
		ResponseEntity<Products> response = rest.getForEntity("/products/", Products.class, repository.findById(product.getId())); 
		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
	}
	
	@Test
	public void getProductWithInvalidId() {
		long id = 99L;
		ResponseEntity<String> response = rest.getForEntity("/products/{id}", String.class, id); 
		System.out.println("getProductWithInvalidId: " + response);
		assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
	}

	@Test
	public void postProduct() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Products> request = new HttpEntity<>(product, headers);
		ResponseEntity<String> response = rest.postForEntity("/products/", request , String.class );
		assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
	}
	
	@Test
	public void postInvalidProduct() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Products zeroPrice = new Products("FIFA 2032", 0, 33, "Ut sapien lorem, tempus a dignissim id, sodales ac turpis.");
		HttpEntity<Products> request = new HttpEntity<>(zeroPrice, headers);
		ResponseEntity<String> response = rest.postForEntity("/products/", request , String.class );
		System.out.println("postInvalidProduct: " + response);
		assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
	}
	
	@Test
	public void putProductWithoutQuantity() {
		ResponseEntity<String> response = rest.exchange("/products/{id}", HttpMethod.PUT, null , String.class, product.getId());
		System.out.println("putProductWithoutQuantity: " + response);
		assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
	}

	@Test
	public void putProductWithInvalidId() {
		int quantity = 10;
		long id = 99L;
		ResponseEntity<String> response = rest.exchange("/products/{id}?quantity=", HttpMethod.PUT, null , String.class, id, quantity);
		System.out.println("putProductWithInvalidId: " + response);
		assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
	}
	
	@Test
	public void deleteProductWithInvalidId() {
		long id = 99L;
		ResponseEntity<String> response = rest.exchange("/products/{id}", HttpMethod.DELETE, null , String.class, id);
		System.out.println("deleteProductWithInvalidId: " + response);
		assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
	}	
}