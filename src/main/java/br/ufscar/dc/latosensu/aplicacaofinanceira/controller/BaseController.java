package br.ufscar.dc.latosensu.aplicacaofinanceira.controller;

import br.ufscar.dc.latosensu.aplicacaofinanceira.exception.DefaultExceptionAttributes;
import br.ufscar.dc.latosensu.aplicacaofinanceira.exception.ExceptionAttributes;
import br.ufscar.dc.latosensu.aplicacaofinanceira.exception.NotEmptyCollectionException;
import br.ufscar.dc.latosensu.aplicacaofinanceira.exception.NotFoundException;
import br.ufscar.dc.latosensu.aplicacaofinanceira.exception.NotUniqueException;
import br.ufscar.dc.latosensu.aplicacaofinanceira.exception.ValidationException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class BaseController {
    
    @ExceptionHandler(NotEmptyCollectionException.class)
    public ResponseEntity<Map<String, Object>> handleNotEmptyCollectionException(Exception exception, HttpServletRequest request) {
        ExceptionAttributes exceptionAttributes = new DefaultExceptionAttributes();

        Map<String, Object> responseBody = exceptionAttributes.getExceptionAttributes(exception.getMessage(), exception, request, HttpStatus.UNPROCESSABLE_ENTITY);

        return new ResponseEntity<>(responseBody, HttpStatus.UNPROCESSABLE_ENTITY);
    } 
    
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFoundException(Exception exception, HttpServletRequest request) {
        ExceptionAttributes exceptionAttributes = new DefaultExceptionAttributes();

        Map<String, Object> responseBody = exceptionAttributes.getExceptionAttributes(exception.getMessage(), exception, request, HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
    }    
    
    @ExceptionHandler(NotUniqueException.class)
    public ResponseEntity<Map<String, Object>> handleCampoUniqueException(Exception exception, HttpServletRequest request) {
        ExceptionAttributes exceptionAttributes = new DefaultExceptionAttributes();

        Map<String, Object> responseBody = exceptionAttributes.getExceptionAttributes(exception.getMessage(), exception, request, HttpStatus.UNPROCESSABLE_ENTITY);

        return new ResponseEntity<>(responseBody, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(Exception exception, HttpServletRequest request) {
        ExceptionAttributes exceptionAttributes = new DefaultExceptionAttributes();

        Map<String, Object> responseBody = exceptionAttributes.getExceptionAttributes(((ValidationException) exception).getErrors(), exception, request, HttpStatus.UNPROCESSABLE_ENTITY);

        return new ResponseEntity<>(responseBody, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
