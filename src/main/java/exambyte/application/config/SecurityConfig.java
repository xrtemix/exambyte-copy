package exambyte.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

   @Bean
   SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
      httpSecurity.authorizeHttpRequests(
              auth -> auth
                  .requestMatchers("/", "/login", "/css/*").permitAll()
                  .requestMatchers("/organisator/**").hasRole("ORGANISATOR")
                  .requestMatchers("/korrektor/**").hasRole("KORREKTOR")
                  .anyRequest().authenticated())

//          .formLogin(
//              form -> form.loginPage("/login")
//                  .permitAll()
//                  .defaultSuccessUrl("/secured", true))
          .oauth2Login(form -> form.loginPage("/login")
              .permitAll()
              .defaultSuccessUrl("/secured", true));
      return httpSecurity.build();
   }

}
