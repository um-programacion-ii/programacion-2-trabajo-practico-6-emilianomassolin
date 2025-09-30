package com.example.businessservice.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductoRequest {
    @NotBlank
    private String nombre;

    @Size(max = 500)
    private String descripcion;

    @NotNull @DecimalMin(value = "0.01")
    private BigDecimal precio;

    @NotBlank
    private String categoriaNombre;

    @NotNull @Min(0)
    private Integer stock; // cantidad inicial para inventario
}
