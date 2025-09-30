package com.example.businessservice.client;

import com.example.businessservice.client.model.DataCategoria;
import com.example.businessservice.client.model.DataInventario;
import com.example.businessservice.client.model.DataProducto;
import com.example.businessservice.dto.ProductoRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "data-service", url = "${data.service.url}")
public interface DataServiceClient {

    @GetMapping("/data/productos")
    List<DataProducto> obtenerTodosLosProductos();

    @GetMapping("/data/productos/{id}")
    DataProducto obtenerProductoPorId(@PathVariable Long id);

    @PostMapping("/data/productos")
    DataProducto crearProducto(@RequestBody ProductoRequest request);

    @PutMapping("/data/productos/{id}")
    DataProducto actualizarProducto(@PathVariable Long id, @RequestBody ProductoRequest request);

    @DeleteMapping("/data/productos/{id}")
    void eliminarProducto(@PathVariable Long id);

    @GetMapping("/data/productos/categoria/{nombre}")
    List<DataProducto> obtenerProductosPorCategoria(@PathVariable String nombre);

    @GetMapping("/data/categorias")
    List<DataCategoria> obtenerTodasLasCategorias();

    @GetMapping("/data/inventario/stock-bajo")
    List<DataInventario> obtenerProductosConStockBajo();
}
