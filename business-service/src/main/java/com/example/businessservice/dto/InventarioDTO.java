package com.example.businessservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InventarioDTO {
    private Long id;
    private Long productoId;
    private Integer cantidad;
    private Integer stockMinimo;
    private LocalDateTime fechaActualizacion;
}
