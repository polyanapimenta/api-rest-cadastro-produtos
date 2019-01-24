package br.com.loja.cadastroprodutos.exception;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javassist.NotFoundException;

@ControllerAdvice
public class RestExceptionHandler {
	
	@ExceptionHandler(value = { NotFoundException.class })
	public ResponseEntity<?> handleNotFoundException(NotFoundException ex) {
		ErrorMessage errorMessage = new ErrorMessage(
				HttpStatus.NOT_FOUND.value(),
				"Produto não encontrado!",
				ex.getMessage(),
				ex.getClass().getName(),
				new Date());
		return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = { MethodArgumentNotValidException.class })
	public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
	
		List<FieldError> fieldErros = ex.getBindingResult().getFieldErrors();
		
		String fields = fieldErros.stream().map(FieldError::getField).collect(Collectors.joining(","));
		String fieldMessages = fieldErros.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));
		
		ValidationErrorMessage errorMessage = new ValidationErrorMessage(
				HttpStatus.BAD_REQUEST.value(),
				"Erro em campo obrigatório",
				fields,
				fieldMessages,
				ex.getClass().getName(),
				new Date());
		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = { MissingServletRequestParameterException.class })
	public ResponseEntity<?> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
		ErrorMessage errorMessage = new ErrorMessage(
				HttpStatus.BAD_REQUEST.value(),
				"Parâmetro 'quantity' não foi informado",
				ex.getMessage(),
				ex.getClass().getName(),
				new Date());
		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = { HttpMessageNotReadableException.class })
	public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
		ErrorMessage errorMessage = new ErrorMessage(
				HttpStatus.BAD_REQUEST.value(),
				"Formato JSON incorreto",
				ex.getMessage(),
				ex.getClass().getName(),
				new Date());
		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = { MethodArgumentTypeMismatchException.class })
	public ResponseEntity<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
		ErrorMessage errorMessage = new ErrorMessage(
				HttpStatus.BAD_REQUEST.value(),
				"Campo com formato incorreto",
				ex.getMessage(),
				ex.getClass().getName(),
				new Date());
		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = { TransactionSystemException.class })
	public ResponseEntity<?> handleTransactionSystemException(TransactionSystemException ex) {
		ErrorMessage errorMessage = new ErrorMessage(
				HttpStatus.INTERNAL_SERVER_ERROR.value(),
				"Erro ao atualizar produto",
				ex.getMessage(),
				ex.getClass().getName(),
				new Date());
		return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = { HttpRequestMethodNotSupportedException.class })
	public ResponseEntity<?> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
		ErrorMessage errorMessage = new ErrorMessage(
				HttpStatus.METHOD_NOT_ALLOWED.value(),
				"URI inválido",
				ex.getMessage(),
				ex.getClass().getName(),
				new Date());
		return new ResponseEntity<>(errorMessage, HttpStatus.METHOD_NOT_ALLOWED);
	}
}
