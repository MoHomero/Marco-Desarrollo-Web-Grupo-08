package com.example.tienda.repositorio;

import com.example.tienda.modelo.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
// se tiene disponible el repositorio de Carrito 
public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    // Devuelve todos los carritos de un usuario específico
    List<Carrito> findByIdUsuario(Long idUsuario);
}