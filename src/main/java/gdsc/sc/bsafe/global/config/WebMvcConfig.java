package gdsc.sc.bsafe.global.config;

import gdsc.sc.bsafe.global.annotation.AuthenticationUserArgumentResolver;
import gdsc.sc.bsafe.global.jwt.JwtTokenProvider;
import gdsc.sc.bsafe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthenticationUserArgumentResolver(jwtTokenProvider, userRepository));
    }
}
