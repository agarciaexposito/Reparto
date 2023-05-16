package helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilPlazas {
    private UtilPlazas(){};

    //Expresiones regulares para el fichero de plazas
/*
    private static final String letrasPermitidasSinEspacio="[A-ZÑÇÁÉÍÓÚ]";
    private static final String letrasPermitidasConEspacio ="[A-ZÑÇÁÉÍÓÚ\\s]";
    public static final String REGEX_LINEA_AUTONOMIA="^[A-Z][A-ZÑÇÁÉÍÓÚ\\s-]+$";
    public static final String REGEX_LINEA_ESPECIALIDAD="^\\d{6}\\s+"+ letrasPermitidasConEspacio +"+\\d\\s+\\d+$";
    public static final String REGEX_TROCEA_ESPECIALIDAD="^(\\d{6})\\s+("+letrasPermitidasSinEspacio+letrasPermitidasSinEspacio+"*)(\\d)\\s+(\\d+)$";
    public static final String REGEX_LINEA_ESPECIALIDAD_SIN_NOMBRE="^\\d\\s+\\d+$";

    public static final String REGEX_TROCEA_ESPECIALIDAD_SIN_NOMBRE="^(\\d)\\s+(\\d+)$";
    public static final String REGE_LINEA_ESPECIALIDAD_NAVARRA="^\\d{6}\\s+"+letrasPermitidasConEspacio+"+\\d\\s+\\d+\\s+\\d+$";

    public static final String REGEX_TROCEA_ESPECIALIDAD_NAVARRA="^(\\d{6})\\s+("+letrasPermitidasSinEspacio+letrasPermitidasConEspacio+"*)(\\d)\\s+(\\d+)\\s+(\\d+)$";
    public static final String REGE_LINEA_ESPECIALIDAD_SIN_NOMBRE_NAVARRA="^\\d\\s+\\d+\\s+\\d+$";
    public static final String REGEX_TROCEA_ESPECIALIDAD_SIN_NOMBRE_NAVARRA="^(\\d)\\s+(\\d+)\\s+(\\d+)$";
*/


    //Expresiones regulares para el fichero de plazas
    public static final String REGEX_LINEA_AUTONOMIA="^[A-Z][A-ZÑÇÁÉÍÓÚ\\s-]+$";
    public static final String REGEX_LINEA_ESPECIALIDAD="^\\d{6}\\s+[A-ZÑÇÁÉÍÓÚ,:\\s/\\(\\)\\-]+\\d\\s+\\d+$";
    public static final String REGEX_TROCEA_ESPECIALIDAD="^(\\d{6})\\s+([A-ZÑÇÁÉÍÓÚ][A-ZÑÇÁÉÍÓÚ,:\\s/\\(\\)\\-]*)(\\d)\\s+(\\d+)$";
    public static final String REGEX_LINEA_ESPECIALIDAD_SIN_NOMBRE="^\\d\\s+\\d+$";

    public static final String REGEX_TROCEA_ESPECIALIDAD_SIN_NOMBRE="^(\\d)\\s+(\\d+)$";
    public static final String REGE_LINEA_ESPECIALIDAD_NAVARRA="^\\d{6}\\s+[A-ZÑÇÁÉÍÓÚ,:\\s/\\(\\)\\-]+\\d\\s+\\d+\\s+\\d+$";
    public static final String REGEX_TROCEA_ESPECIALIDAD_NAVARRA="^(\\d{6})\\s+([A-ZÑÇÁÉÍÓÚ][A-ZÑÇÁÉÍÓÚ,:\\s/\\(\\)\\-]*)(\\d)\\s+(\\d+)\\s+(\\d+)$";
    public static final String REGE_LINEA_ESPECIALIDAD_SIN_NOMBRE_NAVARRA="^\\d\\s+\\d+\\s+\\d+$";
    public static final String REGEX_TROCEA_ESPECIALIDAD_SIN_NOMBRE_NAVARRA="^(\\d)\\s+(\\d+)\\s+(\\d+)$";


    // fichero de plazas

    public static boolean isLineaAutonomia(String linea){
        return linea.trim().matches(REGEX_LINEA_AUTONOMIA);
    }
    public static String getAutonomia(String linea){
        String autonomia="";
        for (String auto : Comun.AUTONOMIA) {
            if (auto.equalsIgnoreCase(linea.trim())){
                autonomia=auto;
                break;
            }
        }
        return autonomia;
    }
    public static boolean isLineaEspecialidad(String linea){
        return linea.trim().matches(REGEX_LINEA_ESPECIALIDAD);
    }
    public static String[] troceaLineasEspecialidad(String linea){
        String patron = REGEX_TROCEA_ESPECIALIDAD;
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(linea.trim());
        if (matcher.matches()) {
            String[] trozos={"","","",""};
            for (int i = 0; i < 4; i++) {
                trozos[i] = matcher.group(i+1).trim();
            }
            return trozos;
        }
        return null;
    }
    public static boolean isLineaEspecialidadSinNombre(String linea){
        return linea.trim().matches(REGEX_LINEA_ESPECIALIDAD_SIN_NOMBRE);
    }

    public static String[] troceaLineasEspecialidadSinNombre(String linea){
        String patron = REGEX_TROCEA_ESPECIALIDAD_SIN_NOMBRE;
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(linea.trim());
        if (matcher.matches()) {
            String[] trozos={"",""};
            for (int i = 0; i < 2; i++) {
                trozos[i] = matcher.group(i+1).trim();
            }
            return trozos;
        }
        return null;
    }

    public static boolean isLineaEspecialidadNavarra(String linea){
        return linea.trim().matches(REGE_LINEA_ESPECIALIDAD_NAVARRA);
    }
    public static boolean isLineaEspecialidadSinNombreNavarra(String linea){
        return linea.trim().matches(REGE_LINEA_ESPECIALIDAD_SIN_NOMBRE_NAVARRA);
    }

    public static String[] troceaLineasEspecialidadNavarra(String linea) {
        String patron = REGEX_TROCEA_ESPECIALIDAD_NAVARRA;
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(linea.trim());
        if (matcher.matches()) {
            String[] trozos={"","","","",""};
            for (int i = 0; i < 5; i++) {
                trozos[i] = matcher.group(i+1).trim();
            }
            return trozos;
        }
        return null;
    }

    public static String[] troceaLineasEspecialidadSinNombreNavarra(String linea) {
        String patron = REGEX_TROCEA_ESPECIALIDAD_SIN_NOMBRE_NAVARRA;
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(linea.trim());
        if (matcher.matches()) {
            String[] trozos={"","",""};
            for (int i = 0; i < 3; i++) {
                trozos[i] = matcher.group(i+1).trim();
            }
            return trozos;
        }
        return null;
    }
}
