package com.example.businessservice.client.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DataProducto {
    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private DataCategoria categoria;
    private DataInventario inventario;
}
