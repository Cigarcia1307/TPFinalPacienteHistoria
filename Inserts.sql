
--Datos de prueba:
--Inserccion en tabla paciente
INSERT INTO paciente (id, nombre, apellido, dni, fechaNacimiento, eliminado)
VALUES 
(1, 'Daniel', 'Gomez', '23564543', '1974-04-05', FALSE),
(2, 'Ana', 'Martinez', '32455678', '1985-08-12', FALSE),
(3, 'Carlos', 'Lopez', '41234567', '1990-01-20', FALSE),
(4, 'Lucia', 'Fernandez', '29876543', '1978-11-03', FALSE),
(5, 'Jorge', 'Ramirez', '36789012', '1982-06-15', FALSE),
(6, 'Sofia', 'Diaz', '44556677', '1995-03-22', FALSE);

--Inserccion en tabla historia_clinica
INSERT INTO historia_clinica (id, nroHistoria, grupoSanguineo, antecedentes, medicacionActual, observaciones, eliminado, paciente_id)
VALUES
(101, 'HC000', 'A+', 'Covid', 'Paracetamol', 'Problemas respiratorios', FALSE, 1),
(102, 'HC001', 'O-', 'Hipertensión', 'Enalapril', 'Revisión mensual', FALSE, 2),
(103, 'HC002', 'B+', 'Asma', 'Salbutamol', 'Evitar ejercicio intenso', FALSE, 3),
(104, 'HC003', 'AB-', 'Diabetes', 'Insulina', 'Control de glucosa diario', FALSE, 4),
(105, 'HC004', 'A-', 'Alergia a penicilina', 'Antihistamínicos', 'Evitar penicilina', FALSE, 5),
(106, 'HC005', 'O+', 'Fractura previa de brazo', 'Ninguna', 'Rehabilitación completada', FALSE, 6);