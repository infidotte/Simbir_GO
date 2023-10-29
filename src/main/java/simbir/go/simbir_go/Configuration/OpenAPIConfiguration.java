package simbir.go.simbir_go.Configuration;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.DeleteMapping;

@Configuration
@SecurityScheme(name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer")
public class OpenAPIConfiguration {

}
