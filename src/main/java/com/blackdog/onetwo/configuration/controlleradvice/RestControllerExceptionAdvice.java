package com.blackdog.onetwo.configuration.controlleradvice;

import com.blackdog.onetwo.configuration.response.error.ErrorCode;
import com.blackdog.onetwo.configuration.response.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@RestControllerAdvice
@Slf4j
public class RestControllerExceptionAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResponse handleRuntimeException(RuntimeException e, HttpServletRequest req) {
        log.error("===================== RuntimeException Handling =====================");
        e.printStackTrace();
        return ErrorResponse.of(ErrorCode.BAD_REQUEST, now());
    }

}
