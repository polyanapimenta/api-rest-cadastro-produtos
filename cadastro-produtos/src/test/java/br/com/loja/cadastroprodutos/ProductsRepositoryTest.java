package br.com.loja.cadastroprodutos;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolationException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.loja.cadastroprodutos.model.Products;
import br.com.loja.cadastroprodutos.repository.ProductRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductsRepositoryTest {
	@Autowired
	private  ProductRepository repository;
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	private String title = "GTA XXIV";
	private double price = 28.96;
	private int quantity = 8;
	private String description = "Ut sapien lorem, tempus a dignissim id, sodales ac turpis.";
	private Products product = new Products(title, price, quantity, description);
	
	@Test
	public void createNewProduct() {
		this.repository.save(product);
		assertThat(product.getId()).isNotNull();
		assertThat(product.getTitle()).isEqualTo("GTA XXIV");
		assertThat(product.getPrice()).isEqualTo(28.96);
		assertThat(product.getQuantity()).isEqualTo(8);
		assertThat(product.getDescription()).isEqualTo("Ut sapien lorem, tempus a dignissim id, sodales ac turpis.");
	}
	
	@Test
	public void updateProduct() {
		product.setQuantity(99);
		this.repository.save(product);
		assertThat(product.getTitle()).isEqualTo("GTA XXIV");
		assertThat(product.getPrice()).isEqualTo(28.96);
		assertThat(product.getQuantity()).isEqualTo(99);
	}
	
	@Test
	public void deleteProduct() {
		this.repository.delete(product);
		assertThat(repository.findById(product.getId())).isNotPresent();
	}
	
	@Test
	public void createProductWithoutTitle() {
		Products noTitle = new Products(null, price, quantity, description);
		thrown.expect(ConstraintViolationException.class);
		thrown.expectMessage("Título não deve estar em branco.");
		this.repository.save(noTitle);
	}
	
	@Test
	public void createProductWithSmallTitle() {
		Products smallTitle = new Products("ABC", price, quantity, description);
		thrown.expect(ConstraintViolationException.class);
		thrown.expectMessage("O título deve ter entre 5 a 200 caracteres.");
		this.repository.save(smallTitle);
	}
	
	@Test
	public void createProductWithSmallDescription() {
		Products smallDescription = new Products(title, price, quantity, "Lorem Ipsum 123");
		thrown.expect(ConstraintViolationException.class);
		thrown.expectMessage("A descrição deve ter entre 20 a 1000 caracteres.");
		this.repository.save(smallDescription);
	}
	
	@Test
	public void createProductWithoutDescription() {
		Products noDescription = new Products(title, price, quantity, null);
		thrown.expect(ConstraintViolationException.class);
		thrown.expectMessage("Descrição não deve estar em branco.");
		this.repository.save(noDescription);
	}
	
	@Test
	public void createProductWithZeroPrice() {
		Products zeroPrice = new Products(title, 0, quantity, description);
		thrown.expect(ConstraintViolationException.class);
		thrown.expectMessage("Verifique se o preço é maior que zero.");
		this.repository.save(zeroPrice);
	}
	
	@Test
	public void createProductWithZeroQuantity() {
		Products zeroQuantity = new Products(title, price, 0, description);
		thrown.expect(ConstraintViolationException.class);
		thrown.expectMessage("Verifique se a quantidade é maior que zero.");
		this.repository.save(zeroQuantity);
	}
	
	@Test
	public void createProductWithoutInformation() {
		Products noInformation = new Products(null, 0, 0, null);
		thrown.expect(ConstraintViolationException.class);
		this.repository.save(noInformation);
	}
	
}
