/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;
/**
 *
 * @author daian
 */

    import config.DatabaseConnection;
    import dao.HistoriaClinicaDao;
    import entities.HistoriaClinica;

    import java.sql.Connection;
    import java.util.List;

    public class HistoriaClinicaService implements GenericService<HistoriaClinica> {

    private final HistoriaClinicaDao dao;

    public HistoriaClinicaService(HistoriaClinicaDao dao) {
        this.dao = dao;
    }

    @Override
    public void insertar(HistoriaClinica hc) throws Exception {
        throw new UnsupportedOperationException(
                "La Historia Clínica se crea desde PacienteService"
        );
    }

    @Override
    public HistoriaClinica getById(long id) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return dao.leer(id, conn);
        }
    }

    @Override
    public List<HistoriaClinica> getAll() throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return dao.leerTodos(conn);
        }
    }

    @Override
    public void actualizar(HistoriaClinica hc) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            dao.actualizar(hc, conn);
        }
    }

    @Override
    public void eliminar(long id) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            dao.eliminar(id, conn);
        }
    }
}
    

