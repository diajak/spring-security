package com.springboot.demosecurity.security;

import com.springboot.demosecurity.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.sql.DataSource;

@Configuration
public class DemoSecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService); //set the custom user details service
        auth.setPasswordEncoder(passwordEncoder()); //set the password encoder - bcrypt
        return auth;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationSuccessHandler customAuthenticationSuccessHandler) throws Exception {

        http.authorizeHttpRequests(p ->
                        p
                                .requestMatchers("/").permitAll()
                                .requestMatchers("/employees/**").hasRole("EMPLOYEE")
                                .requestMatchers("/leaders/**").hasRole("MANAGER")
                                .requestMatchers("/systems/**").hasRole("ADMIN")
                                .requestMatchers("/register/**").permitAll()
                                .anyRequest().authenticated()).
                formLogin( p ->
                        p
                                .loginPage("/showLoginPage")
                .loginProcessingUrl("/authenticateTheUser")
               .successHandler(customAuthenticationSuccessHandler)
                .permitAll()).logout(logout -> logout.permitAll().logoutSuccessUrl("/")).exceptionHandling(configurer ->
                configurer.accessDeniedPage("/access-denied")
        );


        // use HTTP Basic authentication
     // http.httpBasic(Customizer.withDefaults());

        // disable Cross Site Request Forgery (CSRF)
        // in general not required for stateless REST APIs that use POST, PUT, DELETE and/or PATCH
     // http.csrf(AbstractHttpConfigurer::disable);;

        return http.build();

    }





    /*
    //add support for JDBC.. no more hardcoded users

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){


        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        jdbcUserDetailsManager.setUsersByUsernameQuery("select user_id,pw,active from members where user_id=?");

        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("select user_id,role from roles where user_id=?");

    // tell Spring Security to use JDBC authentication with our datasource
        //return new JdbcUserDetailsManager(dataSource);
        return jdbcUserDetailsManager;
    }









    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(p ->
                p.anyRequest().authenticated()
                ).formLogin( p-> p.loginPage("/showLoginPage").loginProcessingUrl("/authenticateTheUser").permitAll()).logout( p -> p.permitAll());

        http.authorizeHttpRequests(p ->
                p.
                        requestMatchers("/").hasRole("EMPLOYEE").
                        requestMatchers("/leaders/**").hasRole("MANAGER").
                        requestMatchers("/systems/**").hasRole("ADMIN").
                        anyRequest().authenticated()).
                formLogin( p-> p.loginPage("/showLoginPage").loginProcessingUrl("/authenticateTheUser").permitAll()).
                logout( p -> p.permitAll()).exceptionHandling(p ->
                p.accessDeniedPage("/access-denied")
        );

            return http.build();

    }



    @Bean
    public InMemoryUserDetailsManager userDetialsManager() {

        UserDetails john = User.builder().username("john").password("{noop}test123").roles("EMPLOYEE").build();
        UserDetails mary = User.builder().username("mary").password("{noop}test123").roles("EMPLOYEE","MANAGER").build();
        UserDetails susan = User.builder().username("susan").password("{noop}test123").roles("EMPLOYEE","MANAGER","ADMIN").build();

        return new InMemoryUserDetailsManager(john,mary,susan);
    } */


}
