package com.example.tienda.controlador;


import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.tienda.service.UsuarioService;
import java.util.Map;

//controladores para manejar la autenticación y recuperación de contraseñas
@RestController
@RequestMapping("/api/auth") 
@CrossOrigin(origins = "*") // Permite CORS desde cualquier origen (ajustar en producción)
public class AuthController {

    @Autowired
    private UsuarioService userService;

    // Endpoint para solicitar el enlace de recuperación
    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(@RequestBody Map<String, String> payload) {
        String emailOrUsername = payload.get("email_username");
        if (emailOrUsername == null || emailOrUsername.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Por favor, ingrese su usuario o correo electrónico."));
        }

        try {
            userService.processForgotPassword(emailOrUsername);
            // Siempre respondemos genéricamente por seguridad
            return ResponseEntity.ok(Map.of("message", "Si el usuario existe, se ha enviado un enlace de recuperación a su correo electrónico."));
        } catch (MessagingException e) {
            // Error al enviar el correo, pero aún así respondemos genéricamente
            System.err.println("Error al enviar correo de recuperación: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Ocurrió un error al procesar su solicitud. Intente de nuevo más tarde."));
        } catch (Exception e) {
            System.err.println("Error inesperado en forgot-password: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Ocurrió un error interno del servidor."));
        }
    }

    // Endpoint para restablecer la contraseña
    // Este endpoint recibe el token y las nuevas contraseñas
    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody Map<String, String> payload) {
        String token = payload.get("token");
        String newPassword = payload.get("new_password");
        String confirmPassword = payload.get("confirm_password");

        if (token == null || token.trim().isEmpty() ||
            newPassword == null || newPassword.trim().isEmpty() ||
            confirmPassword == null || confirmPassword.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Todos los campos son requeridos."));
        }

        if (!newPassword.equals(confirmPassword)) {
            return ResponseEntity.badRequest().body(Map.of("message", "Las contraseñas no coinciden."));
        }

        if (newPassword.length() < 8) {
            return ResponseEntity.badRequest().body(Map.of("message", "La contraseña debe tener al menos 8 caracteres."));
        }
        // Puedes añadir más validaciones de complejidad aquí (mayúsculas, números, etc.)

        boolean resetSuccess = userService.resetPassword(token, newPassword);

        if (resetSuccess) {
            return ResponseEntity.ok(Map.of("message", "Contraseña restablecida exitosamente. Ahora puedes iniciar sesión."));
        } else {
            // Por seguridad, un mensaje genérico para token inválido/expirado
            return ResponseEntity.badRequest().body(Map.of("message", "El enlace de recuperación es inválido o ha expirado. Por favor, solicite uno nuevo."));
        }
    }
}