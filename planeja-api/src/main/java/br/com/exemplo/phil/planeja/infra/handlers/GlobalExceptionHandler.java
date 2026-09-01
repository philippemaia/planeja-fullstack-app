package br.com.exemplo.phil.planeja.infra.handlers;

import br.com.exemplo.phil.planeja.common.exceptions.RegistroNaoEncontradoException;
import br.com.exemplo.phil.planeja.common.exceptions.ValidationException;
import br.com.exemplo.phil.planeja.common.validation.CampoInvalido;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(ValidationException ex){
        var status = HttpStatus.UNPROCESSABLE_CONTENT;
        var body = Map.of(
                "timestamp", LocalDateTime.now(),
                "status", status.value(),
                "error", ex.getMessage(),
                "camposInvalidos", ex.getCamposInvalidos()
        );

        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){

        var camposInvalidos = ex.getFieldErrors()
                .stream().map(fe ->
                        new CampoInvalido(fe.getField(), fe.getDefaultMessage())
                ).toList();

        var status = HttpStatus.UNPROCESSABLE_CONTENT;
        var body = Map.of(
                "timestamp", LocalDateTime.now(),
                "status", status.value(),
                "error", ex.getMessage(),
                "camposInvalidos", camposInvalidos
        );

        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(RegistroNaoEncontradoException.class)
    public ResponseEntity<?> handleRegistroNaoEncontradoException(RegistroNaoEncontradoException ex){
        var status = HttpStatus.NOT_FOUND;
        var body = Map.of(
                "timestamp", LocalDateTime.now(),
                "status", status.value(),
                "message", ex.getMessage()
        );

        return ResponseEntity.status(status).body(body);
    }
}
