package com.springSecurityTemplate.config;

import com.springSecurityTemplate.service.MyUserDetailService;
import com.springSecurityTemplate.utility.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        //daoAuthenticationProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        return daoAuthenticationProvider;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(Customizer-> Customizer
//                        .requestMatchers("/auth/register","/auth/login")
//                        .permitAll()
//                        .anyRequest().authenticated())
//                .formLogin(Customizer.withDefaults())
//                //.sessionManagement(Customizer-> Customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .httpBasic(Customizer.withDefaults());
//
//        return httpSecurity.build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        //logic for filter chain
        http
                .csrf(request->request.disable())
                .authorizeHttpRequests(request-> request
                        .requestMatchers("/auth/register","/auth/login")
                        .permitAll()
                        .anyRequest()
                        .authenticated())

                // for JWT Implementation
                //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                .sessionManagement(request
                        ->request.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.
                addFilterBefore(jwtAuthFilter,UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }




//    @Bean
//    public UserDetailsService userDetailsService(){
//
////        UserDetails userDetails1 = User
////                .withDefaultPasswordEncoder()
////                .username("Suman")
////                .password("1234*")
////                .roles("USER")
////                .build();
//
//        UserDetails userDetails2 = User
//                .withDefaultPasswordEncoder()
//                .username("Savrav")
//                .password("1234*")
//                //.roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(//userDetails1,
//                 userDetails2);
//    }

}