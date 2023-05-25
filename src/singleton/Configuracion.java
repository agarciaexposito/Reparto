package singleton;
import java.io.*;
import java.util.Properties;

public class Configuracion {
    public static final String FILE_CONF="data/config";
    private static Configuracion conf=null;
    private static final String IP_SERVIDOR = "192.168.12.208";
    private static final String PUERTO = "3306";
    private static final String JDBC_MYSQL_BIBLOTECA = "jdbc:mysql://" + IP_SERVIDOR + ":" + PUERTO + "/BIBLIOTECA";
    private static final String DRIVER_DEFAULT="com.mysql.cj.jdbc.Driver";

    private String driver;
    private String url;
    private String user;
    private String password;

    private Configuracion(String driver, String url, String user) {
        if (driver.equals(""))
            driver = DRIVER_DEFAULT;
        this.driver = driver;
        if (url.equals(""))
            url = JDBC_MYSQL_BIBLOTECA;
        this.url = url;
        this.user = user;
    }

    public String getDriver() {
        if (driver.equals(""))
            return DRIVER_DEFAULT;
        else return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        if (url.equals(""))
            return JDBC_MYSQL_BIBLOTECA;
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static Configuracion getInstance() throws IOException {
        if (conf==null){
            Properties p = new Properties();
            p.load(new FileReader(FILE_CONF));
            if (p.size()!=0) {
                String driver = p.getProperty("driver");
                String url = p.getProperty("url");
                String user = p.getProperty("user");
                conf = new Configuracion(driver, url, user);
            }
            else  conf = new Configuracion(DRIVER_DEFAULT,JDBC_MYSQL_BIBLOTECA,"admin");
            Runtime.getRuntime().addShutdownHook(new Configuracion.Grabar());
        }
        return conf;
    }

    private static class Grabar extends Thread {
        @Override
        public void run() {
            try {
                if (conf!=null){
                    Properties p = new Properties();
                    p.setProperty("driver",conf.driver);
                    p.setProperty("url",conf.url);
                    p.setProperty("user", conf.user);
                    p.setProperty("pwd", conf.password);
                    p.save(new FileOutputStream(FILE_CONF),
                            "");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
