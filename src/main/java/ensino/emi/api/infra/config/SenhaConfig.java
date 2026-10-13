//package ensino.emi.api.infra.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
/// **
// * Só o codificador de senhas (BCrypt), da biblioteca spring-security-crypto.
// * Não ativa o Spring Security: os endpoints continuam abertos até a etapa do JWT.
// */
//@Configuration
//public class SenhaConfig {
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
