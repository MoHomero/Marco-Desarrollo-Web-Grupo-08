package com.example.tienda.controlador;

import com.example.tienda.modelo.Usuario;
import com.example.tienda.repositorio.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/autenticado")
    public Map<String, Object> obtenerUsuarioAutenticado(Authentication auth) {
        Map<String, Object> respuesta = new HashMap<>();

        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            respuesta.put("autenticado", false);
            return respuesta;
        }

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String username = userDetails.getUsername();

        Usuario usuario = usuarioRepository.findByUsername(username).orElse(null);
        if (usuario == null) {
            respuesta.put("autenticado", false);
            return respuesta;
        }

        respuesta.put("autenticado", true);
        respuesta.put("username", usuario.getUsername());
        respuesta.put("correo", usuario.getCorreo());
        respuesta.put("roles", userDetails.getAuthorities());
        return respuesta;
    }
}
