package ru.netology.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.netology.exception.ConfirmOperationException;
import ru.netology.exception.InvalidCardData;
import ru.netology.exception.TransferException;
import ru.netology.dto.ExceptionResponse;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(InvalidCardData.class)
    public ResponseEntity<ExceptionResponse> icdHandler(InvalidCardData e){
        log.error(e.getMessage());
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage(),400), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ConfirmOperationException.class)
    public ResponseEntity<ExceptionResponse>coeHandler(ConfirmOperationException e){
        log.error(e.getMessage());
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage(),400),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(TransferException.class)
    public ResponseEntity<ExceptionResponse>teHandler(TransferException e){
        log.error(e.getMessage());
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage(),500),HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
