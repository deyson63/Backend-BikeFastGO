    package com.upc.bikefastgo.config;

    import lombok.RequiredArgsConstructor;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.authentication.AuthenticationProvider;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.config.http.SessionCreationPolicy;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


    @Configuration
    @EnableWebSecurity
    @RequiredArgsConstructor
    public class SecurityConfig {
        private final JwtAuthenticationFilter jwtAuthFilter;
        private final AuthenticationProvider authenticationProvider;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http.csrf(csrf -> csrf.disable())
                    .authorizeHttpRequests(authorize -> authorize
                            .requestMatchers("/api/cyclescape/v1/auth/**").permitAll()
                            .requestMatchers("/api/cyclescape/v1/users",
                                    "/api/cyclescape/v1/rents","/api/cyclescape/v1/cards","/api/cyclescape/v1/bicycles/**").authenticated()
                            .anyRequest().permitAll())
                    .csrf(csrf -> csrf.disable())
                    .sessionManagement(session -> session
                            // cuando se establece en STATELESS, significa que no se creará ni
                            // mantendrá ninguna sesión HTTP en el servidor.
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authenticationProvider(authenticationProvider)
                    .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

            return http.build();
        }



    }
