package ua.kpi.ivanka.marketplace.web.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ua.kpi.ivanka.marketplace.client.RatesClientException;

import java.net.URI;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex,
                                          HttpServletRequest request) {

        String fieldError = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> String.format("Field '%s' %s", err.getField(), err.getDefaultMessage()))
                .findFirst()
                .orElse("Invalid input");
        String dtoName = ex.getBindingResult().getObjectName();

        String message = String.format(
                "Validation failed for object '%s': %s",
                dtoName,
                fieldError
        );
        return buildProblem(HttpStatus.BAD_REQUEST, "Bad Request", message, request.getRequestURI());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        return buildProblem(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(RatesClientException.class)
    public ProblemDetail handleRatesClientError(RatesClientException ex, HttpServletRequest request) {
        log.warn("Rates client failure at path {}: {}", request.getRequestURI(), ex.getMessage());
        String message = "The upstream rates service is currently unavailable.";
        return buildProblem(HttpStatus.BAD_GATEWAY, "Bad Gateway", message, request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneric(Exception ex, HttpServletRequest request) {
        log.error("Unhandled exception occurred at path: {}", request.getRequestURI(), ex);
        String message = "An unexpected internal server error occurred.";
        return buildProblem(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", message, request.getRequestURI());
    }

    private ProblemDetail buildProblem(HttpStatus status, String title, String detail, String path) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, detail);
        problem.setTitle(title);
        problem.setInstance(URI.create(path));
        return problem;
    }
}