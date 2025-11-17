package dao;

import entities.HistoriaClinica;
import entities.GrupoSanguineo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoriaClinicaDao implements GenericDao<HistoriaClinica> {

    // Crear historia clínica vinculada a paciente
    public void crear(HistoriaClinica hc, Connection conn, long pacienteId) throws SQLException {
        // Seteamos nro_historia provisional para evitar error NOT NULL
        hc.setNroHistoria("PENDIENTE");

        String sql = "INSERT INTO historia_clinica "
                   + "(nro_historia, grupo_sanguineo_id, antecedentes, medicacion_actual, observaciones, eliminado, paciente_id) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, hc.getNroHistoria());
            ps.setLong(2, hc.getGrupoSanguineo().ordinal() + 1);
            ps.setString(3, hc.getAntecedentes());
            ps.setString(4, hc.getMedicacionActual());
            ps.setString(5, hc.getObservaciones());
            ps.setBoolean(6, hc.getEliminado());
            ps.setLong(7, pacienteId);
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    long idGenerado = rs.getLong(1);
                    hc.setId(idGenerado);

                    // Generamos nro_historia final basado en id
                    String nroHistoria = "HC" + idGenerado;
                    hc.setNroHistoria(nroHistoria);

                    String updateSql = "UPDATE historia_clinica SET nro_historia = ? WHERE id = ?";
                    try (PreparedStatement psUpdate = conn.prepareStatement(updateSql)) {
                        psUpdate.setString(1, nroHistoria);
                        psUpdate.setLong(2, idGenerado);
                        psUpdate.executeUpdate();
                    }
                }
            }
        }
    }

    @Override
    public void crear(HistoriaClinica t, Connection conn) throws SQLException {
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
                    return mapResultSetToHistoria(rs);
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
                lista.add(mapResultSetToHistoria(rs));
            }
        }
        return lista;
    }

    @Override
    public void actualizar(HistoriaClinica hc, Connection conn) throws SQLException {
        String sql = "UPDATE historia_clinica SET "
                   + "nro_historia=?, grupo_sanguineo_id=?, antecedentes=?, medicacion_actual=?, observaciones=?, eliminado=? "
                   + "WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, hc.getNroHistoria());
            ps.setLong(2, hc.getGrupoSanguineo().ordinal() + 1);
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
                    return mapResultSetToHistoria(rs);
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

    private HistoriaClinica mapResultSetToHistoria(ResultSet rs) throws SQLException {
        long gsId = rs.getLong("grupo_sanguineo_id");
        GrupoSanguineo gs = GrupoSanguineo.values()[(int) gsId - 1]; // IDs 1-8

        return new HistoriaClinica(
                rs.getLong("id"),
                rs.getString("nro_historia"),
                rs.getString("antecedentes"),
                rs.getString("medicacion_actual"),
                rs.getString("observaciones"),
                rs.getBoolean("eliminado"),
                gs
        );
    }

}
