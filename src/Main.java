
import modelo.Aspirantes;
import modelo.Plazas;
import modelo.Repartos;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static final String FICHEROS_PLAZAS_SER = "./ficheros/plazas.ser";
    public static final String FICHEROS_PLAZAS_TXT = "./ficheros/plazas.txt";

    public static void main(String[] args) {
        if (args.length==0){
            System.err.println("Ejecute pasando como parámetro al menos un archivo.txt");
            System.exit(-1);
        }
        Aspirantes aspirantes;
        Plazas plazas=new Plazas();
        
        try {
            if (Files.exists(Paths.get(FICHEROS_PLAZAS_SER)))
                plazas.readSer(FICHEROS_PLAZAS_SER);
            else {
                plazas.loadTXT(FICHEROS_PLAZAS_TXT);
                plazas.writeSer(FICHEROS_PLAZAS_SER);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
        //plazas.saveJSON("./ficheros/plazas.json");
        int cont=0;
        while (cont < args.length){
            String fichero=args[cont++];
            String especialidad=args[cont++];
            Path rutaArchivo = Paths.get(fichero).getParent();
            String nombreArchivo = Paths.get(fichero).getFileName().toString();
            aspirantes=new Aspirantes();
            try {
                aspirantes.loadTXT(fichero);
                String fileJSON = fichero.replaceAll("\\.txt$", ".json");
                aspirantes.saveJSON(fileJSON);

                Repartos repartos=new Repartos(aspirantes,plazas,especialidad);
                repartos.ejecuta();
                String fileReparto = rutaArchivo+"/Reparto_"+nombreArchivo.replaceAll("\\.txt$", ".json");
                repartos.saveJSON(fileReparto);
            } catch (Exception e) {
                System.err.println(e.getMessage());
                //System.exit(-1);
            }
        }
    }
}