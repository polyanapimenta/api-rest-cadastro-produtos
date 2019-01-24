package br.com.loja.cadastroprodutos.exception;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javassist.NotFoundException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler{
	
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
	
	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
	
		List<FieldError> fieldErros = ex.getBindingResult().getFieldErrors();
		String fieldMessages = fieldErros.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));
		
		ErrorMessage errorMessage = new ErrorMessage(
				HttpStatus.BAD_REQUEST.value(),
				"Erro em campo obrigatório",
				fieldMessages,
				ex.getClass().getName(),
				new Date());
		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}
	
	@Override
	public ResponseEntity<Object> handleMissingServletRequestParameter(
			MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		ErrorMessage errorMessage = new ErrorMessage(
				HttpStatus.BAD_REQUEST.value(),
				"Parâmetro 'quantity' não foi informado",
				ex.getMessage(),
				ex.getClass().getName(),
				new Date());
		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(
			Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

		ErrorMessage errorMessage = new ErrorMessage(
				status.value(),
				status.getReasonPhrase(), 
				ex.getMessage(),
				ex.getClass().getName(),
				new Date());
		
		return new ResponseEntity<>(errorMessage, headers, status);
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
}
