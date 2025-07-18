package com.example.tienda.modelo;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;



@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String telefono;
    private String correo;

    @Column(name = "recovery_token")
    private String recoveryToken;

    @Column(name = "recovery_token_expires_at")
    private LocalDateTime recoveryTokenExpiresAt;
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
        name = "usuario_roles", // Nombre de la tabla intermedia en la BD
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private Set<Rol> roles = new HashSet<>(); 
    // Constructor vacío requerido por JPA
    public Usuario() {}

    // Constructor con parámetros (opcional, útil si lo necesitas en pruebas o servicios)
    public Usuario(String username, String password, String telefono, String correo) {
        this.username = username;
        this.password = password;
        this.telefono = telefono;
        this.correo = correo;
        this.roles = new HashSet<>();
    }

    // Getters y Setters

      public void addRol(Rol rol) {
        this.roles.add(rol);
    }

    public void removeRol(Rol rol) {
        this.roles.remove(rol);
    }

      public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public String getRecoveryToken() { 
        return recoveryToken;
    }

    public void setRecoveryToken(String recoveryToken) { 
        this.recoveryToken = recoveryToken;
    }

    public LocalDateTime getRecoveryTokenExpiresAt() { 
        return recoveryTokenExpiresAt;
    }

    public void setRecoveryTokenExpiresAt(LocalDateTime recoveryTokenExpiresAt) { 
        this.recoveryTokenExpiresAt = recoveryTokenExpiresAt;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", telefono='" + telefono + '\'' +
                ", correo='" + correo + '\'' +
                '}';
    }
}
