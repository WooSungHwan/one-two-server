package com.blackdog.onetwo.configuration.security;

import com.blackdog.onetwo.configuration.response.error.ErrorCode;
import com.blackdog.onetwo.configuration.response.error.ErrorResponse;
import com.blackdog.onetwo.utils.ResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
@Slf4j
public class SecurityAccessDeniedHandler implements AccessDeniedHandler {

    @Autowired
    ObjectMapper objectMapper;

    private final ErrorResponse FORBIDDEN_RESPONSE = ErrorResponse.of(ErrorCode.RESOURCE_FORBIDDEN, LocalDateTime.now());

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        log.error("Access Denied!! " + e.getMessage());
        ResponseUtil.writeJson(httpServletResponse, objectMapper, FORBIDDEN_RESPONSE);
    }
}
