package com.example.dataservice.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProductoCreateUpdateDTO(
        @NotBlank String nombre,
        String descripcion,
        @NotNull @DecimalMin(value = "0.01") BigDecimal precio,
        @NotBlank String categoriaNombre
) {}
