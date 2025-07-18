package com.example.tienda.repositorio;

import com.example.tienda.modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    List<Producto> findByNombreContainingIgnoreCaseOrDescripcionContainingIgnoreCase(String nombre, String descripcion);

    @Query("SELECT p FROM Producto p WHERE " +
           "(:categoria IS NULL OR p.categoria = :categoria) AND " +
           "(:condicion IS NULL OR p.condicion = :condicion) AND " +
           "(:envioGratis IS NULL OR p.envioGratis = :envioGratis)")
    List<Producto> filtrarDinamico(
        @Param("categoria") String categoria,
        @Param("condicion") String condicion,
        @Param("envioGratis") Boolean envioGratis
    );
}
