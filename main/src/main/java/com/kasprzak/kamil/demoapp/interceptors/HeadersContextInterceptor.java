package com.kasprzak.kamil.demoapp.interceptors;

import com.kasprzak.kamil.demoapp.common.context.RequestHeadersContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class HeadersContextInterceptor implements HandlerInterceptor {

    private final RequestHeadersContext headersContext;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) {
        Collections.list(request.getHeaderNames())
                .forEach(name ->
                        headersContext.put(name, request.getHeader(name))
                );

        return true;
    }
}
