package com.example.businessservice.controller;

import com.example.businessservice.dto.ProductoDTO;
import com.example.businessservice.dto.ProductoRequest;
import com.example.businessservice.service.ProductoBusinessService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BusinessController {

    private final ProductoBusinessService productoService;

    public BusinessController(ProductoBusinessService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/productos")
    public List<ProductoDTO> todos() {
        return productoService.obtenerTodosLosProductos();
    }

    @GetMapping("/productos/{id}")
    public ProductoDTO porId(@PathVariable Long id) {
        return productoService.obtenerProductoPorId(id);
    }

    @PostMapping("/productos")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductoDTO crear(@Valid @RequestBody ProductoRequest request) {
        return productoService.crearProducto(request);
    }

    @GetMapping("/productos/categoria/{nombre}")
    public List<ProductoDTO> porCategoria(@PathVariable String nombre) {
        return productoService.obtenerPorCategoria(nombre);
    }
}
