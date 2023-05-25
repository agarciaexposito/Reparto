package modelo.dao;

import java.sql.SQLException;
import java.util.List;

public interface AspiranteDAO {
    List<Aspirante> obtenerTodos() throws SQLException;
    Aspirante obtenerPorId(int id) throws SQLException;
    Aspirante obtenerPorDniNombre(String dni, String nombre) throws SQLException;
    void insertar(Aspirante aspirante) throws SQLException;
    void actualizar(Aspirante aspirante) throws SQLException;
    void eliminar(int id) throws SQLException;
}