
Descripción del dominio

El dominio elegido corresponde al área de salud, modelando la relación entre un Paciente y su Historia Clínica.
Cada paciente posee exactamente una historia clínica, y la historia clínica existe únicamente asociada a ese paciente. Esta relación se modela como 1→1 unidireccional, donde Paciente referencia a HistoriaClinica.

El sistema permite:

Registrar pacientes y sus historias clínicas.

Consultar, listar, modificar y eliminar lógicamente los registros.

Mantener la integridad entre paciente e historia clínica mediante reglas de negocio y transacciones.

Persistencia completa con JDBC + DAO + MySQL.
----------------------------------------------------------------------------------------------------------------------------------------------------------------------
Requisitos
Requisitos de software

Java 17 o superior

MySQL 8.x

JDBC Driver MySQL (Connector/J)

NetBeans, IntelliJ o cualquier IDE compatible con Maven/Ant

MySQL Workbench / consola MySQL
------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Pasos para crear la base de datos

En el repositorio se incluyen dos archivos:

01_estructura.sql → crea la base de datos, tablas y claves

02_datos_prueba.sql → inserta datos iniciales

Creación de la base

Abrir MySQL Workbench.

Ejecutar el contenido de:

01_estructura.sql


Incluye:

CREATE DATABASE BDPacienteHistoriaClinica

Tablas: paciente, historia_clinica, grupo_sanguineo

Relación 1→1 implementada con:

paciente_id en historia_clinica como UNIQUE

FOREIGN KEY ON DELETE CASCADE

Insertar datos de prueba

Luego ejecutar:

02_datos_prueba.sql


Esto cargará:

Los grupos sanguíneos del enum

Pacientes de ejemplo

Historias clínicas temporales
-------------------------------------------------------------------------------------------------------------------------------------------------------------
ómo compilar y ejecutar
1. Configurar credenciales de MySQL

En config/DatabaseConnection.java modificar:

private static final String USER = "root";
private static final String PASSWORD = "root";
private static final String URL = "jdbc:mysql://localhost:3306/bdpacientehistoriaclinica"

2. Compilar

Desde NetBeans:

Run → Clean and Build Project

Desde consola (si es Maven):

mvn clean install

3. Ejecutar

Ejecutar la clase:

main.AppMenu


Esto abrirá el menú de consola que permite:

Flujo de uso recomendado

Crear un paciente.

Crear la historia clínica para ese paciente (validación 1→1 incluida).

Consultar por ID o listar todos.

Actualizar datos de paciente o historia clínica.

Eliminar lógicamente un registro.

Todas las operaciones compuestas (crear paciente + historia clínica) se manejan en servicios transaccionales con commit/rollback.
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Credenciales de prueba

Usuario MySQL: root

Contraseña recomendada (solo de ejemplo): root
