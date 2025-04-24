create database dbpos;
use dbpos;

create table producto(
idproducto int auto_increment not null primary key,
nombre varchar(100),
precioProducto decimal (5,2),
stock int
);
select producto.idproducto, producto.nombre, producto.precioProducto, producto.stock from producto;   
insert producto (nombre,precioProducto,stock)values("Mause","1.5",50);
update producto set producto.nombre="teclado", producto.precioProducto="30000",producto.stock="20" where producto.idproducto=2;
delete from producto where producto.idproducto=2;
/*para que funcione solo por nombre, no importa donde inicia ni donde termina se ve validado por los %*/
select*from producto where producto.nombre like concat('%',"z",'%');


create table cliente(
idcliente int auto_increment not null primary key,
nombres varchar(100),
appaterno varchar(100),
apmaterno varchar(100)
);

select cliente.idcliente,cliente.nombres,cliente.appaterno,cliente.apmaterno from cliente;   
insert into cliente (nombres,appaterno,apmaterno) values ("Miguel","Arias","Marin");
update cliente SET cliente.nombres = "pedro", cliente.appaterno = "morales" , cliente.apmaterno = "lopez" where cliente.idcliente = 1;  
delete from cliente where cliente.idcliente=2;
select*from cliente where cliente.nombres like concat('%',"A",'%');



create table factura(
idfactura int auto_increment not null primary key,
fechaFactura date,
fkcliente int,
foreign key (fkcliente) references cliente(idcliente)
);

INSERT INTO factura(fechaFactura, fkcliente) values (curdate(),5);
select*from factura;

create table detalle(
iddetalle int auto_increment not null primary key,
fkfactura int,
foreign key (fkfactura) references factura(idfactura),
fkproducto int,
foreign key (fkproducto) references producto(idproducto),
cantidad int,
precioVenta decimal(10,0)
);

INSERT INTO detalle (fkfactura, fkproducto, cantidad, precioVenta) values ((SELECT max(idfactura)from factura),?,?,?);

UPDATE producto SET producto.stock = stock - ? WHERE idproducto = ?;

select*from detalle;

SELECT max(idfactura) as ultimaFactura from factura;

SELECT factura.idfactura, factura.fechaFactura, cliente.nombres, cliente.appaterno, cliente.apmaterno from factura
INNER JOIN cliente ON cliente.idcliente = fkcliente WHERE factura.idfactura = 5;

SELECT producto.nombre, detalle.cantidad, detalle.precioVenta from detalle
INNER JOIN factura ON factura.idfactura = detalle.fkfactura
INNER JOIN producto ON producto.idproducto = detalle.fkproducto
where factura.idfactura=19;










