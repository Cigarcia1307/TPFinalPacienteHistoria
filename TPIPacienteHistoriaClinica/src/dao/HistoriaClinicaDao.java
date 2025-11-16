package dao;

import entities.HistoriaClinica;
import entities.GrupoSanguineo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoriaClinicaDao implements GenericDao<HistoriaClinica> {

    // Método "extra" para crear historia clínica asociada a un paciente
    // un Paciente tiene una Historia Clínica, pero una Historia Clínica no conoce al Paciente en el modelo Java.
    // HistoriaClinica "NO" tiene atributo paciente. Esto es UNIDIRECCIONALIDAD.
    // Pero en la base de datos, la tabla historia_clinica sí necesita guardar: paciente_id
    // El DAO recibe el pacienteId por parámetro externo, proveniente siempre desde PacienteDao

    public void crear(HistoriaClinica hc, Connection conn, long pacienteId) throws SQLException {
        String sql = "INSERT INTO historia_clinica "
                   + "(id, nroHistoria, grupoSanguineo, antecedentes, medicacionActual, observaciones, eliminado, paciente_id) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, hc.getId());
            ps.setString(2, hc.getNroHistoria());
            ps.setString(3, hc.getGrupoSanguineo().toString());
            ps.setString(4, hc.getAntecedentes());
            ps.setString(5, hc.getMedicacionActual());
            ps.setString(6, hc.getObservaciones());
            ps.setBoolean(7, hc.getEliminado());
            ps.setLong(8, pacienteId); // pacienteId se pasa desde PacienteDao
            ps.executeUpdate();
        }
    }

    @Override
    public void crear(HistoriaClinica t, Connection conn) throws SQLException {
        // Se lanza una excepción a propósito para impedir el uso incorrecto.
        throw new UnsupportedOperationException(
            "Use crear(HistoriaClinica hc, Connection conn, long pacienteId) para insertar");
    }

    @Override
    public HistoriaClinica leer(long id, Connection conn) throws SQLException {
        String sql = "SELECT * FROM historia_clinica WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new HistoriaClinica(
                        rs.getLong("id"),
                        rs.getString("nroHistoria"),
                        rs.getString("antecedentes"),
                        rs.getString("medicacionActual"),
                        rs.getString("observaciones"),
                        rs.getBoolean("eliminado"),
                        GrupoSanguineo.valueOf(
                            rs.getString("grupoSanguineo")
                              .replace("+", "_POSITIVO")
                              .replace("-", "_NEGATIVO")
                        )
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<HistoriaClinica> leerTodos(Connection conn) throws SQLException {
        List<HistoriaClinica> lista = new ArrayList<>();
        String sql = "SELECT * FROM historia_clinica";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new HistoriaClinica(
                    rs.getLong("id"),
                    rs.getString("nroHistoria"),
                    rs.getString("antecedentes"),
                    rs.getString("medicacionActual"),
                    rs.getString("observaciones"),
                    rs.getBoolean("eliminado"),
                    GrupoSanguineo.valueOf(
                        rs.getString("grupoSanguineo")
                          .replace("+", "_POSITIVO")
                          .replace("-", "_NEGATIVO")
                    )
                ));
            }
        }
        return lista;
    }

    @Override
    public void actualizar(HistoriaClinica hc, Connection conn) throws SQLException {
        String sql = "UPDATE historia_clinica SET "
                   + "nroHistoria=?, grupoSanguineo=?, antecedentes=?, medicacionActual=?, observaciones=?, eliminado=? "
                   + "WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, hc.getNroHistoria());
            ps.setString(2, hc.getGrupoSanguineo().toString());
            ps.setString(3, hc.getAntecedentes());
            ps.setString(4, hc.getMedicacionActual());
            ps.setString(5, hc.getObservaciones());
            ps.setBoolean(6, hc.getEliminado());
            ps.setLong(7, hc.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void eliminar(long id, Connection conn) throws SQLException {
        String sql = "UPDATE historia_clinica SET eliminado = TRUE WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }
    
    
    public HistoriaClinica leerPorPaciente(long pacienteId, Connection conn) throws SQLException {
    String sql = "SELECT * FROM historia_clinica WHERE paciente_id = ? AND eliminado = FALSE";

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setLong(1, pacienteId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new HistoriaClinica(
                        rs.getLong("id"),
                        rs.getString("nroHistoria"),
                        rs.getString("antecedentes"),
                        rs.getString("medicacionActual"),
                        rs.getString("observaciones"),
                        rs.getBoolean("eliminado"),
                        GrupoSanguineo.valueOf(
                                rs.getString("grupoSanguineo")
                                        .replace("+", "_POSITIVO")
                                        .replace("-", "_NEGATIVO")
                        )
                );
            }
        }
    }
    return null;
}
    
    
    public void eliminarPorPaciente(long pacienteId, Connection conn) throws SQLException {
    String sql = "UPDATE historia_clinica SET eliminado = TRUE WHERE paciente_id = ?";

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setLong(1, pacienteId);
        ps.executeUpdate();
    }
}
}