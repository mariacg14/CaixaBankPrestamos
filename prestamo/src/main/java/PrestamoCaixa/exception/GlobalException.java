package PrestamoCaixa.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;
@ControllerAdvice
public class GlobalException {

	//Para validaciones manuales
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> manejarErrorNegocio(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    //Recursos no encontrados
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> manejarErrorGeneral(RuntimeException ex) {
        return ResponseEntity.notFound().build();
    }
    
    // Para DNI no valido
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> error = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            if (fieldError.getField().equals("DNI")) {
                error.put("error", fieldError.getDefaultMessage()); // "DNI no válido"
            }
        });

        return ResponseEntity.badRequest().body(error); //Status 400
    }
}

