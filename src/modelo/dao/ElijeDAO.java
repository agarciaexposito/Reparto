package modelo.dao;

import java.sql.SQLException;
import java.util.List;

public interface ElijeDAO {
    List<Elije> obtenerTodos() throws SQLException;
    Elije obtenerPorId(int id) throws SQLException;
    void insertar(Elije elije) throws SQLException;
    void actualizar(Elije elije) throws SQLException;
    void eliminar(int id) throws SQLException;
}
