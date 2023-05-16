package testing;


import helper.Util;

import java.util.regex.Matcher;
        import java.util.regex.Pattern;

public class LineaNota {
    public static void main(String[] args) {
        //String linea = "TOTAL:";
        String linea = "TOTAL:        6,5788             1 4,5788          1.1   2,3915          3 2,0000           3.2    2,0000";

        muestra(linea);

        String linea2 = "TOTAL:        3,9873             1 1,4873          1.2   1,4873          2 0,5000          2.1     0,5000       3 2,0000          3.2     2,0000";


        muestra(linea2);

        String linea3="TOTAL:       13,0000             1 7,0000          1.1   7,0000          2 1,5000       2.1 0,5000                                      3 4,5000       3.1   2,5000";
        muestra(linea3);

        String linea4="                                                   1.2   0,0291                            2.2    1,0000             2.2.2   1,0000";
        muestra("TOTAL:"+linea4);
        String linea5="                                                   1.2   0,0291                            2.2    1,0000             2.2.2   1,0000";
        muestra("TOTAL:"+linea5);
    }

    private static void muestra(String linea2) {
        System.out.println(linea2);
        System.out.printf("Nacional: %f\n", Util.troceaLineaNotaNacionalBaremo(linea2));
        System.out.printf("Nota1: %f\n", Util.troceaLineaNota(linea2,"1"));
        System.out.printf("Nota1.1: %f\n", Util.troceaLineaNota(linea2,"1\\.1"));
        System.out.printf("Nota1.2: %f\n", Util.troceaLineaNota(linea2,"1\\.2"));
        System.out.printf("Nota1.3: %f\n", Util.troceaLineaNota(linea2,"1\\.3"));
        System.out.printf("Nota1.4: %f\n", Util.troceaLineaNota(linea2,"1\\.4"));
        System.out.printf("Nota2: %f\n", Util.troceaLineaNota(linea2,"2"));
        System.out.printf("Nota2.1: %f\n", Util.troceaLineaNota(linea2,"2\\.1"));
        System.out.printf("Nota2.2: %f\n", Util.troceaLineaNota(linea2,"2\\.2"));
        System.out.printf("Nota2.2.1: %f\n", Util.troceaLineaNota(linea2,"2\\.2\\.1"));
        System.out.printf("Nota2.2.2: %f\n", Util.troceaLineaNota(linea2,"2\\.2\\.2"));
        System.out.printf("Nota2.2.3: %f\n", Util.troceaLineaNota(linea2,"2\\.2\\.3"));
        System.out.printf("Nota2.3: %f\n", Util.troceaLineaNota(linea2,"2\\.3"));
        System.out.printf("Nota2.3.1: %f\n", Util.troceaLineaNota(linea2,"2\\.3\\.1"));
        System.out.printf("Nota2.3.2: %f\n", Util.troceaLineaNota(linea2,"2\\.3\\.2"));
        System.out.printf("Nota2.4: %f\n", Util.troceaLineaNota(linea2,"2\\.4"));
        System.out.printf("Nota2.4.1: %f\n", Util.troceaLineaNota(linea2,"2\\.4\\.1"));
        System.out.printf("Nota2.4.2: %f\n", Util.troceaLineaNota(linea2,"2\\.4\\.2"));
        System.out.printf("Nota2.4.3: %f\n", Util.troceaLineaNota(linea2,"2\\.4\\.3"));
        System.out.printf("Nota2.4.4: %f\n", Util.troceaLineaNota(linea2,"2\\.4\\.4"));
        System.out.printf("Nota2.4.5: %f\n", Util.troceaLineaNota(linea2,"2\\.4\\.5"));
        System.out.printf("Nota2.5: %f\n", Util.troceaLineaNota(linea2,"2\\.5"));
        System.out.printf("Nota3: %f\n", Util.troceaLineaNota(linea2,"3"));
        System.out.printf("Nota3.1: %f\n", Util.troceaLineaNota(linea2,"3\\.1"));
        System.out.printf("Nota3.2: %f\n", Util.troceaLineaNota(linea2,"3\\.2"));
    }


    private static void notaNacional(String linea) {
        String patron = "TOTAL:\\s+([\\d,]+(?:\\.\\d+)?)";


        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(linea);

        if (matcher.find()) {
            String valorTotalStr = matcher.group(1);
            float valorTotal = Float.parseFloat(valorTotalStr.replace(",", "."));
            System.out.println("El valor total es: " + valorTotal);
        } else {
            System.out.println("No se encontró la cadena TOTAL:");
        }
    }
}