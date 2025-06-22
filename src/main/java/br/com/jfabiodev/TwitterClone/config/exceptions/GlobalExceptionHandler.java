package br.com.jfabiodev.TwitterClone.config.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handle404Error(EntityNotFoundException exception){
        var error = new ErrorResponse(
                404,
                "Resource not found",
                exception.getMessage()
        );
        return ResponseEntity.status(404).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handle400error(MethodArgumentNotValidException exception){
        var errors = exception.getFieldErrors();
        return ResponseEntity.badRequest().body(errors.stream().map(ListErrors::new).toList());
    }

    public record ListErrors(String campo, String mensagem){
        public ListErrors(FieldError error){
            this(error.getField(), error.getDefaultMessage());
        }
    }
    public record ErrorResponse(int status, String error, String message){}
}
