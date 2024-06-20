package systems.monitor.manager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// This is a configuration class.
// It is used to configure CORS (Cross-Origin Resource Sharing) settings for the application.
// @author Antonio Scardace
// @version 1.0

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Adds CORS mapping for all paths.
    // Allows requests from the frontend origin.
    // Allows just specific HTTP methods.
    // Allows all headers.

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("https://localhost:8000")
            .allowedMethods("DELETE", "GET", "OPTIONS", "POST")
            .allowedHeaders("*");
    }
}