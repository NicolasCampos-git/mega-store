-- Insertar marcas
INSERT INTO marcas (nombre, descripcion, esta_activo) VALUES 
('Nike', 'Marca deportiva internacional', TRUE),
('Adidas', 'Marca global de ropa deportiva', TRUE),
('Puma', 'Marca de calzado y ropa deportiva', TRUE);

-- Insertar categorías
INSERT INTO categorias (nombre, descripcion, esta_activo) VALUES
('Ropa', 'Categoría de ropa deportiva y casual', TRUE),
('Calzado', 'Categoría de calzado deportivo y urbano', TRUE),
('Accesorios', 'Accesorios deportivos y de moda', TRUE);

-- Insertar subcategorías
INSERT INTO sub_categorias (nombre, descripcion, categoria_id, esta_activo) VALUES
('Camisetas', 'Camisetas deportivas y casuales', 1, TRUE),
('Pantalones', 'Pantalones deportivos y casuales', 1, TRUE),
('Zapatillas', 'Zapatillas para deporte y uso diario', 2, TRUE),
('Gorras', 'Gorras deportivas y de moda', 3, TRUE);

-- Insertar productos
INSERT INTO productos (esta_activo, precio_unitario, stock, umbral_bajo_stock, categoria_id, marca_id, tamano, color, nombre, descripcion) VALUES
(TRUE, 120.00, 100, 10, 1, 1, 'M', 'Blanco', 'Camiseta Nike Dri-FIT', 'Camiseta deportiva transpirable para entrenamientos'),
(TRUE, 80.00, 150, 15, 1, 2, 'L', 'Negro', 'Pantalones Adidas Originals', 'Pantalones clásicos con el logo de Adidas'),
(TRUE, 200.00, 75, 8, 2, 3, '42', 'Rojo', 'Zapatillas Puma Suede Classic', 'Zapatillas icónicas de estilo urbano y deportivo'),
(TRUE, 25.00, 200, 20, 3, 1, 'Única', 'Azul', 'Gorra Nike Heritage86', 'Gorra casual de uso diario con el logo Nike');

-- Insertar usuarios
INSERT INTO usuarios (id, nombre, apellido, email, contrasena, telefono, rol) VALUES
(1, 'Juan', 'Prez', 'juan.perez@example.com', 'contrasenaSegura1', '1122334455', 'ADMIN'),
(2, 'Maria', 'Gomez', 'maria.gomez@example.com', 'contrasenaSegura2', '1166778899', 'USER'),
(3, 'Carlos', 'Lopez', 'carlos.lopez@example.com', 'contrasenaSegura3', '1100223344', 'USER');

-- Insertar direcciones de envío
INSERT INTO direcciones_envio (provincia, ciudad, calle, altura, codigo_postal, descripcion_direccion_envio, usuario_id, es_principal) VALUES
('Buenos Aires', 'CABA', 'Calle Falsa', 1234, '1000', 'Dirección principal', 1, TRUE),
('Córdoba', 'Córdoba Capital', 'Avenida Siempreviva', 742, '5000', 'Dirección secundaria', 1, FALSE),
('Mendoza', 'Mendoza', 'Calle de Prueba', 111, '5500', 'Dirección terciaria', 2, TRUE);
