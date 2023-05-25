package modelo.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AutonomiaDAOImpl implements AutonomiaDAO{
    private Connection connection;

    // Constructor que recibe la conexión a la base de datos
    public AutonomiaDAOImpl(Connection connection) {
        this.connection = connection;
    }

    // Método para insertar una nueva autonomía en la tabla Autonomia
    public void insertar(Autonomia autonomia) throws SQLException {
        String query = "INSERT INTO Autonomia (id, nombre) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, autonomia.getId());
            statement.setString(2, autonomia.getNombre());
            statement.executeUpdate();
        }
    }

    // Método para obtener todas las autonomías de la tabla Autonomia
    public List<Autonomia> obtenerTodos() throws SQLException {
        List<Autonomia> autonomias = new ArrayList<>();
        String query = "SELECT id, nombre FROM Autonomia";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                Autonomia autonomia = new Autonomia(id, nombre);
                autonomias.add(autonomia);
            }
        }
        return autonomias;
    }

    public int obtenerId(String nombre) throws SQLException {
        int id=0;
        String query = "SELECT id FROM Autonomia WHERE nombre = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, nombre);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            id = rs.getInt("id");
        }
        return id;
    }

}
