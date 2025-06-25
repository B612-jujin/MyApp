package kr.ac.kopo.cjj.myapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 1) 비밀번호 암호화기를 빈으로 등록
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2) In-Memory 사용자 등록 (DB 없이 메모리에만 저장)
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

    // 3) HTTP 보안 설정 (어떤 URL을 허용/보호할지)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable) // CSRF 보호 비활성화 (API 서버 등에서는 필요할 수 있음)
                // URL 권한 설정
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login", "/css/**", "/js/**").permitAll() // 로그인 페이지와 정적 리소스는 모두 허용
                        .anyRequest().permitAll()                            // 그 외 모든 요청은 인증 필요
                )
                // 폼 로그인 설정
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")// 커스텀 로그인 페이지 경로
                        .defaultSuccessUrl("/home", true) // 로그인 성공 후 리다이렉트할 페이지
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .permitAll()                      // 로그인 페이지는 누구나 접근 가능
                )
                // 로그아웃 설정
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout") // 로그아웃 성공 후 리다이렉트
                        .logoutSuccessUrl("/login")
                );

        return http.build();
    }
}