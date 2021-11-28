package com.naver.financial.demo.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.naver.financial.demo.dto.ErrorDto;
import com.naver.financial.demo.dto.ResponseDto;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RequestFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String header = getHeader(request);
        if (header == null) {
            response.setContentType("application/json");
            response.getWriter()
                    .write(
                            convertObjectToJson(
                                    new ResponseDto<>(false, null,
                                            new ErrorDto(403, "X-USER-ID 헤더값이 없습니다."))
                            )
                    );
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String getHeader(HttpServletRequest request) {
        String header = request.getHeader("X-USER-ID");
        if (!StringUtils.hasText(header)) {
            return null;
        }
        return header;
    }

    private String convertObjectToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}
