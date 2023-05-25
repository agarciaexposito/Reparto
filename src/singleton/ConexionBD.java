package singleton;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConexionBD {
    private static ConexionBD conexionBD = null;
    private Connection conexion;
    private ConexionBD(Connection conexion) {
        this.conexion=conexion;
    }
    public Connection getConexion(){
        return conexion;
    }
    public static ConexionBD getInstance() throws IOException, SQLException, ClassNotFoundException {
        if ( conexionBD == null ) {
            Configuracion myConf= Configuracion.getInstance();
            Class.forName(myConf.getDriver()) ; // cargamos la clase que tiene el driver para MySQL
            Connection conexion = DriverManager.getConnection(myConf.getUrl(),myConf.getUser(),myConf.getPassword());
            conexion.setAutoCommit(true); // por defecto se hace autocommit;
            conexionBD=new ConexionBD(conexion);
            Runtime.getRuntime().addShutdownHook(new ConexionBD.MiApagado());
        }
        return conexionBD ;
    }
    private void desconectar() throws SQLException {
        if ( conexion != null )
            conexion.close() ;
    }
    static class MiApagado extends Thread {
        @Override
        public void run() {
            try {
                if (conexionBD!=null)
                    conexionBD.desconectar();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}