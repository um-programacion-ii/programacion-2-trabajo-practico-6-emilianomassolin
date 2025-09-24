package com.example.dataservice.service;

import com.example.dataservice.entity.Inventario;

import java.util.List;

public interface InventarioService {
    List<Inventario> obtenerTodos();
    List<Inventario> obtenerProductosConStockBajo(Integer umbral);
    Inventario guardar(Inventario inv);
}
