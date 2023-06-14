package com.example.koreabank;

import java.io.IOException;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CorsFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        response.setHeader("Access-Control-Allow-Origin", request.getHeader("origin")); // 허용 URL
        response.setHeader("Access-Control-Allow-Credentials", "true"); // 자격증명
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT"); // 허용 메소드
        response.setHeader("Access-Control-Max-Age", "3600"); // 브라우저 캐시 시간
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With, content-type, enctype"); // 허용 헤더

        chain.doFilter(request, response);

    }
}
