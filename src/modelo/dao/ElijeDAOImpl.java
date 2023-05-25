package modelo.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ElijeDAOImpl implements ElijeDAO {
    private Connection connection;

    public ElijeDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Elije> obtenerTodos() throws SQLException {
        List<Elije> elecciones = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Elije");

        while (rs.next()) {
        int id = rs.getInt("id");
        int idParticipa = rs.getInt("idParticipa");
        int idAutonomia = rs.getInt("idAutonomia");
        int orden = rs.getInt("orden");
        int turno = rs.getInt("turno");
        char rechazado = rs.getString("rechazado").charAt(0);

        Elije eleccion = new Elije(id, idParticipa, idAutonomia, orden, turno, rechazado);
        elecciones.add(eleccion);
        }
        return elecciones;
    }

    @Override
    public Elije obtenerPorId(int id) throws SQLException {
        Elije eleccion = null;
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Elije WHERE id = ?");
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            int idParticipa = rs.getInt("idParticipa");
            int idAutonomia = rs.getInt("idAutonomia");
            int orden = rs.getInt("orden");
            int turno = rs.getInt("turno");
            char rechazado = rs.getString("rechazado").charAt(0);

            eleccion = new Elije(id, idParticipa, idAutonomia, orden, turno, rechazado);
        }
        return eleccion;
    }

        @Override
        public void insertar(Elije elije) throws SQLException {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO Elije (idParticipa, idAutonomia, orden, turno, rechazado) VALUES (?, ?, ?, ?, ?)");
            stmt.setInt(1, elije.getIdParticipa());
            stmt.setInt(2, elije.getIdAutonomia());
            stmt.setInt(3, elije.getOrden());
            stmt.setInt(4, elije.getTurno());
            stmt.setString(5, String.valueOf(elije.getRechazado()));
            stmt.executeUpdate();
        }

        @Override
        public void actualizar(Elije elije) throws SQLException {
            PreparedStatement stmt = connection.prepareStatement("UPDATE Elije SET idParticipa = ?, idAutonomia = ?, orden = ?, turno = ?, rechazado = ? WHERE id = ?");
            stmt.setInt(1, elije.getIdParticipa());
            stmt.setInt(2, elije.getIdAutonomia());
            stmt.setInt(3, elije.getOrden());
            stmt.setInt(4, elije.getTurno());
            stmt.setString(5, String.valueOf(elije.getRechazado()));
            stmt.setInt(6, elije.getId());
            stmt.executeUpdate();
        }

        @Override
        public void eliminar(int id) throws SQLException {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM Elije WHERE id = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
