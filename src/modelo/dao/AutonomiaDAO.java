package modelo.dao;

import java.sql.SQLException;
import java.util.List;

public interface AutonomiaDAO {
    void insertar(Autonomia autonomia) throws SQLException;
    List<Autonomia> obtenerTodos() throws SQLException;
    int obtenerId(String nombre) throws SQLException;
}
