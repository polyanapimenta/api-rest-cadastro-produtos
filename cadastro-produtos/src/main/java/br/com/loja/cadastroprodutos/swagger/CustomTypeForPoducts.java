package br.com.loja.cadastroprodutos.swagger;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Cadastrar Produto")
public class CustomTypeForPoducts {
	
	@ApiModelProperty(notes = "Título")
	private String title;
	@ApiModelProperty(notes = "Preço")
	private double price;
	@ApiModelProperty(notes = "Quantidade")
	private int quantity;
	@ApiModelProperty(notes = "Descrição")
	private String description;
	
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
}
