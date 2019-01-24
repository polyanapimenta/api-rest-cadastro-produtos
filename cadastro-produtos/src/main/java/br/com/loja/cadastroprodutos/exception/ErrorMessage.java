package br.com.loja.cadastroprodutos.exception;

import java.util.Date;

public class ErrorMessage {
	private int status;
	private String title;
	private String message;
	private String developerMessage;
	private Date datetime;

	public ErrorMessage() {
	}

	public ErrorMessage(int status, String title, String message, String developerMessage, Date datetime) {
		this.status = status;
		this.title = title;
		this.message = message;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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
