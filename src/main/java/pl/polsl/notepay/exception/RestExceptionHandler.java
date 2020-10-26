package pl.polsl.notepay.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler
        extends ResponseEntityExceptionHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String buildErrorResponse(HttpStatus status, WebRequest request, RuntimeException ex) {
        return objectMapper.createObjectNode()
                .put("timestamp", LocalDateTime.now().toString())
                .put("status", status.value())
                .put("error", status.getReasonPhrase())
                .put("message", ex.getMessage())
                .put("path", request.getContextPath())
                .toString();
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }

    @ExceptionHandler(value
            = { IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity<Object> handleConflict(
            RuntimeException ex, WebRequest request) {

        return handleExceptionInternal(ex,
                buildErrorResponse(HttpStatus.CONFLICT, request, ex),
                buildHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = {NotAuthenticatedException.class})
    protected ResponseEntity<Object> handleNotAuthenticated(RuntimeException ex, WebRequest request) {

        return handleExceptionInternal(ex,
                buildErrorResponse(HttpStatus.UNAUTHORIZED, request, ex),
                buildHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(value = {NotAuthorizedActionException.class})
    protected ResponseEntity<Object> handleNotAuthorized(RuntimeException ex, WebRequest request) {

        return handleExceptionInternal(ex,
                buildErrorResponse(HttpStatus.FORBIDDEN, request, ex),
                buildHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(value = {WrongRequestException.class})
    protected ResponseEntity<Object> handleWrongRequest(RuntimeException ex, WebRequest request) {

        return handleExceptionInternal(ex,
                buildErrorResponse(HttpStatus.BAD_REQUEST, request, ex),
                buildHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    protected ResponseEntity<Object> handleNotFound(RuntimeException ex, WebRequest request) {

        return handleExceptionInternal(ex,
                buildErrorResponse(HttpStatus.NOT_FOUND, request, ex),
                buildHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
