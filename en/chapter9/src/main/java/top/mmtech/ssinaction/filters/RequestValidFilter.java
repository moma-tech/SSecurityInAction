package top.mmtech.ssinaction.filters;

import java.io.IOException;
import org.springframework.util.StringUtils;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestValidFilter implements Filter {

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response,
            final FilterChain chain) throws IOException, ServletException {

        final var httpRequest = (HttpServletRequest) request;
        final var httpResponse = (HttpServletResponse)response;
        final String tokenName = "token";
        final String tokenValue = httpRequest.getHeader(tokenName);
        
        if(StringUtils.hasText(tokenValue)){
            log.info(tokenValue);
            chain.doFilter(request, response);
        }else{
            log.info("No Valid Token");
            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    
}
