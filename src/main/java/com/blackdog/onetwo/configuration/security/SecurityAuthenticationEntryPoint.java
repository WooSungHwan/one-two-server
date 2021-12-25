package com.blackdog.onetwo.configuration.security;

import com.blackdog.onetwo.configuration.response.error.ErrorCode;
import com.blackdog.onetwo.configuration.response.error.ErrorResponse;
import com.blackdog.onetwo.utils.ResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Component
@Slf4j
public class SecurityAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    ObjectMapper objectMapper;

    private final ErrorResponse UNAUTHORIZED_RESPONSE = ErrorResponse.of(ErrorCode.LOGIN_EXPIRED, LocalDateTime.now());

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        log.error("UNAUTHORIZED!! " + e.getMessage());
        ResponseUtil.writeJson(httpServletResponse, objectMapper, UNAUTHORIZED_RESPONSE);
    }
}
