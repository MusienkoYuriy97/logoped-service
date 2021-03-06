package by.logoped.logopedservice.filter;

import by.logoped.logopedservice.exception.JwtAuthenticationException;
import by.logoped.logopedservice.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        log.info("Check authorization filter");
        String bearerToken = jwtTokenProvider.resolveToken((HttpServletRequest) request);


        if (bearerToken == null || !bearerToken.startsWith(jwtTokenProvider.getPrefix())) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = bearerToken.replace(jwtTokenProvider.getPrefix(),"");
        try {
            if (jwtTokenProvider.validateToken(token)){
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                if (authentication != null){
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }catch (JwtAuthenticationException | UsernameNotFoundException e){
            SecurityContextHolder.clearContext();
            ((HttpServletResponse)response).sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
            log.warn(e.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }
}