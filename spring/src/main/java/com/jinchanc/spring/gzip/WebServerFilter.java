package com.jinchanc.spring.gzip;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author zhangjin@algorix.co
 * @since 2024/12/12 18:02
 */
@Slf4j
@Component
public class WebServerFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        log.info("Filter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpRequestBodyUnzipWrapper requestWrapper = new HttpRequestBodyUnzipWrapper(request);
        // TODO response 卡死bug无法解决
        chain.doFilter(requestWrapper, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
        log.info("Filter destroy");
    }
}
