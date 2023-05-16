package testing;

import helper.Util;

public class LineaAntiguedad {
    public static void main(String[] args) {
        String linea="Antigüedad en centros públicos en la especialidad a que se opta:       04 años       10 meses 22 días";
        muestra(linea);
        String linea2="Antigüedad en centros públicos en la especialidad a que se opta:       14 años       10 meses 02 días";
        muestra(linea2);
    }

    private static void muestra(String linea) {
        System.out.println(Util.isLineaAntiguedad(linea));
        String [] trozo=Util.troceaLineaAntigueada(linea);
        if (trozo!=null)
            System.out.printf("Años: %d Mes: %d Días: %d\n",Integer.parseInt(trozo[0]),Integer.parseInt(trozo[1]),Integer.parseInt(trozo[2]));
    }
}
