package modelo.dao;

import java.sql.SQLException;
import java.util.List;

public interface EspecialidadDAO {
    List<Especialidad> obtenerTodos() throws SQLException;
    Especialidad obtenerPorId(int id) throws SQLException;
    void insertar(Especialidad especialidad) throws SQLException;
    void actualizar(Especialidad especialidad) throws SQLException;
    void eliminar(int id) throws SQLException;
}
