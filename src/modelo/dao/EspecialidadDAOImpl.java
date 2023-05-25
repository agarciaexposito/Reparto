package modelo.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EspecialidadDAOImpl implements EspecialidadDAO {
    private Connection connection;

    public EspecialidadDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Especialidad> obtenerTodos() throws SQLException {
        List<Especialidad> especialidades = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Especialidad");
        while (rs.next()) {
            int id = rs.getInt("id");
            String nombre = rs.getString("nombre");
            int cuerpo = rs.getInt("cuerpo");
            int especialidad = rs.getInt("especialidad");

            Especialidad especialidadObj = new Especialidad(id, nombre, cuerpo, especialidad);
            especialidades.add(especialidadObj);
        }
        return especialidades;
    }


    @Override
    public Especialidad obtenerPorId(int id) throws SQLException {
        Especialidad especialidad = null;
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Especialidad WHERE id = ?");
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            String nombre = rs.getString("nombre");
            int cuerpo = rs.getInt("cuerpo");
            int especialidadId = rs.getInt("especialidad");
            especialidad = new Especialidad(id, nombre, cuerpo, especialidadId);
        }
        return especialidad;
    }

    @Override
    public void insertar(Especialidad especialidad) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Especialidad (nombre, cuerpo, especialidad) VALUES (?, ?, ?)");
        stmt.setString(1, especialidad.getNombre());
        stmt.setInt(2, especialidad.getCuerpo());
        stmt.setInt(3, especialidad.getEspecialidad());
        stmt.executeUpdate();
    }

    @Override
    public void actualizar(Especialidad especialidad) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("UPDATE Especialidad SET nombre = ?, cuerpo = ?, especialidad = ? WHERE id = ?");

        stmt.setString(1, especialidad.getNombre());
        stmt.setInt(2, especialidad.getCuerpo());
        stmt.setInt(3, especialidad.getEspecialidad());
        stmt.setInt(4, especialidad.getId());
        stmt.executeUpdate();
    }

    @Override
    public void eliminar(int id) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Especialidad WHERE id = ?");
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }
}