package ru.kochnev.technomant.SpringBoot.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.kochnev.technomant.SpringBoot.models.MyException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    ObjectMapper mapper = new ObjectMapper();

    @ExceptionHandler({MyException.class})
    public ResponseEntity<String> handlerMyAPIException(MyException ex) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapper.writeValueAsString(ex.getMessage()));
    }

    @Override
    public @NonNull ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {

        BindingResult bindingResult = ex.getBindingResult();
        List<String> errorsMsqList = bindingResult.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        return this.handleExceptionInternal(ex, errorsMsqList, headers, status, request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolation(ConstraintViolationException ex) {
        List<String> msg = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessageTemplate)
                .toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.toString());
    }
}
