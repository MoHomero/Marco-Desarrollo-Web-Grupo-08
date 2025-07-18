package com.example.tienda.service;

import com.example.tienda.modelo.Producto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceCustom {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Producto> consultaAvanzada(String campo, String operador, String valor) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Producto> cq = cb.createQuery(Producto.class);
        Root<Producto> producto = cq.from(Producto.class);

        Predicate condicion = null;

        Path<?> path = producto.get(campo);

        switch (operador) {
            case "=" -> condicion = cb.equal(path, convertirValor(path, valor));
            case "<" -> {
                if (Comparable.class.isAssignableFrom(path.getJavaType())) {
                    @SuppressWarnings("unchecked")
                    Path<? extends Comparable<Object>> compPath = (Path<? extends Comparable<Object>>) path;
                    @SuppressWarnings("unchecked")
                    Comparable<Object> compValue = (Comparable<Object>) convertirValor(path, valor);
                    condicion = cb.lessThan(compPath, compValue);
                } else {
                    throw new IllegalArgumentException("El campo '" + campo + "' no es comparable para '<'");
                }
            }
            case ">" -> {
                if (Comparable.class.isAssignableFrom(path.getJavaType())) {
                    @SuppressWarnings("unchecked")
                    Path<? extends Comparable<Object>> compPath = (Path<? extends Comparable<Object>>) path;
                    @SuppressWarnings("unchecked")
                    Comparable<Object> compValue = (Comparable<Object>) convertirValor(path, valor);
                    condicion = cb.greaterThan(compPath, compValue);
                } else {
                    throw new IllegalArgumentException("El campo '" + campo + "' no es comparable para '>'");
                }
            }
            case "like" -> {
                if (path.getJavaType().equals(String.class)) {
                    Path<String> stringPath = (Path<String>) path;
                    condicion = cb.like(cb.lower(stringPath), "%" + valor.toLowerCase() + "%");
                } else {
                    throw new IllegalArgumentException("El campo '" + campo + "' no es de tipo String para 'like'");
                }
            }
        }

        if (condicion != null) {
            cq.where(condicion);
        }

        return entityManager.createQuery(cq).getResultList();
    }

    private Object convertirValor(Path<?> path, String valor) {
        Class<?> tipo = path.getJavaType();

        if (tipo == Integer.class || tipo == int.class)
            return Integer.parseInt(valor);
        if (tipo == Long.class || tipo == long.class)
            return Long.parseLong(valor);
        if (tipo == Float.class || tipo == float.class)
            return Float.parseFloat(valor);
        if (tipo == Double.class || tipo == double.class)
            return Double.parseDouble(valor);
        if (tipo == java.math.BigDecimal.class)
            return new java.math.BigDecimal(valor);

        return valor;
    }

}
