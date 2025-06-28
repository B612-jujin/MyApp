package kr.ac.kopo.cjj.myapp.config;

import kr.ac.kopo.cjj.myapp.custom.CustomUserDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;  // ← 추가
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder encoder) {
        // 사용자1: profilePictureUrl="/images/user1.png"
        CustomUserDetails user = new CustomUserDetails(
                "user1",
                encoder.encode("pass123"),
                List.of(new SimpleGrantedAuthority("USER")),
                "/images/user.png"
        );

        CustomUserDetails user2 = new CustomUserDetails(
                "user2",
                encoder.encode("pass123"),
                List.of(new SimpleGrantedAuthority("USER")),
                "/images/user1.png"
        );

        // 관리자: profilePictureUrl="/images/admin.png"
        CustomUserDetails admin = new CustomUserDetails(
                "admin",
                encoder.encode("admin123"),
                List.of(new SimpleGrantedAuthority("ADMIN")),
                "/images/admin.png"
        );

        return new InMemoryUserDetailsManager(user, admin, user2);
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // CSRF 비활성화 시 GET/POST 모두 즉시 로그아웃
                .authorizeHttpRequests(auth -> auth
                        // 로그인·정적 리소스는 모두 공개
                        .requestMatchers("/login", "/css/**", "/js/**", "/images**").permitAll()
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
