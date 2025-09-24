package com.example.dataservice.service;

import com.example.dataservice.entity.Categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaService {
    List<Categoria> obtenerTodas();
    Categoria guardar(Categoria c);
    Optional<Categoria> buscarPorNombre(String nombre);
}
