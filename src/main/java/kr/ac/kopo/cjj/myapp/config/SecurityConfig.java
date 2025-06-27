package kr.ac.kopo.cjj.myapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;  // ← 추가
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder encoder) {
        UserDetails user = User.builder()
                .username("user1")
                .password(encoder.encode("pass123"))
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(encoder.encode("admin123"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // CSRF 비활성화 시 GET/POST 모두 즉시 로그아웃
                .authorizeHttpRequests(auth -> auth
                        // 로그인·정적 리소스는 모두 공개
                        .requestMatchers("/login", "/css/**", "/js/**").permitAll()
                        // 채팅 페이지와 WS 핸드셰이크는 인증된 사용자만
                        .requestMatchers("/chat", "/ws/**").authenticated()
                        // 그 외도 모두 인증 필요
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/home", true)
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")                  // 기본 /logout 엔드포인트 사용
                        .logoutSuccessUrl("/login?logout")     // 로그아웃 후 리다이렉트
                        .invalidateHttpSession(true)           // 세션 무효화
                        .deleteCookies("JSESSIONID")           // 쿠키 삭제
                        .permitAll()                           // 누구나 접근 허용
                );

        return http.build();
    }

}
