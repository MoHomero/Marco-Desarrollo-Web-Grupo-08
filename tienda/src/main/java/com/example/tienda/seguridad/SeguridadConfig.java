package com.example.tienda.seguridad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.tienda.service.DetallesUsuario;

@Configuration
@EnableWebSecurity
public class SeguridadConfig {

    private final DetallesUsuario detallesUsuario;

    public SeguridadConfig(DetallesUsuario detallesUsuario){
        this.detallesUsuario = detallesUsuario;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(detallesUsuario); // Establece tu DetallesUsuario
        authProvider.setPasswordEncoder(passwordEncoder());          // Establece tu PasswordEncoder
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/auth/forgot-password", "/api/auth/reset-password").permitAll()
                .requestMatchers("/api/registro").permitAll()
                .requestMatchers("/api/usuario/autenticado").permitAll()
                .requestMatchers("/", "/Registro.html","/api/productos/*", "/Login.html", "/Inicio.html","/Producto.html", "/index.html", "/forgot_password.html", "/reset_password.html", "/css/**", "/img/**", "/js/**").permitAll()
                .requestMatchers("/api/productos/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/Login.html") // La URL de tu página HTML de login
                .loginProcessingUrl("/perform_login") // URL a la que el formulario **POSTea** para que Spring Security procese
                .defaultSuccessUrl("/Inicio.html", true) // Redirige después de un login exitoso
                .failureUrl("/Login.html?error=true") // Redirige después de un login fallido
                .permitAll() // Permite acceso a la página de login y a la URL de procesamiento
            )
            .logout(logout -> logout
                .logoutUrl("/perform_logout") // URL para hacer logout
                .logoutSuccessUrl("/Login.html?logout=true") // Redirige después de un logout exitoso
                .permitAll() // Permite acceso a la URL de logout
            );

        return http.build();
    }
}