package com.reticket.reticket.config;

import com.reticket.reticket.security.RoleEnum;
import com.reticket.reticket.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
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
        http
                .csrf().disable()
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(new AntPathRequestMatcher("/api/performance/searchPerformance",
                                HttpMethod.POST.toString())).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/play/listPlays",
                                HttpMethod.GET.toString())).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/theater/listTheater",
                                HttpMethod.GET.toString())).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/appUser/api/saveGuest",
                                HttpMethod.POST.toString())).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/auditorium/list",
                                HttpMethod.GET.toString())).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/contributor/list",
                                HttpMethod.GET.toString())).permitAll()

                        .requestMatchers(new AntPathRequestMatcher("/api/appUser/update/{username}", HttpMethod.PUT.toString())).hasAnyRole(
                                RoleEnum.GUEST.name(), RoleEnum.SUPER_ADMIN.name())
                        .requestMatchers(new AntPathRequestMatcher("/api/appUser/delete/{username}", HttpMethod.DELETE.toString())).hasAnyRole(
                                RoleEnum.GUEST.name(), RoleEnum.SUPER_ADMIN.name())


                        .requestMatchers(new AntPathRequestMatcher("/api/appUser/{username}/tickets", HttpMethod.GET.toString())).hasAnyRole(
                                RoleEnum.GUEST.name(), RoleEnum.THEATRE_USER.name(), RoleEnum.THEATRE_ADMIN.name(), RoleEnum.SUPER_ADMIN.name())
                        .requestMatchers(new AntPathRequestMatcher("/api/appUser/{username}/{playId}", HttpMethod.POST.toString())).hasAnyRole(
                                RoleEnum.GUEST.name(), RoleEnum.THEATRE_USER.name(), RoleEnum.THEATRE_ADMIN.name(), RoleEnum.SUPER_ADMIN.name())
                        .requestMatchers(new AntPathRequestMatcher("/api/appUser/{username}/likedPlays", HttpMethod.GET.toString())).hasAnyRole(
                                RoleEnum.GUEST.name(), RoleEnum.THEATRE_USER.name(), RoleEnum.THEATRE_ADMIN.name(), RoleEnum.SUPER_ADMIN.name())
                        .requestMatchers(new AntPathRequestMatcher("/api/ticketAction", HttpMethod.POST.toString())).hasAnyRole(
                                RoleEnum.GUEST.name(), RoleEnum.THEATRE_USER.name(), RoleEnum.THEATRE_ADMIN.name(), RoleEnum.SUPER_ADMIN.name())

                        .requestMatchers(new AntPathRequestMatcher("/api/report", HttpMethod.POST.toString())).hasAnyRole(
                                RoleEnum.THEATRE_USER.name(), RoleEnum.THEATRE_ADMIN.name(), RoleEnum.SUPER_ADMIN.name())

                        .requestMatchers(new AntPathRequestMatcher("/api/theater/create", HttpMethod.POST.toString())).hasRole(
                                RoleEnum.SUPER_ADMIN.name())
                        .requestMatchers(new AntPathRequestMatcher("/api/theater/update/{theaterName}", HttpMethod.PUT.toString())).hasRole(
                                RoleEnum.SUPER_ADMIN.name())
                        .requestMatchers(new AntPathRequestMatcher("/api/theater/delete/{theaterName}", HttpMethod.DELETE.toString())).hasRole(
                                RoleEnum.SUPER_ADMIN.name())

                        .requestMatchers(new AntPathRequestMatcher("/api/address", HttpMethod.POST.toString())).hasAnyRole(
                                RoleEnum.THEATRE_ADMIN.name(), RoleEnum.SUPER_ADMIN.name())
                        .requestMatchers(new AntPathRequestMatcher("/api/address/update/{id}", HttpMethod.PUT.toString())).hasAnyRole(
                                RoleEnum.THEATRE_ADMIN.name(), RoleEnum.SUPER_ADMIN.name())
                        .requestMatchers(new AntPathRequestMatcher("/api/appUser/saveAssociate", HttpMethod.POST.toString())).hasAnyRole(
                                RoleEnum.THEATRE_ADMIN.name(), RoleEnum.SUPER_ADMIN.name())
                        .requestMatchers(new AntPathRequestMatcher("/api/auditorium", HttpMethod.POST.toString())).hasAnyRole(
                                RoleEnum.THEATRE_ADMIN.name(), RoleEnum.SUPER_ADMIN.name())
                        .requestMatchers(new AntPathRequestMatcher("/api/auditorium/{id}", HttpMethod.PUT.toString())).hasAnyRole(
                                RoleEnum.THEATRE_ADMIN.name(), RoleEnum.SUPER_ADMIN.name())
                        .requestMatchers(new AntPathRequestMatcher("/api/auditorium/{id}", HttpMethod.DELETE.toString())).hasAnyRole(
                                RoleEnum.THEATRE_ADMIN.name(), RoleEnum.SUPER_ADMIN.name())
                        .requestMatchers(new AntPathRequestMatcher("/api/contributor", HttpMethod.POST.toString())).hasAnyRole(
                                RoleEnum.THEATRE_ADMIN.name(), RoleEnum.SUPER_ADMIN.name())
                        .requestMatchers(new AntPathRequestMatcher("/api/contributor/update/{id}", HttpMethod.PUT.toString())).hasAnyRole(
                                RoleEnum.THEATRE_ADMIN.name(), RoleEnum.SUPER_ADMIN.name())
                        .requestMatchers(new AntPathRequestMatcher("/api/performance", HttpMethod.POST.toString())).hasAnyRole(
                                RoleEnum.THEATRE_ADMIN.name(), RoleEnum.SUPER_ADMIN.name())
                        .requestMatchers(new AntPathRequestMatcher("/api/play", HttpMethod.POST.toString())).hasAnyRole(
                                RoleEnum.THEATRE_ADMIN.name(), RoleEnum.SUPER_ADMIN.name())
                        .requestMatchers(new AntPathRequestMatcher("/api/play/formData/{auditoriumId}", HttpMethod.GET.toString())).hasAnyRole(
                                RoleEnum.THEATRE_ADMIN.name(), RoleEnum.SUPER_ADMIN.name())
                        .requestMatchers(new AntPathRequestMatcher("/api/perforamnce/updatePerformance/{id}", HttpMethod.POST.toString())).hasAnyRole(
                                RoleEnum.THEATRE_ADMIN.name(), RoleEnum.SUPER_ADMIN.name())
                        .requestMatchers(new AntPathRequestMatcher("/api/play/updatePlay/{id}", HttpMethod.PUT.toString())).hasAnyRole(
                                RoleEnum.THEATRE_ADMIN.name(), RoleEnum.SUPER_ADMIN.name())
                        .requestMatchers(new AntPathRequestMatcher("/api/play/{id}", HttpMethod.DELETE.toString())).hasAnyRole(
                                RoleEnum.THEATRE_ADMIN.name(), RoleEnum.SUPER_ADMIN.name())



                        .anyRequest().authenticated())
                .httpBasic();
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
