package modelo.dao;

import java.sql.SQLException;
import java.util.List;

public interface ParticipaDAO {
    List<Participa> obtenerTodos() throws SQLException;
    Participa obtenerPorId(int id) throws SQLException;
    void insertar(Participa participa) throws SQLException;
    void actualizar(Participa participa) throws SQLException;
    void eliminar(int id) throws SQLException;
}