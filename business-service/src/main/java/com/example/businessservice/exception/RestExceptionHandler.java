package com.example.businessservice.exception;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Validación fallida");
        body.put("detalles", ex.getBindingResult().getFieldErrors().stream()
                .map(f -> Map.of("campo", f.getField(), "mensaje", f.getDefaultMessage()))
                .toList());
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(ProductoNoEncontradoException.class)
    public ResponseEntity<?> notFound(ProductoNoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(MicroserviceCommunicationException.class)
    public ResponseEntity<?> microservice(MicroserviceCommunicationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<?> feign(FeignException ex) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(Map.of(
                "error", "Fallo al comunicar con data-service",
                "status", ex.status()
        ));
    }
}
