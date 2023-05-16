package testing;

import helper.UtilPlazas;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LineaEspecialidad {
    public static void main(String[] args) {
        String linea = "590002       LENGUAS: MUERTAS                                                                 1        2";
        String linea2 = "590002       GRIE:GO                                                                 1        2";
        String navarra1 = "597032   LENGUA EXTRANJERA-NAVARRA                                1       1         1";
        String linea4 = "597032   LENGUA (EXTRANJERA): INGLÉS                                1       1";

        muestra(linea);
        muestra(linea2);
        muestraN(navarra1);
        muestra(linea4);
    }
    private static void muestra(String linea) {
        if (UtilPlazas.isLineaEspecialidad(linea)){

            String[] trozos=UtilPlazas.troceaLineasEspecialidad(linea);
            System.out.println("Especialidad: " + trozos[0]);
            System.out.println("Descripción: " + trozos[1]);
            System.out.println("Libre: " + trozos[2]);
            System.out.println("Plazas: " + trozos[3]);
        }


    }
    private static void muestraN(String linea) {
        if (UtilPlazas.isLineaEspecialidadNavarra(linea)){

            String[] trozos=UtilPlazas.troceaLineasEspecialidadNavarra(linea);
            System.out.println("Especialidad: " + trozos[0]);
            System.out.println("Descripción: " + trozos[1]);
            System.out.println("Euskera"+trozos[2]);
            System.out.println("Libre: " + trozos[3]);
            System.out.println("Plazas: " + trozos[4]);
        }


    }
    /*private static void muestra(String linea) {
        String patron = "^(\\d{6})\\s+([A-ZÑÇÁÉÍÓÚ][A-ZÑÇÁÉÍÓÚ\\s]*)(\\d)\\s+(\\d+)$";
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(linea);
        if (matcher.matches()) {

            String codigo = matcher.group(1).trim();
            String descripcion = matcher.group(2).trim();
            String libre = matcher.group(3).trim();
            String plazas = matcher.group(4).trim();
            System.out.println("Especialidad: " + codigo);
            System.out.println("Descripción: " + descripcion);
            System.out.println("Libre: " + libre);
            System.out.println("Plazas: " + plazas);
        } else {
            System.out.println("La cadena de texto no sigue el patrón especificado.");
        }
    }*/
}
