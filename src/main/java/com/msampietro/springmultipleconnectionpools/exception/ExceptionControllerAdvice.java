package com.msampietro.springmultipleconnectionpools.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@ControllerAdvice
@Log4j2
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {

    private final ObjectMapper objectMapper;
    public static final String LOG_ERROR_MESSAGE_TEMPLATE = "({}) - exception: {}";

    @Autowired
    public ExceptionControllerAdvice(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        HttpServletRequest req = ((HttpServletRequest) ((ServletWebRequest) request).getNativeRequest());
        List<FieldError> errorList = ex.getBindingResult().getFieldErrors();
        var sb = new StringBuilder();
        var i = 0;
        for (FieldError fieldError : errorList) {
            sb.append(fieldError.getField());
            sb.append(":");
            sb.append(" ");
            sb.append(fieldError.getDefaultMessage());
            if (i + 1 != errorList.size())
                sb.append(", ");
            i++;
        }
        var message = sb.toString();
        log.debug(LOG_ERROR_MESSAGE_TEMPLATE, ex.getClass(), ex.getMessage());
        return buildErrorResponse(ObjectNotValidException.class.getSimpleName(), message, status, req.getRequestURI());
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<Object> handleObjectNotFoundException(HttpServletRequest request, ObjectNotFoundException ex) {
        return buildGenericErrorResponse(request, ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ObjectNotValidException.class)
    public ResponseEntity<Object> handleObjectNotValidException(HttpServletRequest request, ObjectNotValidException ex) {
        return buildGenericErrorResponse(request, ex, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> buildGenericErrorResponse(HttpServletRequest request, Exception ex, HttpStatus httpStatus) {
        String requestURI = Optional.ofNullable(request).map(HttpServletRequest::getRequestURI).orElse("Unresolved Request URI");
        log.debug(LOG_ERROR_MESSAGE_TEMPLATE, ex.getClass(), ex.getMessage());
        return buildErrorResponse(ex.getClass().getSimpleName(), ex.getMessage(), httpStatus, requestURI);
    }

    private ResponseEntity<Object> buildErrorResponse(String type, String message, HttpStatus httpStatus, String requestUri) {
        var apiError = new ApiError(httpStatus.value(), message, processExceptionSimpleName(type), requestUri);
        var apiErrorString = resolveApiErrorString(apiError);
        return new ResponseEntity<>(apiErrorString, httpStatus);
    }

    private String processExceptionSimpleName(String exceptionSimpleName) {
        return StringUtils.remove(exceptionSimpleName, "Exception");
    }

    private String resolveApiErrorString(ApiError apiError) {
        try {
            return objectMapper.writeValueAsString(apiError);
        } catch (JsonProcessingException e) {
            log.debug("Error parsing ApiError to String {}", e.getMessage());
            return "";
        }
    }

}
