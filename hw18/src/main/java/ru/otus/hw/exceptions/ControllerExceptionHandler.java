package ru.otus.hw.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.otus.hw.dto.ErrorDto;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    ResponseEntity<ErrorDto> commonHandler(Exception e) {
        log.error("Server error: {}", e.getMessage(), e);
        return new ResponseEntity<>(new ErrorDto(e.toString()), HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
