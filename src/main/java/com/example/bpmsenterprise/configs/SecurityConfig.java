//package com.example.bpmsenterprise.configs;
//
//import com.example.bpmsenterprise.layers.services.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.HttpStatusEntryPoint;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//
//@EnableWebSecurity
//    @EnableMethodSecurity(securedEnabled = true)
//    public class SecurityConfig {
//        private UserService userService;
//        private JwtRequestFilter jwtRequestFilter;
//
//        @Autowired
//        public void setUserService(UserService userService) {
//            this.userService = userService;
//        }
//
//        @Autowired
//        public void setJwtRequestFilter(JwtRequestFilter jwtRequestFilter) {
//            this.jwtRequestFilter = jwtRequestFilter;
//        }
//
//        @Bean
//        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//            http
//                    .csrf(AbstractHttpConfigurer::disable)
//                    .cors(AbstractHttpConfigurer::disable)
//                    .authorizeHttpRequests(authz -> authz
//                            .requestMatchers("/secured").authenticated()
//                            .requestMatchers("/info").authenticated()
//                            .requestMatchers("/admin").hasRole("ADMIN")
//                            .anyRequest().permitAll())
//                    .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                    .exceptionHandling((exceptionHandling) -> exceptionHandling.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));
//            //addFilterBefore()
//            return http.build();
//        }
//
//        @Bean
//        public DaoAuthenticationProvider daoAuthenticationProvider() {
//            DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//            daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
//            daoAuthenticationProvider.setUserDetailsService(userService);
//            return daoAuthenticationProvider;
//        }
//
//        @Bean
//        public BCryptPasswordEncoder passwordEncoder() {
//            return new BCryptPasswordEncoder();
//        }
//
//        @Bean
//        public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//            return authenticationConfiguration.getAuthenticationManager();
//        }
//    }
//
//
