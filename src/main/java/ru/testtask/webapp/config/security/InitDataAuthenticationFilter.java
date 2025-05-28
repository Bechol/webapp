package ru.testtask.webapp.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.testtask.webapp.service.InitDataValidatorService;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
public class InitDataAuthenticationFilter extends OncePerRequestFilter {

    private final InitDataValidatorService validatorService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (Arrays.asList("/", "/index").contains(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }
        String initData = request.getParameter("initData");
        if (initData == null || initData.isEmpty()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing initData");
            return;
        }
        if (!validatorService.validate(initData)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid initData");
            return;
        }
        Authentication auth = new InitDataAuthenticationToken(initData);
        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);
    }
}
