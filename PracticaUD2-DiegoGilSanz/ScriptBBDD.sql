CREATE DATABASE if not exists Gimnasio;
--
USE Gimnasio;
--
CREATE TABLE IF NOT EXISTS entrenador (
idEntrenador int auto_increment primary key,
nombre varchar(50) not null,
apellidos varchar(150) not null,
fechainicio date,
pais varchar(50));
--
CREATE TABLE IF NOT EXISTS liga (
idLiga int auto_increment primary key,
nombre varchar(50)  not null,
descripcion varchar(100) not null,
participantes varchar(9),
tipoliga varchar(50),
web varchar(500));
--
CREATE TABLE IF NOT EXISTS peleador (
idPeleador int auto_increment primary key,
nombre varchar(50) not null,
estilo varchar(40) not null ,
idLiga int not null,
idEntrenador int not null,
genero varchar(30),
peso float not null,
fechanacimiento date);
--
alter table peleador
add foreign key (idLiga) references liga(idLiga),
add foreign key (idEntrenador) references entrenador(idEntrenador);
--
CREATE FUNCTION IF NOT EXISTS existeNombreEntrenador(f_name VARCHAR(50))
RETURNS BIT
BEGIN
    RETURN EXISTS (
        SELECT 1 
        FROM entrenador 
        WHERE nombre = f_name
    );
END;

-- 
CREATE FUNCTION IF NOT EXISTS existeNombreLiga(f_name VARCHAR(50))
RETURNS BIT
BEGIN
    RETURN EXISTS (
        SELECT 1 
        FROM liga 
        WHERE nombre = f_name
    );
END;

-- 
CREATE FUNCTION IF NOT EXISTS existeNombrePeleador(f_name VARCHAR(50))
RETURNS BIT
BEGIN
    RETURN EXISTS (
        SELECT 1 
        FROM peleador 
        WHERE nombre = f_name
    );
END;

