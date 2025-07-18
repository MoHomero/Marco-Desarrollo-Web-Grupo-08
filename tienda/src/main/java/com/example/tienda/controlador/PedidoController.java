package com.example.tienda.controlador;

import com.example.tienda.modelo.Carrito;
import com.example.tienda.modelo.Pedido;
import com.example.tienda.modelo.Usuario;
import com.example.tienda.repositorio.CarritoRepository;
import com.example.tienda.repositorio.PedidoRepository;
import com.example.tienda.repositorio.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.*;

@RestController
@RequestMapping("/api/pedido")
@CrossOrigin(origins = "http://localhost:5500", allowCredentials = "true")
public class PedidoController {
    // dependencias para acceder a los repositorios de pedidos, carrito y usuario
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Obtener ID del usuario autenticado
    private Long obtenerIdUsuarioAutenticado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            return null;
        }
        // busca el usuario autenticado por su nombre de usuario y obtiene su ID
        String username = auth.getName();
        Optional<Usuario> usuario = usuarioRepository.findByUsername(username);
        // si no lo encuentra, retorna null
        return usuario.map(Usuario::getId).orElse(null);
    }

    // Registrar pedidos desde el carrito
    @PostMapping("/registrar")
    public Map<String, String> registrarPedidosDesdeCarrito() {
        Long idUsuario = obtenerIdUsuarioAutenticado();
        if (idUsuario == null) {
            return Map.of("mensaje", "Usuario no autenticado");
        }
        // busca los items del carrito del usuario autenticado que estén pendientes
        List<Carrito> carrito = carritoRepository.findByIdUsuario(idUsuario)
            .stream()
            .filter(item -> "pendiente".equals(item.getEstado()))
            .toList();
        // copia los items del carrito a pedidos
        for (Carrito item : carrito) {
            Pedido pedido = new Pedido();
            pedido.setIdUsuario(idUsuario);
            pedido.setNombreProducto(item.getProducto().getNombre());
            pedido.setCantidad(item.getCantidad());
            pedido.setEstado("Pagado");
            pedido.setFechaPedido(new Timestamp(System.currentTimeMillis()));
            pedido.setImagenProducto(item.getProducto().getImagen());
            pedidoRepository.save(pedido);
        }
        // Limpia el carrito después de registrar los pedidos
        carritoRepository.deleteAll(carrito);

        return Map.of("mensaje", "Pedido registrado correctamente");
    }
    // Registrar pedido desde un producto específico
    @PostMapping("/producto/pedido")
public ResponseEntity<?> registrarPedidoDesdeProducto(@RequestBody Pedido pedido) {
    Long idUsuario = obtenerIdUsuarioAutenticado(); // Obtiene el ID del usuario autenticado
    if (idUsuario == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado");
    }
    // guarda el pedido con los datos del usuario autenticado
    pedido.setIdUsuario(idUsuario);
    pedido.setEstado("Pagado");
    pedido.setFechaPedido(new Timestamp(System.currentTimeMillis()));

    pedidoRepository.save(pedido);

    return ResponseEntity.ok().body(Map.of("mensaje", "Pedido registrado correctamente"));
}

    // Obtiene los pedidos del usuario autenticado
    @GetMapping
    public List<Pedido> listarPedidosDelUsuario() {
        Long idUsuario = obtenerIdUsuarioAutenticado();
        if (idUsuario == null) return List.of();
        return pedidoRepository.findByIdUsuario(idUsuario);
    }
}
