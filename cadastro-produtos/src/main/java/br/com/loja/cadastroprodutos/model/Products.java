package br.com.loja.cadastroprodutos.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@ApiModel(value = "Produto")
public class Products {

	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(notes = "Código do produto")
	private long id;

	@NotEmpty(message = "{title.not.empty}")
	@Length(min = 5, max = 200, message = "{title.length}")
	@ApiModelProperty(notes = "Título")
	private String title;

	@Min(value = 1, message = "{price.min}")
	@ApiModelProperty(notes = "Preço")
	private double price;

	@Min(value = 0, message = "{quantity.min}")
	@ApiModelProperty(notes = "Quantidade")
	private int quantity;

	@NotEmpty(message = "{description.not.empty}")
	@Length(min = 20, max = 1000, message = "{description.length}")
	@ApiModelProperty(notes = "Descrição")
	private String description;

	public Products() {
		super();
	}

	public Products(@NotEmpty(message = "{title.not.empty}") @Length(min = 5, max = 200, message = "{title.length}") String title,
					@Min(value = 1, message = "{price.min}") double price,
					@Min(value = 1, message = "{quantity.min}") int quantity,
					@NotEmpty(message = "{description.not.empty}") @Length(min = 20, max = 1000, message = "{description.length}") String description) {
		super();
		this.title = title;
		this.price = price;
		this.quantity = quantity;
		this.description = description;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Products other = (Products) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
