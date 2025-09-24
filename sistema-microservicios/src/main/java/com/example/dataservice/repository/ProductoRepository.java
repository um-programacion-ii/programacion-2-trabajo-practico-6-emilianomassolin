package com.example.dataservice.repository;

import com.example.dataservice.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    @Query("SELECT p FROM Producto p JOIN p.categoria c WHERE LOWER(c.nombre) = LOWER(:nombre)")
    List<Producto> findByCategoriaNombre(@Param("nombre") String nombre);
}
