package com.example.dataservice.controller;

import com.example.dataservice.dto.ProductoCreateUpdateDTO;
import com.example.dataservice.entity.Categoria;
import com.example.dataservice.entity.Inventario;
import com.example.dataservice.entity.Producto;
import com.example.dataservice.exception.RecursoNoEncontradoException;
import com.example.dataservice.service.CategoriaService;
import com.example.dataservice.service.InventarioService;
import com.example.dataservice.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/data")
@RequiredArgsConstructor
public class DataController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;
    private final InventarioService inventarioService;

    // Productos
    @GetMapping("/productos")
    public List<Producto> obtenerTodosLosProductos() {
        return productoService.obtenerTodos();
    }

    @GetMapping("/productos/{id}")
    public Producto obtenerProductoPorId(@PathVariable Long id) { return productoService.buscarPorId(id); }

    @PostMapping("/productos")
    @ResponseStatus(HttpStatus.CREATED)
    public Producto crearProducto(@Valid @RequestBody ProductoCreateUpdateDTO dto) {
        Categoria cat = categoriaService.buscarPorNombre(dto.categoriaNombre())
                .orElseGet(() -> categoriaService.guardar(Categoria.builder()
                        .nombre(dto.categoriaNombre()).descripcion("auto-creada").build()));

        Producto p = Producto.builder()
                .nombre(dto.nombre())
                .descripcion(dto.descripcion())
                .precio(dto.precio())
                .categoria(cat)
                .build();

        Producto guardado = productoService.guardar(p);

        Inventario inv = Inventario.builder()
                .producto(guardado)
                .cantidad(0)
                .stockMinimo(5)
                .fechaActualizacion(LocalDateTime.now())
                .build();

        inventarioService.guardar(inv);
        guardado.setInventario(inv);
        return guardado;
    }

    @PutMapping("/productos/{id}")
    public Producto actualizarProducto(@PathVariable Long id, @Valid @RequestBody ProductoCreateUpdateDTO dto) {
        Categoria cat = categoriaService.buscarPorNombre(dto.categoriaNombre())
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada: " + dto.categoriaNombre()));

        Producto p = Producto.builder()
                .nombre(dto.nombre())
                .descripcion(dto.descripcion())
                .precio(dto.precio())
                .categoria(cat)
                .build();

        return productoService.actualizar(id, p);
    }

    @DeleteMapping("/productos/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarProducto(@PathVariable Long id) { productoService.eliminar(id); }

    @GetMapping("/productos/categoria/{nombre}")
    public List<Producto> obtenerProductosPorCategoria(@PathVariable String nombre) {
        return productoService.buscarPorCategoria(nombre);
    }

    // Categorías
    @GetMapping("/categorias")
    public List<Categoria> obtenerTodasLasCategorias() { return categoriaService.obtenerTodas(); }

    // Inventario
    @GetMapping("/inventario")
    public List<Inventario> obtenerInventario() { return inventarioService.obtenerTodos(); }

    @GetMapping("/inventario/stock-bajo")
    public List<Inventario> obtenerStockBajo(@RequestParam(defaultValue = "5") Integer umbral) {
        return inventarioService.obtenerProductosConStockBajo(umbral);
    }

    @PutMapping("/inventario/{productoId}/stock")
    public Inventario actualizarStock(@PathVariable Long productoId, @RequestParam Integer cantidad) {
        return inventarioService.obtenerTodos().stream()
                .filter(i -> i.getProducto().getId().equals(productoId))
                .findFirst()
                .map(i -> {
                    i.setCantidad(cantidad);
                    i.setFechaActualizacion(LocalDateTime.now());
                    return inventarioService.guardar(i);
                })
                .orElseThrow(() -> new RecursoNoEncontradoException("Inventario no encontrado para producto: " + productoId));
    }
}
