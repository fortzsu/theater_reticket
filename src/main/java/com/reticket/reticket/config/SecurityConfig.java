package com.reticket.reticket.config;

import com.reticket.reticket.config.oauth2.CustomOidcUserService;
import com.reticket.reticket.config.oauth2.CustomSuccessHandler;
import com.reticket.reticket.security.AuthorityEnum;
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
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig implements WebMvcConfigurer {

    private final AppUserService appUserService;
    private final PasswordEncoder passwordEncoder;
    private final CustomSuccessHandler customSuccessHandler;
    private final CustomOidcUserService customOidcUserService;

    @Autowired
    public SecurityConfig(AppUserService appUserService, PasswordEncoder passwordEncoder,
                          CustomSuccessHandler customSuccessHandler, CustomOidcUserService customOidcUserService) {
        this.appUserService = appUserService;
        this.passwordEncoder = passwordEncoder;
        this.customSuccessHandler = customSuccessHandler;
        this.customOidcUserService = customOidcUserService;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        setUpBasicAuth(http);
        setUpOAuth2(http);
        return http.build();
    }

    private void setUpOAuth2(HttpSecurity http) throws Exception {
        http.oauth2Login()
                .authorizationEndpoint().baseUri("/api/oauth2/authorization")
                .and().userInfoEndpoint()
                .oidcUserService(customOidcUserService)
//                .and().redirectionEndpoint().baseUri("/api/play/list")
                .and().successHandler(customSuccessHandler);
    }

    private void setUpBasicAuth(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().and().httpBasic();
        setUpEndpointPermissions(http);
        http.logout()
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", "JWT-TOKEN")
                .logoutUrl("/api/logout")
                .logoutSuccessHandler(new LogoutSuccessHandlerImpl() {
                });
    }

    private void setUpEndpointPermissions(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/api/auth/me", HttpMethod.GET.toString()).hasAuthority(AuthorityEnum.FREE_LISTS_AND_SEARCH.name()) //TODO
                .requestMatchers("/api/auth/**").permitAll() //TODO

                .requestMatchers("/api/appUser/saveAssociate", HttpMethod.POST.toString()).hasAuthority(AuthorityEnum.MODIFY_IN_THEATER.name())
                .requestMatchers("/api/auditorium/list", HttpMethod.GET.toString()).permitAll()
                .requestMatchers("/api/auditorium/list", HttpMethod.GET.toString()).hasAuthority(AuthorityEnum.FREE_LISTS_AND_SEARCH.name())
                .requestMatchers("/api/contributor/list", HttpMethod.GET.toString()).permitAll()
                .requestMatchers("/api/contributor/list", HttpMethod.GET.toString()).hasAuthority(AuthorityEnum.FREE_LISTS_AND_SEARCH.name())
                .requestMatchers("/api/play/list", HttpMethod.POST.toString()).permitAll()
                .requestMatchers("/api/play/list", HttpMethod.POST.toString()).hasAuthority(AuthorityEnum.FREE_LISTS_AND_SEARCH.name())
                .requestMatchers("/api/theater/list", HttpMethod.POST.toString()).permitAll()
                .requestMatchers("/api/theater/list", HttpMethod.POST.toString()).hasAuthority(AuthorityEnum.FREE_LISTS_AND_SEARCH.name())
                .requestMatchers("/api/performance/search", HttpMethod.POST.toString()).permitAll()
                .requestMatchers("/api/performance/search", HttpMethod.POST.toString()).hasAuthority(AuthorityEnum.FREE_LISTS_AND_SEARCH.name())
                .requestMatchers("/api/appUser/saveGuest", HttpMethod.POST.toString()).permitAll()
                .requestMatchers("/api/appUser/saveGuest", HttpMethod.POST.toString()).hasRole(RoleEnum.SUPER.name())


//                .requestMatchers("/api/theater/create", HttpMethod.POST.toString()).hasRole(RoleEnum.SUPER.name())
//                .requestMatchers("/api/theater/update/{theaterName}", HttpMethod.PUT.toString()).hasRole(RoleEnum.SUPER.name())
//                .requestMatchers("/api/theater/delete/{theaterName}", HttpMethod.DELETE.toString()).hasRole(RoleEnum.SUPER.name())



//                .requestMatchers("/api/appUser/update/{username}", HttpMethod.PUT.toString()).hasAuthority(AuthorityEnum.MODIFY_APPUSER_AND_FOLLOW_ACTIONS.name())
                .requestMatchers("/api/appUser/delete/{username}", HttpMethod.DELETE.toString()).hasAuthority(AuthorityEnum.MODIFY_APPUSER_AND_FOLLOW_ACTIONS.name())
                .requestMatchers("/api/appUser/{username}/tickets", HttpMethod.GET.toString()).hasAuthority(AuthorityEnum.MODIFY_APPUSER_AND_FOLLOW_ACTIONS.name())
                .requestMatchers("/api/appUser/{username}/likedPlays", HttpMethod.GET.toString()).hasAuthority(AuthorityEnum.MODIFY_APPUSER_AND_FOLLOW_ACTIONS.name())

                .requestMatchers("/api/appUser/{username}/{playId}", HttpMethod.POST.toString()).hasAuthority(AuthorityEnum.LIKE_PLAY.name())
                .requestMatchers("/api/ticketAction", HttpMethod.POST.toString()).hasAuthority(AuthorityEnum.TICKET_ACTIONS.name())
                .requestMatchers("/api/report", HttpMethod.POST.toString()).hasAuthority(AuthorityEnum.ACCESS_REPORT.name())
                .requestMatchers("/api/address", HttpMethod.POST.toString()).hasAuthority(AuthorityEnum.MODIFY_IN_THEATER.name())
                .requestMatchers("/api/address/update/{id}", HttpMethod.PUT.toString()).hasAuthority(AuthorityEnum.MODIFY_IN_THEATER.name())
                .requestMatchers("/api/auditorium", HttpMethod.POST.toString()).hasAuthority(AuthorityEnum.MODIFY_IN_THEATER.name())
                .requestMatchers("/api/auditorium/{id}", HttpMethod.PUT.toString()).hasAuthority(AuthorityEnum.MODIFY_IN_THEATER.name())
                .requestMatchers("/api/auditorium/{id}", HttpMethod.DELETE.toString()).hasAuthority(AuthorityEnum.MODIFY_IN_THEATER.name())
                .requestMatchers("/api/contributor", HttpMethod.POST.toString()).hasAuthority(AuthorityEnum.MODIFY_IN_THEATER.name())
                .requestMatchers("/api/contributor/update/{id}", HttpMethod.PUT.toString()).hasAuthority(AuthorityEnum.MODIFY_IN_THEATER.name())
                .requestMatchers("/api/performance/save", HttpMethod.POST.toString()).hasAuthority(AuthorityEnum.MODIFY_IN_THEATER.name())
                .requestMatchers("/api/performance/updatePerformance/{id}", HttpMethod.POST.toString()).hasAuthority(AuthorityEnum.MODIFY_IN_THEATER.name())
                .requestMatchers("/api/play/save", HttpMethod.POST.toString()).hasAuthority(AuthorityEnum.MODIFY_IN_THEATER.name())
                .requestMatchers("/api/play/formData/{auditoriumId}", HttpMethod.GET.toString()).hasAuthority(AuthorityEnum.MODIFY_IN_THEATER.name())
                .requestMatchers("/api/play/updatePlay/{id}", HttpMethod.PUT.toString()).hasAuthority(AuthorityEnum.MODIFY_IN_THEATER.name())
                .requestMatchers("/api/play/{id}", HttpMethod.DELETE.toString()).hasAuthority(AuthorityEnum.MODIFY_IN_THEATER.name())
                .anyRequest().authenticated());
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
