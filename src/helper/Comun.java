package helper;

import java.util.HashMap;
import java.util.Map;

public class Comun {
    private Comun() {
    }
    public static final String REGEX_LINEA_VACIA="^\\s*$";
    /*public static final Map<String,Integer> E590221 = new HashMap<String,Integer>(){{
        put("Andalucía_L",11);put("Andalucía_D",1);
        put("Aragón_L",3);put("Aragón_D",0);
        put("Illes Balears_L",11);put("Illes Balears_D",0);
        put("Cantabria_L",4);put("Cantabria_D",0);
        put("Castilla y León_L",2);put("Castilla y León_D",0);
        put("La Rioja_L",2);put("La Rioja_D",0);
        put("Madrid_L",6);put("Madrid_D",0);
        put("Murcia_L",3);put("Murcia_D",0);
        put("Valencia_L",44);put("Valencia_D",0);
    }};*/
    public static final String[] AUTONOMIA={
            "Andalucía","Aragón","Asturias","Illes Balears",
            "Cantabria","Castilla-La Mancha","Castilla y León","Ceuta",
            "Extremadura","La Rioja","Madrid","Melilla","Murcia","Navarra","Valencia"};
    public static boolean isDouble(String valor){
        return valor.matches("^\\d+(\\d*|(.)\\d+)$");
    }
    public static boolean isVacia(String linea){
        return linea.matches(REGEX_LINEA_VACIA);
    }
}
