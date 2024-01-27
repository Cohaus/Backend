package gdsc.sc.bsafe.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import static io.swagger.v3.oas.models.security.SecurityScheme.In.HEADER;

@Configuration
public class SwaggerConfig {

    private static final String KEY = "Access Token (Bearer)";

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Cohaus")
                .version("v1.0")
                .description("API 명세서입니다.");

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList(KEY);

        SecurityScheme securityScheme = new SecurityScheme()
                .name(HttpHeaders.AUTHORIZATION)
                .type(SecurityScheme.Type.HTTP)
                .in(HEADER)
                .bearerFormat("JWT")
                .scheme("Bearer");

        Components components = new Components().addSecuritySchemes(KEY, securityScheme);

        return new OpenAPI()
                .info(info)
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}
