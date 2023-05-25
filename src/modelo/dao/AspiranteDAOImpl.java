package modelo.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AspiranteDAOImpl implements AspiranteDAO {
    private Connection connection;

    public AspiranteDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Aspirante> obtenerTodos() throws SQLException {
        List<Aspirante> aspirantes = new ArrayList<>();
        try (Connection conn = connection){
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Aspirante");

            while (rs.next()) {
                int id = rs.getInt("id");
                String dni = rs.getString("dni");
                String nombre = rs.getString("nombre");

                Aspirante aspirante = new Aspirante(id, dni, nombre);
                aspirantes.add(aspirante);
            }
        }
        return aspirantes;
    }

    @Override
    public Aspirante obtenerPorId(int id) throws SQLException {
        Aspirante aspirante = null;
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Aspirante WHERE id = ?");
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            String dni = rs.getString("dni");
            String nombre = rs.getString("nombre");
            aspirante = new Aspirante(id, dni, nombre);
        }
        return aspirante;
    }

    @Override
    public Aspirante obtenerPorDniNombre(String dni, String nombre) throws SQLException {
        Aspirante aspirante = null;
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Aspirante WHERE dni = ? AND nombre = ?");
        stmt.setString(1, dni);
        stmt.setString(2, nombre);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            aspirante = new Aspirante(rs.getInt("id"), rs.getString("dni"), rs.getString("nombre"));
        }
        return aspirante;
    }

    @Override
    public void insertar(Aspirante aspirante) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Aspirante (dni, nombre) VALUES (?, ?)");
        stmt.setString(1, aspirante.getDni());
        stmt.setString(2, aspirante.getNombre());
        stmt.executeUpdate();
    }

    @Override
    public void actualizar(Aspirante aspirante) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("UPDATE Aspirante SET dni = ?, nombre = ? WHERE id = ?");
        stmt.setString(1, aspirante.getDni());
        stmt.setString(2, aspirante.getNombre());
        stmt.setInt(3, aspirante.getId());
        stmt.executeUpdate();
    }

    @Override
    public void eliminar(int id) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Aspirante WHERE id = ?");
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }
}