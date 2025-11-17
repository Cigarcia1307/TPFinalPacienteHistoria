package dao;

import entities.Paciente;
import entities.HistoriaClinica;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDao implements GenericDao<Paciente> {

    private final HistoriaClinicaDao historiaClinicaDao = new HistoriaClinicaDao();

    @Override
    public void crear(Paciente p, Connection conn) throws SQLException {
        // 1. Insertar el Paciente
        String sql = "INSERT INTO paciente (nombre, apellido, dni, fecha_nacimiento, eliminado) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getApellido());
            ps.setString(3, p.getDni());
            ps.setDate(4, java.sql.Date.valueOf(p.getFechaNacimiento()));
            ps.setBoolean(5, p.getEliminado());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    // Obtener el ID generado por la base de datos
                    p.setId(rs.getLong(1));
                }
            }
        }

        // 2. Manejar la Historia Clínica (Lógica C/U: Crear o Actualizar)
        HistoriaClinica hc = p.getHistoriaClinica();
        if (hc != null) {
            long pacienteId = p.getId();
            
            // A. Verificar si la Historia Clínica ya existe para este paciente
            HistoriaClinica hcExistente = historiaClinicaDao.leerPorPaciente(pacienteId, conn);

            if (hcExistente != null) {
                // B. Si EXISTE (el paciente 11 ya tiene una ficha), actualiza la ficha existente.
                // Usamos el ID de la ficha existente para el UPDATE.
                hc.setId(hcExistente.getId()); 
                historiaClinicaDao.actualizar(hc, conn);
            } else {
                // C. Si NO EXISTE, la crea (INSERT).
                historiaClinicaDao.crear(hc, conn, pacienteId);
            }
        }
    }


    @Override
    public Paciente leer(long id, Connection conn) throws SQLException {
        String sql = "SELECT * FROM paciente WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Paciente(
                        rs.getLong("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("dni"),
                        rs.getDate("fecha_nacimiento").toLocalDate(),
                        rs.getBoolean("eliminado"),
                        null // dejamos historiaClinica null; 
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<Paciente> leerTodos(Connection conn) throws SQLException {
        List<Paciente> lista = new ArrayList<>();
        String sql = "SELECT * FROM paciente";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Paciente(
                    rs.getLong("id"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("dni"),
                    rs.getDate("fecha_nacimiento").toLocalDate(),
                    rs.getBoolean("eliminado"),
                    null
                ));
            }
        }
        return lista;
    }

    @Override
    public void actualizar(Paciente p, Connection conn) throws SQLException {
        String sql = "UPDATE paciente SET nombre=?, apellido=?, dni=?, fecha_nacimiento=?, eliminado=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getApellido());
            ps.setString(3, p.getDni());
            ps.setDate(4, java.sql.Date.valueOf(p.getFechaNacimiento()));
            ps.setBoolean(5, p.getEliminado());
            ps.setLong(6, p.getId());
            ps.executeUpdate();
        }
        
    }

    @Override
    public void eliminar(long id, Connection conn) throws SQLException {
        String sql = "UPDATE paciente SET eliminado = TRUE WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
        
    }
    
    // Devuelve un Paciente si existe un paciente con el DNI dado (metodo adicional!)
    public Paciente leerPorDni(String dni, Connection conn) throws SQLException {
        String sql = "SELECT * FROM paciente WHERE dni = ? AND eliminado = FALSE";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Paciente(
                        rs.getLong("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("dni"),
                        rs.getDate("fecha_nacimiento").toLocalDate(),
                        rs.getBoolean("eliminado"),
                        null
                    );
                }
            }
        }
        return null;
    }
}