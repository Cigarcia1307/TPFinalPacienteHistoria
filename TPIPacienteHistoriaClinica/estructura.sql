-- Crear la base de datos
CREATE DATABASE BDPacienteHistoriaClinica;
USE BDPacienteHistoriaClinica;

-- Tabla grupo_sanguineo (según enum en Java)
CREATE TABLE grupo_sanguineo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(5) NOT NULL UNIQUE
);

-- Tabla paciente
CREATE TABLE paciente (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    dni VARCHAR(20) NOT NULL UNIQUE,
    fecha_nacimiento DATE NOT NULL,
    eliminado BOOLEAN DEFAULT FALSE
);

-- Tabla historia_clinica (relación 1→1 con paciente y grupo sanguíneo)
CREATE TABLE historia_clinica (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nro_historia VARCHAR(30) NOT NULL UNIQUE,
    antecedentes TEXT,
    medicacion_actual TEXT,
    observaciones TEXT,
    eliminado BOOLEAN DEFAULT FALSE,
    paciente_id BIGINT NOT NULL UNIQUE,
    grupo_sanguineo_id BIGINT,
    FOREIGN KEY (paciente_id) REFERENCES paciente(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (grupo_sanguineo_id) REFERENCES grupo_sanguineo(id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
);

-- Índices adicionales
CREATE INDEX idx_paciente_dni ON paciente(dni);
CREATE INDEX idx_historia_nro ON historia_clinica(nro_historia);


