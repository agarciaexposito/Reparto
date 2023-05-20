package testing;

import helper.Util;

public class LineaEuskera {
    public static void main(String[] args) {
        String linea = "590001 FILOSOFÍA (EUSKERA) 1";

        String linea2 = "597032 LENGUA EXTRANJERA: INGLÉS";
        String linea3 = "597082 EUSKERA (NAVARRA) (EUSKERA) 1";
        String linea4="590057 LENGUA Y LITERATURA VASCA (NAVARRA) (EUSKERA) 1";
        String linea5="593099 TECLADOS / PIANO JAZZ";
        String linea6="595518 MATERIALES Y TECNOLOGÍA: (CERÁMICA Y VIDRIO)";
        String linea7="595519 MATERIALES Y TECNOLOGÍA: CONSERVACIÓN Y RESTAURACIÓN";
        String linea8="595520 MATERIALES Y TECNOLOGÍA: DISEÑO";
        String linea9="590120 PROCESOS Y PRODUCTOS DE TEXTIL, CONFECCIÓN Y PIEL";
        muestra(linea9);
    }

    private static void muestra(String linea) {
        System.out.println("euskera:"+Util.isLineaEuskera(linea));
        System.out.println("especialiadad: "+Util.isLineaEspecialidad(linea));
        String[] trozo;
        if (Util.isLineaEuskera(linea))
            trozo=Util.troceaLineaEspecialidadEuskera(linea);
        else trozo= Util.troceaLineaEspecialidad(linea);
        System.out.println("Especialidad: "+trozo[0]);
        System.out.println("nombre: "+trozo[1]);
    }
}
