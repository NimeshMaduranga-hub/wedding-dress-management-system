package lk.ijse.wedding_dress.exception;

import lk.ijse.wedding_dress.constatns.CommonResponse;
import lk.ijse.wedding_dress.constatns.ResponseCode;
import lk.ijse.wedding_dress.constatns.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppExceptionHandler {

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

    // Handle RuntimeException
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CommonResponse> handleRuntimeException(
            RuntimeException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        new CommonResponse(
                                ResponseCode.OPERATION_FAILED,
                                null,
                                ex.getMessage()
                        )
                );
    }
}

