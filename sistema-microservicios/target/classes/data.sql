-- Categorías (sin id)
INSERT INTO categorias (nombre, descripcion) VALUES
                                                 ('Electrónica', 'Dispositivos y gadgets'),
                                                 ('Hogar', 'Artículos para el hogar');

-- Productos (sin id) usando ids de categorías ya conocidas
INSERT INTO productos (nombre, descripcion, precio, categoria_id) VALUES
                                                                      ('Auriculares', 'Over-ear con cancelación de ruido', 199.99, 1),
                                                                      ('Lámpara LED', 'Luz cálida de escritorio',        29.90,  2);

-- Inventario (sin id) referenciando producto_id existentes
INSERT INTO inventario (producto_id, cantidad, stock_minimo, fecha_actualizacion) VALUES
                                                                                      (1, 3,  5, CURRENT_TIMESTAMP),
                                                                                      (2, 12, 5, CURRENT_TIMESTAMP);
