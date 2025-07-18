package com.example.tienda.controlador;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import java.util.HashMap;

// para devolver respuestas JSON y manejar las peticiones HTTP
@RestController 
@RequestMapping("/api")
public class PagoController {
    // captura los datos del pago en cuanto al monto y el usuario
    @PostMapping("/pagos")
    public ResponseEntity<Map<String, String>> registrarPago(@RequestBody Map<String, Object> pago) {
        // Mostrar los datos recibidos
        System.out.println("Pago recibido: " + pago);

        // Respuesta JSON con un mensaje de éxito
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Pago registrado correctamente");
        return ResponseEntity.ok(respuesta);
    }
}
