package com.blackdog.onetwo.configuration.security;

import com.blackdog.onetwo.configuration.response.error.ErrorCode;
import com.blackdog.onetwo.configuration.response.error.ErrorResponse;
import com.blackdog.onetwo.utils.ResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
@Component
public class SecurityAuthenticationFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;
    private final SecurityUserDetailsService securityUserDetailsService;
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("==================================== Security Authentication Filter START ====================================");

        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.isBlank(authorization)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String jwtToken = jwtProvider.getJwtToken(request);
            Long seq = jwtProvider.getSeq(jwtToken);
            UserDetails userDetails = securityUserDetailsService.loadUserByUsername(String.valueOf(seq));
//            userDetailsChecker.check(userDetails);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch(Exception e) {
            // TODO : ExceptionHandlerMethodResolver 알아보기
            ResponseUtil.writeJson(response, objectMapper, ErrorResponse.of(ErrorCode.JWT_BAD_REQUEST));
            e.printStackTrace();
            return;
        }

        filterChain.doFilter(request, response);
        log.info("==================================== Security Authentication Filter END ====================================");
    }
}
