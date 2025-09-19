package hieesu.vn.demospringst5.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    /** true = dùng login.html ; false = dùng trang login mặc định của Spring */
    @Value("${app.security.custom-login:false}")
    private boolean customLogin;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/swagger-ui/**","/v3/api-docs/**",
                                "/css/**","/js/**","/images/**","/i18n/**"
                        ).permitAll()
                        .requestMatchers("/", "/login").permitAll() // cho phép vào trang chủ & trang login
                        // (tuỳ) nếu muốn test API không cần login, mở dòng dưới:
                        // .requestMatchers("/api/**").permitAll()
                        .anyRequest().authenticated()
                );

        if (customLogin) {
            // Dùng trang login tùy biến
            http.formLogin(form -> form
                    .loginPage("/login")              // GET /login -> templates/login.html
                    .loginProcessingUrl("/login")     // form POST tới /login
                    .defaultSuccessUrl("/admin/categories/list", true)     // đổi sang URL có thật của bạn nếu muốn
                    .failureUrl("/login?error")
                    .permitAll()
            );
        } else {
            // Dùng trang login mặc định
            http.formLogin(form -> form.permitAll());
        }

        http.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl(customLogin ? "/login?logout" : "/")
                .permitAll()
        );

        return http.build();
    }

    /** User demo: admin / 123 */
    @Bean
    public UserDetailsService users() {
        UserDetails admin = User.withUsername("admin")
                .password("{noop}123") // {noop} = không mã hoá (demo)
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(admin);
    }
}
