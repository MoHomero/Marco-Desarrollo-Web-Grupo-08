package com.example.tienda.service;

import com.example.tienda.modelo.Usuario;
import com.example.tienda.repositorio.UsuarioRepository;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; 

    @Autowired
    private EmailService emailService;

    @Value("${app.base-url}")
    private String appBaseUrl;

    @Transactional
    public void processForgotPassword(String emailOrUsername) throws MessagingException {
        Optional<Usuario> usuarioOptional = userRepository.findByCorreoOrUsername(emailOrUsername, emailOrUsername);

        // Por seguridad, siempre respondemos genéricamente, incluso si el usuario no existe.
        // Esto evita la enumeración de usuarios.
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();

            // Generar token único
            String token = UUID.randomUUID().toString();
            // Establecer expiración (ej. 1 hora desde ahora)
            LocalDateTime expiresAt = LocalDateTime.now().plusHours(1);

            usuario.setRecoveryToken(token);
            usuario.setRecoveryTokenExpiresAt(expiresAt);
            userRepository.save(usuario); // Guardar el token y la expiración en la DB

            // Construir enlace de recuperación
            String recoveryLink = appBaseUrl + "/reset_password.html?token=" + token;

            // Enviar correo electrónico
            String subject = "Recuperación de Contraseña para tu Cuenta";
            String body = "Hola <strong>" + usuario.getUsername() + "</strong>,<br><br>"
                        + "Hemos recibido una solicitud para restablecer tu contraseña. Haz clic en el siguiente enlace para continuar:<br><br>"
                        + "<a href='" + recoveryLink + "'>Restablecer mi Contraseña</a><br><br>"
                        + "Este enlace expirará en 1 hora.<br><br>"
                        + "Si no solicitaste esto, puedes ignorar este correo.<br><br>"
                        + "Saludos,<br>"
                        + "Tu Aplicación Web";
            emailService.sendEmail(usuario.getCorreo(), subject, body);
        }
        // No lanzamos excepción aquí para el frontend, solo registramos.
        // El mensaje de éxito genérico se enviará de todas formas.
    }

    @Transactional
    public boolean resetPassword(String token, String newPassword) {
        Optional<Usuario> usuarioOptional = userRepository.findByRecoveryToken(token);

        if (usuarioOptional.isEmpty()) {
            return false; // Token inválido o no encontrado
        }

        Usuario usuario = usuarioOptional.get();

        // Verificar si el token ha expirado
        if (usuario.getRecoveryTokenExpiresAt() == null || usuario.getRecoveryTokenExpiresAt().isBefore(LocalDateTime.now())) {
            // Limpiar el token expirado de la DB
            usuario.setRecoveryToken(null);
            usuario.setRecoveryTokenExpiresAt(null);
            userRepository.save(usuario);
            return false; // Token expirado
        }

        // Hashear la nueva contraseña con Spring Security PasswordEncoder
        String encodedPassword = passwordEncoder.encode(newPassword);
        usuario.setPassword(encodedPassword);

        // Limpiar el token y la fecha de expiración después de usarlo
        usuario.setRecoveryToken(null);
        usuario.setRecoveryTokenExpiresAt(null);
        userRepository.save(usuario);

        return true; // Contraseña restablecida exitosamente
    }
}