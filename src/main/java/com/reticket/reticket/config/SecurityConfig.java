package com.reticket.reticket.config;

import com.reticket.reticket.security.AuthorityEnum;
import com.reticket.reticket.security.RoleEnum;
import com.reticket.reticket.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig implements WebMvcConfigurer {

    private final AppUserService appUserService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(AppUserService appUserService, PasswordEncoder passwordEncoder) {
        this.appUserService = appUserService;
        this.passwordEncoder = passwordEncoder;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .httpBasic()
                .and()
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/api/auditorium/list", HttpMethod.GET.toString()).permitAll()
                        .requestMatchers("/api/auditorium/list", HttpMethod.GET.toString()).hasAnyRole(RoleEnum.SUPER.name(), RoleEnum.GUEST.name(),
                                RoleEnum.THEATRE_USER.name(), RoleEnum.THEATER_ADMIN.name())
                        .requestMatchers("/api/contributor/list", HttpMethod.GET.toString()).permitAll()
                        .requestMatchers("/api/contributor/list",HttpMethod.GET.toString()).hasAnyRole(RoleEnum.SUPER.name(), RoleEnum.GUEST.name(),
                                RoleEnum.THEATRE_USER.name(), RoleEnum.THEATER_ADMIN.name())
                        .requestMatchers("/api/appUser/saveGuest", HttpMethod.POST.toString()).permitAll()
                        .requestMatchers("/api/appUser/saveGuest", HttpMethod.POST.toString()).hasAnyRole(RoleEnum.SUPER.name(), RoleEnum.GUEST.name(),
                                RoleEnum.THEATRE_USER.name(), RoleEnum.THEATER_ADMIN.name())
                        .requestMatchers("/api/play/list", HttpMethod.POST.toString()).permitAll()
                        .requestMatchers("/api/play/list", HttpMethod.POST.toString()).hasAnyRole(RoleEnum.SUPER.name(), RoleEnum.GUEST.name(),
                                RoleEnum.THEATRE_USER.name(), RoleEnum.THEATER_ADMIN.name())
                        .requestMatchers("/api/theater/list", HttpMethod.POST.toString()).permitAll()
                        .requestMatchers("/api/theater/list", HttpMethod.POST.toString()).hasAnyRole(RoleEnum.SUPER.name(), RoleEnum.GUEST.name(),
                                RoleEnum.THEATRE_USER.name(), RoleEnum.THEATER_ADMIN.name())
                        .requestMatchers("/api/performance/search", HttpMethod.POST.toString()).permitAll()
                        .requestMatchers("/api/performance/search", HttpMethod.POST.toString()).hasAnyRole(RoleEnum.SUPER.name(), RoleEnum.GUEST.name(),
                                RoleEnum.THEATRE_USER.name(), RoleEnum.THEATER_ADMIN.name())




                        .anyRequest().authenticated());


        http.logout()
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", "JWT-TOKEN")
                .logoutUrl("/api/logout") //TODO
                .logoutSuccessHandler(new LogoutSuccessHandlerImpl() {
                });


        return http.build();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(appUserService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "http://localhost:4200")
                .allowedMethods("GET", "POST", "DELETE", "PUT");
    }


}
