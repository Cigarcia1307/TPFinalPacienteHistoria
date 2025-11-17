package Service;

import config.DatabaseConnection;
import dao.PacienteDao;
import dao.HistoriaClinicaDao;
import entities.Paciente;
import entities.HistoriaClinica;

import java.sql.Connection;
import java.util.List;

public class PacienteService implements GenericService<Paciente> {

    private final PacienteDao pacienteDao;
    private final HistoriaClinicaDao historiaDao;

    public PacienteService(PacienteDao pacienteDao, HistoriaClinicaDao historiaDao) {
        this.pacienteDao = pacienteDao;
        this.historiaDao = historiaDao;
    }
    
    
    private void validarPaciente(Paciente p) throws Exception {
        if (p.getNombre() == null || p.getNombre().isBlank()) 
            throw new Exception("Nombre obligatorio");
        if (p.getApellido() == null || p.getApellido().isBlank()) 
            throw new Exception("Apellido obligatorio");
        if (p.getDni() == null || p.getDni().isBlank()) 
            throw new Exception("El DNI es obligatorio");

        // Validar unicidad de DNI
        try (Connection conn = DatabaseConnection.getConnection()) {
            Paciente existente = pacienteDao.leerPorDni(p.getDni(), conn);
            if (existente != null && !existente.getId().equals(p.getId())) {
                throw new Exception("Ya existe un paciente con este DNI");
            }
        }
    }
    
    
    // Insertar (Paciente + HistoriaClinica en transacción)
 
    // Archivo: PacienteService.java

@Override
public void insertar(Paciente paciente) throws Exception {

    if (paciente == null)
        throw new IllegalArgumentException("Paciente no puede ser null");

    if (paciente.getHistoriaClinica() == null)
        throw new IllegalArgumentException("Debe incluir una Historia Clínica");

    validarPaciente(paciente);

    Connection conn = DatabaseConnection.getConnection();
    conn.setAutoCommit(false); // inicia transacción

    try {
        // 1) Crear PACIENTE (ESTA ÚNICA LLAMADA YA INCLUYE LA LÓGICA DE CREAR/ACTUALIZAR LA FICHA)
        pacienteDao.crear(paciente, conn);

        // ¡ELIMINAMOS EL BLOQUE DE CÓDIGO QUE LLAMABA DIRECTAMENTE A historiaDao.crear()!
        // Ese código era redundante y causaba el segundo INSERT, generando el error.

        conn.commit();

    } catch (Exception ex) {
        conn.rollback();
        throw ex;

    } finally {
        conn.setAutoCommit(true);
        conn.close();
    }
}


    // Get by Id
    
    @Override
    public Paciente getById(long id) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {

            Paciente p = pacienteDao.leer(id, conn);

            if (p != null) {
                HistoriaClinica hc = historiaDao.leerPorPaciente(id, conn);
                p.setHistoriaClinica(hc);
            }

            return p;
        }
    }

    
    // Get by DNI
    public Paciente getByDni(String dni) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            Paciente p = pacienteDao.leerPorDni(dni, conn);
            if (p != null) {
                HistoriaClinica hc = historiaDao.leerPorPaciente(p.getId(), conn);
                p.setHistoriaClinica(hc);
            }
            return p;
        }
    }
    
    // Get all
 
    @Override
    public List<Paciente> getAll() throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {

            List<Paciente> lista = pacienteDao.leerTodos(conn);

            for (Paciente p : lista) {
                HistoriaClinica hc = historiaDao.leerPorPaciente(p.getId(), conn);
                p.setHistoriaClinica(hc);
            }

            return lista;
        }
    }

    // Actualizar (Paciente + HistoriaClinica)

    @Override
    public void actualizar(Paciente paciente) throws Exception {

        if (paciente == null)
            throw new IllegalArgumentException("Paciente no puede ser null");

        validarPaciente(paciente);

        Connection conn = DatabaseConnection.getConnection();
        conn.setAutoCommit(false);

        try {
            // actualizar paciente
            pacienteDao.actualizar(paciente, conn);

            // actualizar história clínica si existe
            if (paciente.getHistoriaClinica() != null) {
                historiaDao.actualizar(paciente.getHistoriaClinica(), conn);
            }

            conn.commit();

        } catch (Exception e) {
            conn.rollback();
            throw e;

        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }


    // Eliminar (baja lógica)
    
    @Override
    public void eliminar(long id) throws Exception {

        Connection conn = DatabaseConnection.getConnection();
        conn.setAutoCommit(false);

        try {
            // baja lógica del paciente
            pacienteDao.eliminar(id, conn);

            // baja lógica de su historia clínica
            historiaDao.eliminarPorPaciente(id, conn);

            conn.commit();

        } catch (Exception e) {
            conn.rollback();
            throw e;

        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }

}

