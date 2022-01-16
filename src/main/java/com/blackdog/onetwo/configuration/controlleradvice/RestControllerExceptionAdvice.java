package com.blackdog.onetwo.configuration.controlleradvice;

import com.blackdog.onetwo.configuration.exception.VerifyException;
import com.blackdog.onetwo.configuration.response.error.ErrorCode;
import com.blackdog.onetwo.configuration.response.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;

@Order(0)
@RestControllerAdvice
@Slf4j
public class RestControllerExceptionAdvice {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.initDirectFieldAccess();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResponse handleVerifyException(VerifyException e, HttpServletRequest req) {
        log.error("===================== VerifyException Handling =====================");
        e.printStackTrace();
        return ErrorResponse.of(e.getErrorCode());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResponse handleBindException(BindException e, HttpServletRequest req) {
        log.error("===================== BindException Handling =====================");
        e.printStackTrace();
        return ErrorResponse.of(ErrorCode.BAD_REQUEST);
    }

    private ErrorResponse getErrorResponseByBindingResult(BindingResult bindingResult, ErrorCode errorCode, String defaultMessage) {
        List<String> errorMessages = Optional.ofNullable(bindingResult.getAllErrors()).orElse(Collections.emptyList())
                .stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList());
        return ErrorResponse.of(errorCode.getCode(), (errorMessages.isEmpty() ? defaultMessage : errorMessages.get(0)), errorMessages);
    }

}
