package top.mmtech.ssinaction.filters;

import java.io.IOException;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthenticationLogFilter extends OncePerRequestFilter {

    // @Override
    // public void doFilter(final ServletRequest request, final ServletResponse response,
    //         final FilterChain chain) throws IOException, ServletException {
    //     final var httpRequest = (HttpServletRequest) request;
    //     log.info("The passed request is : {}",httpRequest.getRequestId());
    //     chain.doFilter(request, response);         
    // }

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
            final HttpServletResponse response, final FilterChain filterChain)
            throws ServletException, IOException {
        log.info("The passed request is : {}",request.getRequestId());
        filterChain.doFilter(request, response);     
    }
    
}
