package com.example.businessservice.client.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DataInventario {
    private Long id;
    private DataProducto producto; // puede venir null o con id
    private Integer cantidad;
    private Integer stockMinimo;
    private LocalDateTime fechaActualizacion;
}
