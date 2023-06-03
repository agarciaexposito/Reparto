package helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilMinisterio {
    public static final String REGEX_AUTONOMIA="(Andalucía|Aragón|Asturias|Illes Balears|Cantabria|Castilla-La Mancha|Castilla y León|Ceuta|Extremadura|La Rioja|Madrid|Melilla|Murcia|Navarra|Valencia)";
    public static final String REGEX_ESPECIALIDAD="^59\\d{4}\\s.*";
    public static final String REGEX_TURNO="^TURNO:\\s+(LIBRE|DISCAPACIDAD)\\s+INICIALES:\\s+[0-9]+\\s+DESIERTAS:\\s+[0-9]+$";
    public static final String REGEX_TROCEA_TURNO="TURNO:\\s+(LIBRE|DISCAPACIDAD)\\s+INICIALES:\\s+([0-9]+)\\s+DESIERTAS:\\s+([0-9]+)";

    public static final String REGEX_DNI="^(\\*{4,5}[0-9]+[A-Z]?|\\*{9}).*";
    public static final String REGEX_TROCEA_ASPIRANTE="(\\*{4,5}[0-9]+[A-Z]?|\\*{9})\\s+([A-Za-záéíóú\\- ]+[a-z])\\s+([A-ZÑÇÁÉÍÓÚÀÈÌÒÙÜÏ][A-ZÑÇÁÉÍÓÚÀÈÌÒÙÜÏ\\-\\s, \\.ª·']+)\\s+([\\d,]+(?:\\.\\d+)?)";

    public static boolean contineEuskera(String linea){
        return linea.trim().contains("(EUSKERA)");
    }
    public static boolean isLineaAutonomia(String linea){
        return linea.trim().matches(REGEX_AUTONOMIA);
    }
    public static boolean isLineaEspeciadlidad(String linea){
        return linea.trim().matches(REGEX_ESPECIALIDAD);
    }
    public static boolean isLineaTurno(String linea){
        return linea.trim().matches(REGEX_TURNO);
    }
    public static String[] troceaLineaTurno(String linea){
        Pattern pattern = Pattern.compile(REGEX_TROCEA_TURNO);
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

    public static boolean isLineaDNI(String linea){
        return linea.trim().matches(REGEX_DNI);
    }
    public static String[] troceaLineaAspirante(String linea){
        Pattern pattern = Pattern.compile(REGEX_TROCEA_ASPIRANTE);
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
}
