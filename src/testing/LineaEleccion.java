package testing;


import helper.Comun;
import helper.Util;

public class LineaEleccion {
    public static void main(String[] args) {
        String linea = "1    Andalucía            L 7,0666 2          Cantabria            L   7,0666    3     Castilla y León      D   7,0666   4   Madrid               L   7,0666   5     La Rioja         L   7,0666";
        String linea2= "1    Valencia             L 3,5312 2          Aragón               L   3,5312    3 * Castilla-La Mancha   L   3,5312   4    Madrid              L   3,5312   5 * Murcia         L   3,5312";
        String linea3="6 * Asturias              L 3,5312 7          Andalucía            L   3,5312    8   Cantabria            L   3,5312   9    Castilla y León     L   3,5312   10 Extremadura     L   3,5312";
        //muestraElecciones(trocea(linea));
        muestraElecciones(trocea(linea2));
        //muestraElecciones(trocea(linea3));
    }
    public static String[] trocea(String linea){
        //String patron = "\\d+(\\s+|\\s*\\s)[A-Za-zñÑáéíóúÁÉÍÓÚ\\s]+(L|D)\\s+\\d+,\\d+\s+";
        /*String patron = "\\d+\\s[A-Za-zñÑáéíóúÁÉÍÓÚ\\s]+L\\s+(\\d*|,\\d+)\\s+";
        linea = linea.trim();
        linea = linea+" ";
        String[] bloques = linea.split(patron);
        // Elimina los espacios en blanco al principio y al final de cada bloque
        for (int i = 0; i < bloques.length; i++) {
            bloques[i] = bloques[i].trim();
        }*/

       // String linea = "1    Andalucía            L 7,0666 2          Cantabria            L   7,0666    3     Castilla y León      L   7,0666   4   Madrid               L   7,0666   5     La Rioja         L   7,0666";
        String[] bloques = linea.split("(?<=\\d)\\s+");
        for (int i = 0; i < bloques.length; i++) {
            bloques[i] = bloques[i].trim();
        }
        System.out.println(bloques.length);
        return bloques;
    }


    public static String[] eleccion(String ele){
        return ele.trim().split("\\s+");
    }
    public static void muestraElecciones(String[] ele){
        int cont = 1;
        while (cont<=ele.length){
            String valor=ele[cont-1];
            if (cont %2 ==0){
                if (valor.contains("*")) {
                    System.out.println("Rechazado");
                    valor=valor.replaceAll("\\*","");
                }
                for (String autonomia: Comun.AUTONOMIA) {
                    if (valor.toUpperCase().contains(autonomia.toUpperCase())){
                        System.out.println("Comunidad:"+autonomia);
                        valor=valor.toUpperCase().replaceAll(autonomia.toUpperCase(),"");
                    }

                }
                String[] trozos = eleccion(valor);
                System.out.println(String.format("Libre: %b",(trozos[0].equals("L"))?true:false));
                trozos[1]=trozos[1].trim().replace(",",".");
                System.out.printf("Nota: %f\n",Float.parseFloat(trozos[1]));
            }
            else System.out.println("Orden:"+valor);
            cont++;
        }
    }
}
