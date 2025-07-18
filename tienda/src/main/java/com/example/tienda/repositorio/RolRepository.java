package com.example.tienda.repositorio;

import com.example.tienda.modelo.Rol;
import com.example.tienda.modelo.NombreRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombre(NombreRol nombre);
}