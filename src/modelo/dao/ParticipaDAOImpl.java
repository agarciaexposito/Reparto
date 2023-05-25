package modelo.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipaDAOImpl implements ParticipaDAO {
    private Connection connection;

    public ParticipaDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Participa> obtenerTodos() throws SQLException {
        List<Participa> participaciones = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Participa");
        while (rs.next()) {
            int id = rs.getInt("id");
            int idAspirante = rs.getInt("idAspirante");
            int idEspecialidad = rs.getInt("idEspecialidad");
            double notaNacional = rs.getDouble("notaNacional");
            double nota1 = rs.getDouble("nota1");
            double nota11 = rs.getDouble("nota11");
            double nota12 = rs.getDouble("nota12");
            double nota13 = rs.getDouble("nota13");
            double nota14 = rs.getDouble("nota14");
            double nota2 = rs.getDouble("nota2");
            double nota21 = rs.getDouble("nota21");
            double nota22 = rs.getDouble("nota22");
            double nota221 = rs.getDouble("nota221");
            double nota222 = rs.getDouble("nota222");
            double nota223 = rs.getDouble("nota223");
            double nota23 = rs.getDouble("nota23");
            double nota231 = rs.getDouble("nota231");
            double nota232 = rs.getDouble("nota232");
            double nota24 = rs.getDouble("nota24");
            double nota241 = rs.getDouble("nota241");
            double nota242 = rs.getDouble("nota242");
            double nota243 = rs.getDouble("nota243");
            double nota244 = rs.getDouble("nota244");
            double nota245 = rs.getDouble("nota245");
            double nota25 = rs.getDouble("nota25");
            double nota3 = rs.getDouble("nota3");
            double nota31 = rs.getDouble("nota31");
            double nota32 = rs.getDouble("nota32");
            int anos = rs.getInt("anos");
            int meses = rs.getInt("meses");
            int dias = rs.getInt("dias");
            char euskera = rs.getString("euskera").charAt(0);
            char asignado = rs.getString("asignado").charAt(0);

            Participa participaObj = new Participa(id, idAspirante, idEspecialidad, notaNacional, nota1, nota11,
                    nota12, nota13, nota14, nota2, nota21, nota22, nota221, nota222, nota223, nota23, nota231,
                    nota232, nota24, nota241, nota242, nota243, nota244, nota245, nota25, nota3, nota31, nota32,
                    anos, meses, dias, euskera, asignado);
            participaciones.add(participaObj);
        }
        return participaciones;
    }

    @Override
    public Participa obtenerPorId(int id) throws SQLException {
        Participa participa = null;
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Participa WHERE id = ?");
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            int idAspirante = rs.getInt("idAspirante");
            int idEspecialidad = rs.getInt("idEspecialidad");
            double notaNacional = rs.getDouble("notaNacional");
            double nota1 = rs.getDouble("nota1");
            double nota11 = rs.getDouble("nota11");
            double nota12 = rs.getDouble("nota12");
            double nota13 = rs.getDouble("nota13");
            double nota14 = rs.getDouble("nota14");
            double nota2 = rs.getDouble("nota2");
            double nota21 = rs.getDouble("nota21");
            double nota22 = rs.getDouble("nota22");
            double nota221 = rs.getDouble("nota221");
            double nota222 = rs.getDouble("nota222");
            double nota223 = rs.getDouble("nota223");
            double nota23 = rs.getDouble("nota23");
            double nota231 = rs.getDouble("nota231");
            double nota232 = rs.getDouble("nota232");
            double nota24 = rs.getDouble("nota24");
            double nota241 = rs.getDouble("nota241");
            double nota242 = rs.getDouble("nota242");
            double nota243 = rs.getDouble("nota243");
            double nota244 = rs.getDouble("nota244");
            double nota245 = rs.getDouble("nota245");
            double nota25 = rs.getDouble("nota25");
            double nota3 = rs.getDouble("nota3");
            double nota31 = rs.getDouble("nota31");
            double nota32 = rs.getDouble("nota32");
            int anos = rs.getInt("anos");
            int meses = rs.getInt("meses");
            int dias = rs.getInt("dias");
            char euskera = rs.getString("euskera").charAt(0);
            char asignado = rs.getString("asignado").charAt(0);
            participa = new Participa(id, idAspirante, idEspecialidad, notaNacional, nota1, nota11, nota12, nota13,
                    nota14, nota2, nota21, nota22, nota221, nota222, nota223, nota23, nota231, nota232, nota24,
                    nota241, nota242, nota243, nota244, nota245, nota25, nota3, nota31, nota32, anos, meses, dias,
                    euskera, asignado);
        }
        return participa;
    }

    @Override
    public void insertar(Participa participa) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Participa (idAspirante, idEspecialidad, notaNacional, nota1, nota11, nota12, nota13, nota14, nota2, nota21, nota22, nota221, nota222, nota223, nota23, nota231, nota232, nota24, nota241, nota242, nota243, nota244, nota245, nota25, nota3, nota31, nota32, anos, meses, dias, euskera, asignado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        stmt.setInt(1, participa.getIdAspirante());
        stmt.setInt(2, participa.getIdEspecialidad());
        stmt.setDouble(3, participa.getNotaNacional());
        stmt.setDouble(4, participa.getNota1());
        stmt.setDouble(5, participa.getNota11());
        stmt.setDouble(6, participa.getNota12());
        stmt.setDouble(7, participa.getNota13());
        stmt.setDouble(8, participa.getNota14());
        stmt.setDouble(9, participa.getNota2());
        stmt.setDouble(10, participa.getNota21());
        stmt.setDouble(11, participa.getNota22());
        stmt.setDouble(12, participa.getNota221());
        stmt.setDouble(13, participa.getNota222());
        stmt.setDouble(14, participa.getNota223());
        stmt.setDouble(15, participa.getNota23());
        stmt.setDouble(16, participa.getNota231());
        stmt.setDouble(17, participa.getNota232());
        stmt.setDouble(18, participa.getNota24());
        stmt.setDouble(19, participa.getNota241());
        stmt.setDouble(20, participa.getNota242());
        stmt.setDouble(21, participa.getNota243());
        stmt.setDouble(22, participa.getNota244());
        stmt.setDouble(23, participa.getNota245());
        stmt.setDouble(24, participa.getNota25());
        stmt.setDouble(25, participa.getNota3());
        stmt.setDouble(26, participa.getNota31());
        stmt.setDouble(27, participa.getNota32());
        stmt.setInt(28, participa.getAnos());
        stmt.setInt(29, participa.getMeses());
        stmt.setInt(30, participa.getDias());
        stmt.setString(31, String.valueOf(participa.getEuskera()));
        stmt.setString(32, String.valueOf(participa.getAsignado()));
        stmt.executeUpdate();
    }
    @Override
    public void actualizar(Participa participa) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("UPDATE Participa SET idAspirante = ?, idEspecialidad = ?, notaNacional = ?, nota1 = ?, nota11 = ?, nota12 = ?, nota13 = ?, nota14 = ?, nota2 = ?, nota21 = ?, nota22 = ?, nota221 = ?, nota222 = ?, nota223 = ?, nota23 = ?, nota231 = ?, nota232 = ?, nota24 = ?, nota241 = ?, nota242 = ?, nota243 = ?, nota244 = ?, nota245 = ?, nota25 = ?, nota3 = ?, nota31 = ?, nota32 = ?, anos = ?, meses = ?, dias = ?, euskera = ?, asignado = ? WHERE id = ?");
        stmt.setInt(1, participa.getIdAspirante());
        stmt.setInt(2, participa.getIdEspecialidad());
        stmt.setDouble(3, participa.getNotaNacional());
        stmt.setDouble(4, participa.getNota1());
        stmt.setDouble(5, participa.getNota11());
        stmt.setDouble(6, participa.getNota12());
        stmt.setDouble(7, participa.getNota13());
        stmt.setDouble(8, participa.getNota14());
        stmt.setDouble(9, participa.getNota2());
        stmt.setDouble(10, participa.getNota21());
        stmt.setDouble(11, participa.getNota22());
        stmt.setDouble(12, participa.getNota221());
        stmt.setDouble(13, participa.getNota222());
        stmt.setDouble(14, participa.getNota223());
        stmt.setDouble(15, participa.getNota23());
        stmt.setDouble(16, participa.getNota231());
        stmt.setDouble(17, participa.getNota232());
        stmt.setDouble(18, participa.getNota24());
        stmt.setDouble(19, participa.getNota241());
        stmt.setDouble(20, participa.getNota242());
        stmt.setDouble(21, participa.getNota243());
        stmt.setDouble(22, participa.getNota244());
        stmt.setDouble(23, participa.getNota245());
        stmt.setDouble(24, participa.getNota25());
        stmt.setDouble(25, participa.getNota3());
        stmt.setDouble(26, participa.getNota31());
        stmt.setDouble(27, participa.getNota32());
        stmt.setInt(28, participa.getAnos());
        stmt.setInt(29, participa.getMeses());
        stmt.setInt(30, participa.getDias());
        stmt.setString(31, String.valueOf(participa.getEuskera()));
        stmt.setString(32, String.valueOf(participa.getAsignado()));
        stmt.setInt(33, participa.getId());
        stmt.executeUpdate();
    }

    @Override
    public void eliminar(int id) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Participa WHERE id = ?");
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }
}