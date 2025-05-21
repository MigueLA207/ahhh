create database dbpos;
use dbpos;

-- Creación de la tabla producto
create table producto(
    idproducto int auto_increment not null primary key,
    nombre varchar(100),
    precioProducto decimal (60,2),
    stock int
);

-- Línea de prueba para ver los productos (puede comentarse)
-- select producto.idproducto, producto.nombre, producto.precioProducto, producto.stock from producto;   

-- Inserción de prueba (puede comentarse)
-- insert producto (nombre,precioProducto,stock)values("Mause","1.5",50);

-- Actualización de prueba (puede comentarse)
-- update producto set producto.nombre="teclado", producto.precioProducto="30000",producto.stock="20" where producto.idproducto=2;

-- Eliminación de prueba (puede comentarse)
-- delete from producto where producto.idproducto=2;

-- Búsqueda con LIKE como ejemplo (puede comentarse)
-- select*from producto where producto.nombre like concat('%',"z",'%');

-- Creación de la tabla cliente
create table cliente(
    idcliente int auto_increment not null primary key,
    nombres varchar(100),
    appaterno varchar(100),
    apmaterno varchar(100)
);

-- Consulta de prueba (puede comentarse)
-- select cliente.idcliente,cliente.nombres,cliente.appaterno,cliente.apmaterno from cliente;   

-- Inserción de prueba (puede comentarse)
-- insert into cliente (nombres,appaterno,apmaterno) values ("Miguel","Arias","Marin");

-- Actualización de prueba (puede comentarse)
-- update cliente SET cliente.nombres = "pedro", cliente.appaterno = "morales" , cliente.apmaterno = "lopez" where cliente.idcliente = 1;  

-- Eliminación de prueba (puede comentarse)
-- delete from cliente where cliente.idcliente=2;

-- Búsqueda con LIKE como ejemplo (puede comentarse)
-- select*from cliente where cliente.nombres like concat('%',"A",'%');

-- Creación de la tabla factura
create table factura(
    idfactura int auto_increment not null primary key,
    fechaFactura date,
    fkcliente int,
    foreign key (fkcliente) references cliente(idcliente)
);

-- Inserción de prueba (puede comentarse)
-- INSERT INTO factura(fechaFactura, fkcliente) values (curdate(),5);

-- Consulta de prueba (puede comentarse)
-- select*from factura;

-- Creación de la tabla detalle
create table detalle(
    iddetalle int auto_increment not null primary key,
    fkfactura int,
    foreign key (fkfactura) references factura(idfactura),
    fkproducto int,
    foreign key (fkproducto) references producto(idproducto),
    cantidad int,
    precioVenta decimal(10,0)
);

-- Inserción de prueba con parámetros (puede comentarse)
-- INSERT INTO detalle (fkfactura, fkproducto, cantidad, precioVenta) values ((SELECT max(idfactura)from factura),?,?,?);

-- Actualización de stock como parte de una venta (puede comentarse si no se está usando aún)
-- UPDATE producto SET producto.stock = stock - ? WHERE idproducto = ?;

-- Consulta de prueba (puede comentarse)
-- select*from detalle;

-- Consulta para obtener la última factura (útil, pero puede comentarse si no se usa en producción directa)
-- SELECT max(idfactura) as ultimaFactura from factura;

-- Consulta detallada de una factura con cliente (puede comentarse si es solo para test)
-- SELECT factura.idfactura, factura.fechaFactura, cliente.nombres, cliente.appaterno, cliente.apmaterno 
-- from factura
-- INNER JOIN cliente ON cliente.idcliente = fkcliente WHERE factura.idfactura = 5;

-- Consulta de productos de una factura específica (puede comentarse si es solo para prueba)
-- SELECT producto.nombre, detalle.cantidad, detalle.precioVenta 
-- from detalle
-- INNER JOIN factura ON factura.idfactura = detalle.fkfactura
-- INNER JOIN producto ON producto.idproducto = detalle.fkproducto
-- where factura.idfactura=19;

-- Consulta con rango de facturas (puede dejarse o comentarse según uso)
-- SELECT factura.idfactura, factura.fechaFactura, producto.nombre, detalle.cantidad, detalle.precioVenta 
-- from detalle
-- INNER JOIN factura ON factura.idfactura = detalle.fkfactura
-- INNER JOIN producto ON producto.idproducto = detalle.fkproducto
-- where factura.idfactura between ? AND ?;

-- Consulta general (puede comentarse si es solo para revisión)
-- select * from factura;











