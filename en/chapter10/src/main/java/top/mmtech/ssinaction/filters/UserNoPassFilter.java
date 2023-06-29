package top.mmtech.ssinaction.filters;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class UserNoPassFilter implements Filter {

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response,
            final FilterChain chain) throws IOException, ServletException {

        final var httpRequest = (HttpServletRequest)request;
        final var httpResponse = (HttpServletResponse)response;

        final Map<String, String[]> params = httpRequest.getParameterMap();
            
        final String[] text = params.get("super");
        if(Objects.nonNull(text)){
            final String input = text[0];
            if("ivan".equals(input)){
                   chain.doFilter(request, response); 
            }
        }else{
            
            log.info("No Valid Token");
            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    
}
