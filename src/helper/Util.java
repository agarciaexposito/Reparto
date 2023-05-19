package helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    private Util() {}

    public static final String URL = "https://www.educacionyfp.gob.es/contenidos/profesorado/no-universitarios/oposiciones-y-ofertas-trabajo/convocatoria-estabilizacion.html";
    public static final String DOWNLOAD_PATH = "./descargas";

    //Expresiones regulares para el fichero de Baremación
    public static final String REGEX_DNI="\\*{4,5}[0-9A-Z]+";
    public static final String REGEX_NOMBRE="\\s[A-ZÑÇÁÉÍÓÚ][A-ZÑÇÁÉÍÓÚ\\s,]+"; //solo para dentro de la línea donde esté el nombre
    public static final String PROTECCION="*********         *** No se publica por protección de datos ***";
    public static final String PROTECCION2=".*No se publica por protección de datos.*";
    public static final String REGEX_TOTAL="^TOTAL:\\s*.*";
    public static final String REGEX_ANTES="Plazas convocadas que se solicitan por orden de preferencia:";
    public static final String REGEX_ANTIG="Antigüedad en centros públicos en la especialidad a que se opta:";

    public static final String REGEX_TROCEA_ANTIG=REGEX_ANTIG+"\\s+(\\d+)\\s.*\\s(\\d+)\\s.*\\s(\\d+)\\s.*";


    // Fichero de baremación
    public static boolean isNombre(String linea){
        //"^\\*{4}[0-9A-Z]+.*"
        return linea.trim().matches("^"+REGEX_DNI+".*") || linea.trim().equals(PROTECCION) || linea.trim().matches(PROTECCION2);
    }

    public static boolean isNotaNacionalBaremo(String linea){
        return linea.trim().matches(REGEX_TOTAL);
    }

    public static boolean isLineaAntesDeNotasAutonomicas(String linea){
        return linea.trim().matches(REGEX_ANTES);
    }
    public static boolean isLineaAntiguedad(String linea){
        return linea.trim().contains(REGEX_ANTIG);
    }

    public static String[] troceaLineaAntigueada(String linea){
        Pattern pattern = Pattern.compile(REGEX_TROCEA_ANTIG);
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

    public static String[] troceaLineaNombre(String linea){
        String[] trozo={"",""};

        Pattern patronCodigoPattern = Pattern.compile(Util.REGEX_DNI);
        Pattern patronNombrePattern = Pattern.compile(Util.REGEX_NOMBRE);

        Matcher matcherCodigo = patronCodigoPattern.matcher(linea);
        Matcher matcherNombre = patronNombrePattern.matcher(linea);

        if (matcherCodigo.find() && matcherNombre.find()) {
            trozo[0] = matcherCodigo.group().trim();
            trozo[1]  = matcherNombre.group().trim();
        } else {//proteccion de datos;
            trozo[0]="*********";
            trozo[1]="*** No se publica por protección de datos ***";
        }
        return trozo;
    }

    public static float troceaLineaNotaNacionalBaremo(String linea){
        float nota = 0;
        String patron = "TOTAL:\\s+([\\d,]+(?:\\.\\d+)?)";

        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(linea.trim());

        if (matcher.find() && matcher.group().length()>=2) {
            String valorTotalStr = matcher.group(1);
            if (!valorTotalStr.equals(""))
                nota = Float.parseFloat(valorTotalStr.replace(",", "."));
        }
        return nota;
    }

    public static float troceaLineaNota(String linea, String apartado) {
        float nota = 0;
        String patron = "TOTAL:\\s+.*\\s+"+apartado+"\\s+([\\d,]+(?:\\.\\d+)?)";
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(linea.trim());

        if (matcher.find() && matcher.group().length()>=2) {
            String valorTotalStr = matcher.group(1);
            if (!valorTotalStr.equals(""))
                nota = Float.parseFloat(valorTotalStr.replace(",", "."));
        }
        return nota;
    }
    public static float troceaLineaSinTotalNota(String linea, String apartado) {
        float nota = 0;
        linea="TOTAL:  "+linea.trim();
        String patron = "TOTAL:\\s+.*\\s+"+apartado+"\\s+([\\d,]+(?:\\.\\d+)?)";
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(linea);

        if (matcher.find() && matcher.group().length()>=2) {
            String valorTotalStr = matcher.group(1);
            if (!valorTotalStr.equals(""))
                nota = Float.parseFloat(valorTotalStr.replace(",", "."));
        }
        return nota;
    }
    public static String[] capturaEleccciones(String linea) {
        String[] bloques = linea.split("(?<=\\d)\\s+");
        for (int i = 0; i < bloques.length; i++) {
            bloques[i] = bloques[i].trim();
        }
        return bloques;
    }



}
