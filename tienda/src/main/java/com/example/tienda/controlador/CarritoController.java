package com.example.tienda.controlador;

import com.example.tienda.modelo.Carrito;
import com.example.tienda.modelo.Usuario;
import com.example.tienda.repositorio.CarritoRepository;
import com.example.tienda.repositorio.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/carrito")
@CrossOrigin(origins = "http://localhost:5500", allowCredentials = "true") //Permite que el frontend haga peticiones al backend
public class CarritoController {

    // Inyectamos los repositorios necesarios para accede a la base de datos
    @Autowired
    private CarritoRepository carritoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    // Obtener ID del usuario autenticado y verifica que no sea anonimo 
    private Long obtenerIdUsuarioAutenticado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            return null;
        }
        String username = auth.getName(); // Obtener el nombre de usuario del objeto Authentication
        // Buscar el usuario en la base de datos por su nombre de usuario
        Optional<Usuario> usuario = usuarioRepository.findByUsername(username);
        return usuario.map(Usuario::getId).orElse(null); 
    }

    // Obtener productos del carrito y si no hay productos, retorna una lista vacía
    @GetMapping
    public List<Carrito> obtenerCarritoPorUsuario() {
        Long idUsuario = obtenerIdUsuarioAutenticado();
        if (idUsuario == null) return List.of(); // No autenticado
        return carritoRepository.findByIdUsuario(idUsuario);
    }

    // Agregar producto al carrito y verifica el usuario
    @PostMapping
    public String agregarAlCarrito(@RequestBody Carrito nuevoItem) {
        Long idUsuario = obtenerIdUsuarioAutenticado();
        if (idUsuario == null) return "NO_SESSION";

        // Verifica el usuario logueado y pone el estado del carrito como pendiente
        nuevoItem.setIdUsuario(idUsuario);
        nuevoItem.setEstado("pendiente");

        // Buscar si ya existe el producto en el carrito del usuario
        List<Carrito> existentes = carritoRepository.findByIdUsuario(idUsuario).stream()
            .filter(c -> c.getIdProducto() == nuevoItem.getIdProducto() && "pendiente".equals(c.getEstado()))
            .toList();
        // Si existe, actualiza la cantidad y la fecha, si no, agrega el nuevo item
        if (!existentes.isEmpty()) {
            Carrito existente = existentes.get(0);
            existente.setCantidad(existente.getCantidad() + nuevoItem.getCantidad());
            existente.setFechaAgregado(new Timestamp(System.currentTimeMillis()));
            carritoRepository.save(existente);
        } else {
            nuevoItem.setFechaAgregado(new Timestamp(System.currentTimeMillis()));
            carritoRepository.save(nuevoItem);
        }

        return "OK";
    }

    //  Eliminar producto del carrito y verifica el usuario
    @DeleteMapping("/{id}")
    public String eliminarDelCarrito(@PathVariable Long id) {
        Long idUsuario = obtenerIdUsuarioAutenticado();
        if (idUsuario == null) return "NO_SESSION";
        // Verifica si el item existe y pertenece al usuario autenticado
        Optional<Carrito> item = carritoRepository.findById(id);
        if (item.isPresent() && item.get().getIdUsuario().equals(idUsuario)) {
            carritoRepository.deleteById(id);
            return "OK";
        }
        return "NOT_FOUND";
    }
}
