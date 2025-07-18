package com.example.tienda.repositorio;

import com.example.tienda.modelo.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    // Devuelve todos los pedidos hechos por un usuario específico
    List<Pedido> findByIdUsuario(Long idUsuario);
}
