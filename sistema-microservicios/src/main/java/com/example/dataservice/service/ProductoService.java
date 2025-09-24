package com.example.dataservice.service;

import com.example.dataservice.entity.Producto;

import java.util.List;

public interface ProductoService {
    List<Producto> obtenerTodos();
    Producto buscarPorId(Long id);
    Producto guardar(Producto p);
    Producto actualizar(Long id, Producto p);
    void eliminar(Long id);
    List<Producto> buscarPorCategoria(String nombreCategoria);
}
