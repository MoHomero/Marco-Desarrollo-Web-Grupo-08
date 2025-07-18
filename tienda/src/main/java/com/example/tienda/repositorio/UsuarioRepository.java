package com.example.tienda.repositorio;

import com.example.tienda.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsernameAndPassword(String username, String password);
    Optional<Usuario> findByUsername(String username);
    Optional<Usuario> findByCorreo(String correo);
    Optional<Usuario> findByRecoveryToken(String recoveryToken);
    // Para buscar por email O username
    Optional<Usuario> findByCorreoOrUsername(String correo, String username);
     boolean existsByUsername(String username);
     boolean existsByCorreo(String correo);
}

