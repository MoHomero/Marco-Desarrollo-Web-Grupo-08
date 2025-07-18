package com.example.tienda.config;

import com.example.tienda.modelo.NombreRol;
import com.example.tienda.modelo.Rol;
import com.example.tienda.repositorio.RolRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner initRoles(RolRepository rolRepository) {
        return args -> {
            if (rolRepository.findByNombre(NombreRol.ROLE_ADMIN).isEmpty()) {
                rolRepository.save(new Rol(NombreRol.ROLE_ADMIN));
                System.out.println("Rol ROLE_ADMIN creado.");
            }
            if (rolRepository.findByNombre(NombreRol.ROLE_USER).isEmpty()) {
                rolRepository.save(new Rol(NombreRol.ROLE_USER));
                System.out.println("Rol ROLE_USER creado.");
            }
        };
    }
}