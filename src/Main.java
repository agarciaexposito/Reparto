
import helper.Comun;
import helper.Util;
import modelo.Aspirantes;
import modelo.Plazas;
import modelo.Repartos;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public class Main {
    public static final String FICHEROS_PLAZAS_SER = "./data/plazas.ser";
    public static final String FICHEROS_ASPIRANTES_SER = "./data/aspirantes.ser";
    public static final String FICHEROS_REPARTOS_SER = "./data/repartos.ser";
    public static final String FICHEROS_PLAZAS_TXT = "./data/plazas.txt";
    public static final boolean generarJSON=false;
    public static final boolean generarSer=false;

    public static void main(String[] args) {
        if (args.length<=1){
            Path directoryPath;
            if (args.length==0)
                directoryPath = Paths.get(Util.DOWNLOAD_PATH);
            else directoryPath = Paths.get(args[0].trim());
            try {
                Files.walkFileTree(directoryPath, new SimpleFileVisitor<>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                        if (file.getFileName().toString().toLowerCase().endsWith(".pdf")) {
                            String fichero=file.getFileName().toString();
                            String especialidad=Comun.nombreArchivoSinExt(fichero).trim().substring(0,6);
                            reparto(directoryPath+"/"+fichero,especialidad);
                        }
                        return FileVisitResult.CONTINUE;
                    }
                });
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        } else {
            int cont = 0;
            while (cont < args.length) {
                String fichero = args[cont++];
                String especialidad = args[cont++];
                reparto(fichero, especialidad);
            }
        }
    }

    private static void reparto(String fichero, String especialidad) {
        Plazas plazas = getPlazas();
        Aspirantes aspirantes=new Aspirantes();
        try {
            if (Comun.extension(fichero).equalsIgnoreCase("pdf")){
                Comun.pdfToTxt(fichero);
                fichero = fichero.toLowerCase().replaceAll("\\.pdf$", ".txt");
            }
            aspirantes.loadTXT(fichero);
            if (generarJSON)
                aspirantesJSON(aspirantes, fichero);
            if (generarSer)
                aspirantesSer(aspirantes);
            Repartos repartos=new Repartos(aspirantes, plazas, especialidad);
            repartos.ejecuta();
            Path file = Paths.get(fichero);
            Path rutaArchivo = file.getParent();
            String nombreArchivo = file.getFileName().toString();
            List<String> ficherosGeneradosCSV=repartosCSV(rutaArchivo, nombreArchivo, repartos);
            repartosExcel(rutaArchivo, nombreArchivo,ficherosGeneradosCSV);
            if (generarJSON) //Pa manuel
                repartosJSON(rutaArchivo, nombreArchivo, repartos);
            if (generarSer)
                repartosSer(repartos);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            //System.exit(-1);
        }
    }

    private static void aspirantesSer(Aspirantes aspirantes) throws IOException {
        aspirantes.writeSer(FICHEROS_ASPIRANTES_SER);
    }

    private static Plazas getPlazas() {
        Plazas plazas=new Plazas();
        try {
            if (Files.exists(Paths.get(FICHEROS_PLAZAS_SER)))
                plazas.readSer(FICHEROS_PLAZAS_SER);
            else {
                plazas.loadTXT(FICHEROS_PLAZAS_TXT);
                plazas.writeSer(FICHEROS_PLAZAS_SER);
            }
            creaCarpetaFicheros();
            if (generarJSON)
                plazas.saveJSON("./ficheros/plazas.json");

        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
        return plazas;
    }

    private static void aspirantesJSON(Aspirantes aspirantes, String fichero) throws IOException {
        String fileJSON = fichero.replaceAll("\\.txt$", ".json");
        aspirantes.saveJSON(fileJSON);
    }

    private static List<String> repartosCSV(Path rutaArchivo, String nombreArchivo, Repartos repartos) throws IOException {
        String fileRepartoCSV = rutaArchivo +"/"+ nombreArchivo.replaceAll("\\.txt$", ".csv");
        return repartos.saveCSV(fileRepartoCSV);
    }

    private static void repartosJSON(Path rutaArchivo, String nombreArchivo, Repartos repartos) throws IOException {
        //String fileRepartoJSON = rutaArchivo +"/Reparto_"+ nombreArchivo.replaceAll("\\.txt$", ".json");
        // modificado para Manuel
        String fileRepartoJSON = rutaArchivo +"/"+ nombreArchivo.trim().substring(0,6)+".json";
        repartos.saveJSON(fileRepartoJSON);
    }
    private static void repartosSer(Repartos repartos) throws IOException {
        repartos.writeSer(FICHEROS_REPARTOS_SER);
    }
    public static void repartosExcel(Path rutaArchivo, String nombreArchivo,List<String> archivosCSV) throws IOException{
        String fileRepartoExcel = rutaArchivo +"/Reparto_"+ nombreArchivo.replaceAll("\\.txt$", ".xlsx");
        Comun.csvToExcel(fileRepartoExcel,archivosCSV);
    }
    public static void creaCarpetaFicheros() throws IOException {
        Comun.creaCarpeta("./ficheros");
    }
}