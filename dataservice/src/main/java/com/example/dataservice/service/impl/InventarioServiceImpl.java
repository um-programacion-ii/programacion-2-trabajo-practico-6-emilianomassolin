package com.example.dataservice.service.impl;

import com.example.dataservice.entity.Inventario;
import com.example.dataservice.repository.InventarioRepository;
import com.example.dataservice.service.InventarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InventarioServiceImpl implements InventarioService {

    private final InventarioRepository inventarioRepository;

    @Override
    public List<Inventario> obtenerTodos() {
        return inventarioRepository.findAll();
    }

    @Override
    public List<Inventario> obtenerProductosConStockBajo(Integer umbral) {
        return inventarioRepository.findByCantidadLessThanEqual(umbral);
    }

    @Override
    public Inventario guardar(Inventario inv) {
        return inventarioRepository.save(inv);
    }
}
