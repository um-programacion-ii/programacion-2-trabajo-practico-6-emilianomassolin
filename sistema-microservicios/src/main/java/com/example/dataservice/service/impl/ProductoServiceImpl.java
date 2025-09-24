package com.example.dataservice.service.impl;

import com.example.dataservice.entity.Producto;
import com.example.dataservice.exception.RecursoNoEncontradoException;
import com.example.dataservice.repository.ProductoRepository;
import com.example.dataservice.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    @Override
    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    @Override
    public Producto buscarPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado: " + id));
    }

    @Override
    public Producto guardar(Producto p) {
        return productoRepository.save(p);
    }

    @Override
    public Producto actualizar(Long id, Producto p) {
        Producto existente = buscarPorId(id);
        existente.setNombre(p.getNombre());
        existente.setDescripcion(p.getDescripcion());
        existente.setPrecio(p.getPrecio());
        existente.setCategoria(p.getCategoria());
        return productoRepository.save(existente);
    }

    @Override
    public void eliminar(Long id) {
        Producto existente = buscarPorId(id);
        productoRepository.delete(existente);
    }

    @Override
    public List<Producto> buscarPorCategoria(String nombreCategoria) {
        return productoRepository.findByCategoriaNombre(nombreCategoria);
    }
}
