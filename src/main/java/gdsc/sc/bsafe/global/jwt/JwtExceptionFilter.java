package gdsc.sc.bsafe.global.jwt;

import gdsc.sc.bsafe.global.exception.ErrorResponse;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain chain
    ) throws ServletException, IOException {
        try {
            chain.doFilter(httpServletRequest, httpServletResponse);
        } catch (JwtException jwtException) {
            setErrorResponse(HttpStatus.UNAUTHORIZED, httpServletResponse, jwtException);
        } catch (UsernameNotFoundException usernameNotFoundException){
            setErrorResponse(HttpStatus.UNAUTHORIZED, httpServletResponse, usernameNotFoundException);
        }
    }

    public void setErrorResponse(
            HttpStatus status,
            HttpServletResponse httpServletResponse,
            Throwable throwable
    ) throws IOException {
        httpServletResponse.setStatus(status.value());
        httpServletResponse.setContentType("application/json; charset=UTF-8");

        ErrorResponse jwtExceptionResponse = ErrorResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .error(HttpStatus.UNAUTHORIZED.name())
                .code("TOKEN")
                .detail(throwable.getMessage())
                .build();
        jwtExceptionResponse.setDateNull();

        httpServletResponse.getWriter().write(jwtExceptionResponse.convertToJson());
    }
}