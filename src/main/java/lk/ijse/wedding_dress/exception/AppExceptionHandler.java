package lk.ijse.wedding_dress.exception;

import lk.ijse.wedding_dress.constatns.CommonResponse;
import lk.ijse.wedding_dress.constatns.ResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.access.AccessDeniedException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AppExceptionHandler {

    // Handle Validation Exceptions
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse> handleValidationException(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        new CommonResponse(
                                ResponseCode.OPERATION_FAILED,
                                errors,
                                "Validation failed"
                        )
                );
    }


    // Handle Access Denied
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CommonResponse> handleAccessDeniedException(
            AccessDeniedException ex) {

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(
                        new CommonResponse(
                                ResponseCode.OPERATION_FAILED,
                                null,
                                "Access Denied"
                        )
                );
    }


    // Handle RuntimeException
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CommonResponse> handleRuntimeException(
            RuntimeException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        new CommonResponse(
                                ResponseCode.OPERATION_FAILED,
                                null,
                                ex.getMessage()
                        )
                );
    }


    // Handle unexpected exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse> handleServerException(
            Exception ex) {

        ex.printStackTrace();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        new CommonResponse(
                                ResponseCode.OPERATION_FAILED,
                                null,
                                "Unexpected error occurred"
                        )
                );
    }
}