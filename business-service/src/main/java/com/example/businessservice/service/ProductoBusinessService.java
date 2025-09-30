package com.example.businessservice.service;

import com.example.businessservice.client.DataServiceClient;
import com.example.businessservice.client.model.DataProducto;
import com.example.businessservice.dto.ProductoDTO;
import com.example.businessservice.dto.ProductoRequest;
import com.example.businessservice.exception.MicroserviceCommunicationException;
import com.example.businessservice.exception.ProductoNoEncontradoException;
import com.example.businessservice.exception.ValidacionNegocioException;
import feign.FeignException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoBusinessService {

    private final DataServiceClient dataServiceClient;

    public ProductoBusinessService(DataServiceClient dataServiceClient) {
        this.dataServiceClient = dataServiceClient;
    }

    public List<ProductoDTO> obtenerTodosLosProductos() {
        try {
            return dataServiceClient.obtenerTodosLosProductos()
                    .stream().map(this::toDTO).toList();
        } catch (FeignException e) {
            throw new MicroserviceCommunicationException("Error de comunicación con el servicio de datos");
        }
    }

    public ProductoDTO obtenerProductoPorId(Long id) {
        try {
            DataProducto p = dataServiceClient.obtenerProductoPorId(id);
            if (p == null) throw new ProductoNoEncontradoException("Producto no encontrado: " + id);
            return toDTO(p);
        } catch (FeignException.NotFound e) {
            throw new ProductoNoEncontradoException("Producto no encontrado: " + id);
        } catch (FeignException e) {
            throw new MicroserviceCommunicationException("Error de comunicación con el servicio de datos");
        }
    }

    public ProductoDTO crearProducto(ProductoRequest request) {
        validarProducto(request);
        try {
            DataProducto creado = dataServiceClient.crearProducto(request);
            return toDTO(creado);
        } catch (FeignException e) {
            throw new MicroserviceCommunicationException("Error de comunicación con el servicio de datos");
        }
    }

    public List<ProductoDTO> obtenerPorCategoria(String nombre) {
        try {
            return dataServiceClient.obtenerProductosPorCategoria(nombre)
                    .stream().map(this::toDTO).toList();
        } catch (FeignException e) {
            throw new MicroserviceCommunicationException("Error de comunicación con el servicio de datos");
        }
    }

    private void validarProducto(ProductoRequest request) {
        if (request.getPrecio() == null || request.getPrecio().signum() <= 0) {
            throw new ValidacionNegocioException("El precio debe ser mayor a cero");
        }
        if (request.getStock() == null || request.getStock() < 0) {
            throw new ValidacionNegocioException("El stock no puede ser negativo");
        }
    }

    private ProductoDTO toDTO(DataProducto p) {
        Integer cantidad = (p.getInventario() != null) ? p.getInventario().getCantidad() : null;
        Integer min = (p.getInventario() != null) ? p.getInventario().getStockMinimo() : null;
        boolean stockBajo = (cantidad != null && min != null) && cantidad <= min;

        return new ProductoDTO(
                p.getId(),
                p.getNombre(),
                p.getDescripcion(),
                p.getPrecio(),
                p.getCategoria() != null ? p.getCategoria().getNombre() : null,
                cantidad,
                stockBajo
        );
    }
}
