package com.example.tienda.service;

import com.example.tienda.modelo.Usuario;
import com.example.tienda.modelo.Rol; // Importar la clase Rol
import com.example.tienda.repositorio.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection; // Usar Collection en lugar de List para flexibilidad
import java.util.stream.Collectors; // Necesario para .stream().map().collect()
import java.util.Optional; // Necesario para Optional en findByCorreo

@Service
public class DetallesUsuario implements UserDetailsService {

    private final UsuarioRepository userRepository;

    @Autowired
    public DetallesUsuario(UsuarioRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        // Busca al usuario por username o correo electrónico
        Usuario usuario = userRepository.findByUsername(usernameOrEmail)
                                       .orElseGet(() -> userRepository.findByCorreo(usernameOrEmail)
                                       .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con: " + usernameOrEmail)));

        // --- ESTO ES LO QUE DEBES TENER PARA QUE LOS ROLES FUNCIONEN ---
        // Mapea los roles de tu entidad Usuario a las autoridades de Spring Security
        Collection<SimpleGrantedAuthority> authorities = usuario.getRoles().stream()
                                                               .map(rol -> new SimpleGrantedAuthority(rol.getNombre().name()))
                                                               .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                usuario.getUsername(),
                usuario.getPassword(), // Contraseña hasheada de la DB
                authorities // ¡Ahora carga los roles reales del usuario!
        );
    }
}