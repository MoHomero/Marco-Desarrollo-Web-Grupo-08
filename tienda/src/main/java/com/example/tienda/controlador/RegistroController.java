package com.example.tienda.controlador;

import com.example.tienda.modelo.Usuario;
import com.example.tienda.modelo.Rol;         // Importa Rol
import com.example.tienda.modelo.NombreRol; // Importa NombreRol
import com.example.tienda.repositorio.UsuarioRepository;
import com.example.tienda.repositorio.RolRepository; // Importa RolRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class RegistroController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final RolRepository rolRepository; // Inyecta RolRepository

    @Autowired
    public RegistroController(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, RolRepository rolRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.rolRepository = rolRepository;
    }

    @PostMapping("/registro")
    public ResponseEntity<String> registrarUsuario(@RequestBody Usuario usuario) {
        // Validaciones de existencia (username y correo)
        if (usuarioRepository.existsByUsername(usuario.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El nombre de usuario ya existe.");
        }
        if (usuario.getCorreo() != null && usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El correo electrónico ya está registrado.");
        }

        // Validación básica de campos no nulos
        if (usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body("La contraseña no puede estar vacía.");
        }
        if (usuario.getUsername() == null || usuario.getUsername().isEmpty()) {
            return ResponseEntity.badRequest().body("El nombre de usuario no puede estar vacío.");
        }
        if (usuario.getCorreo() == null || usuario.getCorreo().isEmpty()) {
            return ResponseEntity.badRequest().body("El correo electrónico no puede estar vacío.");
        }

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        // --- Asignar el rol por defecto (ROLE_USER) ---
        Rol userRol = rolRepository.findByNombre(NombreRol.ROLE_USER)
                          .orElseThrow(() -> new IllegalStateException("Error de configuración: El rol 'ROLE_USER' no se encontró en la base de datos. Asegúrese de que DataLoader lo haya inicializado."));

        Set<Rol> roles = new HashSet<>();
        roles.add(userRol);
        usuario.setRoles(roles); // Establece el rol en el objeto Usuario

        usuarioRepository.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado exitosamente como ROLE_USER.");
    }
}

