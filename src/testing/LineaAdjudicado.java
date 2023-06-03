package testing;

import helper.UtilMinisterio;

public class LineaAdjudicado {
    public static void main(String[] args) {
        String linea="****1337N Aragón VALVERDE LLANTADA, M. ANTONIA  11,0000";
        muestra(linea);
        String linea2="*****4799 Madrid MOLINERO CAMARA, MªGEMMA  10,7000";
        muestra(linea2);
        String linea3="*****7593 Madrid RUBIO MENDEZ, MARIA  12,6915";
        muestra(linea3);
        String linea4="****7007J Illes Balears SOBERATS REUS, MARIA APOL·LONIA  9,0000";
        muestra(linea4);
    }
    public static void muestra(String linea){
        if (UtilMinisterio.isLineaDNI(linea)){
            String [] trozo=UtilMinisterio.troceaLineaAspirante(linea);
            System.out.printf("%s-%s-%s-%.4f\n",trozo[0],trozo[1],trozo[2],Float.parseFloat(trozo[3].replace(",", ".")));
        }
    }
}
