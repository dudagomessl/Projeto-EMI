//package ensino.emi.api.infra.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
/// ** CORS global. As origens permitidas vêm da propriedade app.cors.origens. */
//@Configuration
//public class CorsConfig implements WebMvcConfigurer {
//
//    private final String[] origens;
//
//    public CorsConfig(@Value("${app.cors.origens}") String[] origens) {
//        this.origens = origens;
//    }
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins(origens)
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                .allowedHeaders("*");
//    }
//}
