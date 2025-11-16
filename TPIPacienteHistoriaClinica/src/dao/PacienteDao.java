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
        String sql = "INSERT INTO paciente (id, nombre, apellido, dni, fechaNacimiento, eliminado) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, p.getId());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getApellido());
            ps.setString(4, p.getDni());
            ps.setDate(5, java.sql.Date.valueOf(p.getFechaNacimiento()));
            ps.setBoolean(6, p.getEliminado());
            ps.executeUpdate();
        }

        // Si tiene historia clínica asociada, la insertamos vinculándola al paciente
        HistoriaClinica hc = p.getHistoriaClinica();
        if (hc != null) {
            historiaClinicaDao.crear(hc, conn, p.getId());
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
                        rs.getDate("fechaNacimiento").toLocalDate(),
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
                    rs.getDate("fechaNacimiento").toLocalDate(),
                    rs.getBoolean("eliminado"),
                    null
                ));
            }
        }
        return lista;
    }

    @Override
    public void actualizar(Paciente p, Connection conn) throws SQLException {
        String sql = "UPDATE paciente SET nombre=?, apellido=?, dni=?, fechaNacimiento=?, eliminado=? WHERE id=?";
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
                        rs.getDate("fechaNacimiento").toLocalDate(),
                        rs.getBoolean("eliminado"),
                        null
                    );
                }
            }
        }
        return null;
    }
}