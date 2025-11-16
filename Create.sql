USE BDPacienteHistoriaClinica;

CREATE TABLE paciente (
    id BIGINT PRIMARY KEY,
    nombre VARCHAR(80) NOT NULL,
    apellido VARCHAR(80) NOT NULL,
    dni VARCHAR(15) NOT NULL UNIQUE,
    fechaNacimiento DATE,
    eliminado BOOLEAN DEFAULT FALSE
);


CREATE TABLE historia_clinica (
    id BIGINT PRIMARY KEY,
    nroHistoria VARCHAR(20) NOT NULL UNIQUE,
    grupoSanguineo ENUM('A+','A-','B+','B-','AB+','AB-','O+','O-'),
    antecedentes TEXT,
    medicacionActual TEXT,
    observaciones TEXT,
    eliminado BOOLEAN DEFAULT FALSE,
    paciente_id BIGINT UNIQUE,
    FOREIGN KEY (paciente_id) REFERENCES paciente(id) ON DELETE CASCADE
);