package com.example.tienda.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING) // Guarda el nombre del enum como String en la BD
    @Column(nullable = false, unique = true)
    private NombreRol nombre;

    public Rol() {}

    public Rol(NombreRol nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NombreRol getNombre() {
        return nombre;
    }

    public void setNombre(NombreRol nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Rol{" +
               "id=" + id +
               ", nombre=" + nombre +
               '}';
    }
}