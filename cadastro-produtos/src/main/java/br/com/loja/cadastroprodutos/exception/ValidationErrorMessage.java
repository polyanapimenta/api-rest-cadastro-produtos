package br.com.loja.cadastroprodutos.exception;

import java.util.Date;

public class ValidationErrorMessage {
	private int status;
	private String title;
	private String field;
	private String fieldMessage;
	private String developerMessage;
	private Date datetime;
	
	public ValidationErrorMessage() {
		
	}
	
	public ValidationErrorMessage(int status, String title, String field, String fieldMessage, String developerMessage,
			Date datetime) {
		this.status = status;
		this.title = title;
		this.field = field;
		this.fieldMessage = fieldMessage;
		this.developerMessage = developerMessage;
		this.datetime = datetime;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getFieldMessage() {
		return fieldMessage;
	}
	public void setFieldMessage(String fieldMessage) {
		this.fieldMessage = fieldMessage;
	}
	public String getDeveloperMessage() {
		return developerMessage;
	}
	public void setDeveloperMessage(String developerMessage) {
		this.developerMessage = developerMessage;
	}
	public Date getDatetime() {
		return datetime;
	}
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
}
