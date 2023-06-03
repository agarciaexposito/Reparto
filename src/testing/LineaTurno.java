package testing;

import helper.UtilMinisterio;

public class LineaTurno {
    public static void main(String[] args) {
        String linea="TURNO: LIBRE INICIALES: 28 DESIERTAS: 0";
        muestra(linea);
        String linea2="TURNO: DISCAPACIDAD INICIALES: 1 DESIERTAS: 0";
        muestra(linea2);
    }
    public static void muestra(String linea){
        if (UtilMinisterio.isLineaTurno(linea)){
            String [] trozo=UtilMinisterio.troceaLineaTurno(linea);
            System.out.printf("%s %d %d\n",trozo[0],Integer.parseInt(trozo[1]),Integer.parseInt(trozo[2]));
        }
    }
}
