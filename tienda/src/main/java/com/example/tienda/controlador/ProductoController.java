package com.example.tienda.controlador;

import com.example.tienda.modelo.Producto;
import com.example.tienda.repositorio.ProductoRepository;
import com.example.tienda.service.ProductoServiceCustom;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {
    @Autowired
    private ProductoServiceCustom productoServiceCustom;

    @Autowired
    private ProductoRepository productoRepository;

    // Obtener producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerProductoPorId(@PathVariable int id) {
        Optional<Producto> producto = productoRepository.findById(id);
        if (producto.isPresent()) {
            return ResponseEntity.ok(producto.get());
        } else {
            return ResponseEntity.status(404).body("Producto no encontrado");
        }
    }

    // Buscar productos por nombre o descripción
    @GetMapping("/buscar")
    public ResponseEntity<?> buscarProductos(@RequestParam String query) {
        List<Producto> productos = productoRepository
                .findByNombreContainingIgnoreCaseOrDescripcionContainingIgnoreCase(query, query);
        return ResponseEntity.ok(productos);
    }

    // Eliminar producto por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable int id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return ResponseEntity.ok("Producto eliminado correctamente");
        } else {
            return ResponseEntity.status(404).body("Producto no encontrado");
        }
    }

    @PostMapping
    public ResponseEntity<?> crearProducto(@RequestBody Producto producto) {
        try {
            producto.setFechaRegistro(new Timestamp(System.currentTimeMillis())); // Generar fecha automáticamente
            return ResponseEntity.ok(productoRepository.save(producto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al guardar el producto");
        }
    }

    // Endpoint para Filtro Rápido
    @GetMapping("/filtrar")
    public ResponseEntity<List<Producto>> filtrarProductos(
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String condicion,
            @RequestParam(required = false) Boolean envioGratis) {
        List<Producto> resultados = productoRepository.filtrarDinamico(categoria, condicion, envioGratis);
        return ResponseEntity.ok(resultados);
    }

    @GetMapping("/reporte")
    public void exportarReporte(HttpServletResponse response) throws IOException {
        System.out.println("🔥 Generando archivo Excel...");

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=productos.xlsx");

        Workbook workbook = new XSSFWorkbook();
        Sheet hoja = workbook.createSheet("Productos");

        Row cabecera = hoja.createRow(0);
        String[] columnas = { "ID", "Nombre", "Categoría", "Descripción", "Precio", "Stock", "Imagen" };
        for (int i = 0; i < columnas.length; i++) {
            cabecera.createCell(i).setCellValue(columnas[i]);
        }

        int filaIdx = 1;
        for (Producto p : productoRepository.findAll()) {
            Row fila = hoja.createRow(filaIdx++);
            fila.createCell(0).setCellValue(p.getId());
            fila.createCell(1).setCellValue(p.getNombre());
            fila.createCell(2).setCellValue(p.getCategoria());
            fila.createCell(3).setCellValue(p.getDescripcion());
            fila.createCell(4).setCellValue(p.getPrecio().doubleValue());
            fila.createCell(5).setCellValue(p.getStock());
            fila.createCell(6).setCellValue(p.getImagen() != null ? p.getImagen() : "");
        }

        workbook.write(response.getOutputStream());
        workbook.close();

        System.out.println("✅ Excel generado con éxito");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarProducto(@PathVariable int id, @RequestBody Producto productoActualizado) {
        Optional<Producto> existente = productoRepository.findById(id);
        if (existente.isPresent()) {
            Producto producto = existente.get();
            producto.setNombre(productoActualizado.getNombre());
            producto.setCategoria(productoActualizado.getCategoria());
            producto.setDescripcion(productoActualizado.getDescripcion());
            producto.setPrecio(productoActualizado.getPrecio());
            producto.setStock(productoActualizado.getStock());
            producto.setImagen(productoActualizado.getImagen());
            return ResponseEntity.ok(productoRepository.save(producto));
        } else {
            return ResponseEntity.status(404).body("Producto no encontrado");
        }
    }

    @GetMapping
    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.findAll();
    }
    
    @GetMapping("/consulta-avanzada")
public ResponseEntity<?> consultaAvanzada(
    @RequestParam String campo,
    @RequestParam String operador,
    @RequestParam String valor
) {
    try {
        List<Producto> resultado = productoServiceCustom.consultaAvanzada(campo, operador, valor);
        return ResponseEntity.ok(resultado);
    } catch (Exception e) {
        return ResponseEntity.badRequest().body("Error en consulta avanzada: " + e.getMessage());
    }
}


}