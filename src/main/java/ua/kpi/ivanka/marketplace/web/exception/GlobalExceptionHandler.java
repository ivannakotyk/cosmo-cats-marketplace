package ua.kpi.ivanka.marketplace.web.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ua.kpi.ivanka.marketplace.client.RatesClientException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFound(ResourceNotFoundException ex,
                                                               HttpServletRequest request) {
        return buildError(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleValidation(MethodArgumentNotValidException ex,
                                                         HttpServletRequest request) {
        String fieldError = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> String.format("Field '%s' %s", err.getField(), err.getDefaultMessage()))
                .findFirst()
                .orElse("Invalid input");
        String message = String.format("Validation failed for object 'ProductDTO': %s.", fieldError);
        return buildError(HttpStatus.BAD_REQUEST, "Bad Request", message, request.getRequestURI());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDetails> handleConstraintViolation(ConstraintViolationException ex,
                                                                  HttpServletRequest request) {
        return buildError(HttpStatus.BAD_REQUEST, "Bad Request", ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(RatesClientException.class)
    public ResponseEntity<ErrorDetails> handleRatesClientError(RatesClientException ex,
                                                               HttpServletRequest request) {
        return buildError(HttpStatus.BAD_GATEWAY, "Bad Gateway", ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGeneric(Exception ex,
                                                      HttpServletRequest request) {
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error",
                ex.getMessage(), request.getRequestURI());
    }

    private ResponseEntity<ErrorDetails> buildError(HttpStatus status, String error, String message, String path) {
        ErrorDetails body = new ErrorDetails(status.value(), error, message, path);
        return ResponseEntity.status(status).body(body);
    }
}
