package com.example.demo.exception;

import com.example.demo.dto.error.ErrorDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomException extends ResponseEntityExceptionHandler {
    // * Xử lí chung
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<?> handleAllException(Exception ex, WebRequest request){
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(ex.getMessage());
        errorDto.setDetails(request.getDescription(false));
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
//     * Xử lí lỗi validate
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(err->{
            String field = ((FieldError) err).getField();
            String message = err.getDefaultMessage();
            errors.put(field, message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    // * Xử lý lỗi về yêu cầu
    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<ErrorDto> handleBadRequestException(Exception ex, WebRequest request) throws Exception {
        ErrorDto errorDto = new ErrorDto(
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<ErrorDto>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static class BadRequestException extends RuntimeException {
        public BadRequestException(String message) {
            super(message);
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class NotFoundException extends RuntimeException {
        public NotFoundException(String message) {
            super(message);
        }
    }
}
