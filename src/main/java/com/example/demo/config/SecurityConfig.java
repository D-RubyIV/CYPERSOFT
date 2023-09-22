package com.example.demo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig{

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, HandlerMappingIntrospector handlerMappingIntrospector) throws Exception{
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.cors(AbstractHttpConfigurer::disable);
//        httpSecurity.sessionManagement(ss -> ss.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(handlerMappingIntrospector);
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authorize -> authorize
                                .requestMatchers(mvcMatcherBuilder.pattern("api/v1/home/admin")).hasRole("ADMIN")
                                .requestMatchers(mvcMatcherBuilder.pattern("api/v1/home/normal")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("api/v1/auth/*")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/css/**")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/js/**")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/images/**")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("https://fonts.googleapis.com/**")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/fonts.googleapis.com/**")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/")).permitAll()
//                                .anyRequest().permitAll()
                                .anyRequest().authenticated()
                );
        httpSecurity.formLogin(Customizer.withDefaults());
        return httpSecurity.build();
    }

//    @Bean
//    public UserDetailsService users() {
//        UserDetails user1 = User.builder()
//                .username("user")
//                .password(passwordEncoder().encode("a123456"))
//                .roles("USER")
//                .build();
//        UserDetails user2 = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("a123456"))
//                .roles("USER", "ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user1, user2);
//    }
}
