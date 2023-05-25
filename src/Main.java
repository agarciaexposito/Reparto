
import helper.Comun;
import helper.Util;
import modelo.*;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Set;

public class Main {
    public static final String FICHEROS_PLAZAS_SER = "./data/plazas.ser";
    public static final String FICHEROS_ASPIRANTES_SER = "./data/aspirantes.ser";
    public static final String FICHEROS_REPARTOS_SER = "./data/repartos.ser";
    public static final String FICHEROS_PLAZAS_TXT = "./data/plazas.txt";
    public static final boolean generarJSON=false;
    public static final boolean generarSer=false;
    private static int numFile=0;
    private static final boolean generarReparto =false;  // con falso hago una ordenación de todos
    private static final boolean hojaResumen = true; // para mostrar una hoja resumen con la vulnearción de las disposiciones tanto la D.A. Sexta, D.A. Octava como Directiva 1999/70/CE



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
                            carga(directoryPath+"/"+fichero);
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
                carga(fichero);
            }
        }
    }

    private static void carga(String file) {
        try {
            System.out.printf("%d$%s\n",numFile++,file);
            if (Comun.extension(file).equalsIgnoreCase("pdf")){
                String fileOut=file.toLowerCase().replaceAll("\\.pdf$", "-Temp.txt");
                Comun.pdfToTxt(file,fileOut);
                file = fileOut;
            }
            Set<String> ficheros=Comun.troceaTXTEnEspecialidades(file);
            for (String fichero:ficheros) {
                String especialidad=Comun.nombreArchivoSinExt(fichero).trim().substring(0,6);
                cargaEspecialidad(fichero, especialidad);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            //System.exit(-1);
        }
    }

    private static void cargaEspecialidad(String fichero, String especialidad) throws IOException {
        Plazas plazas = getPlazas();
        Aspirantes aspirantes=new Aspirantes();
        aspirantes.loadTXT(fichero);
        if (generarJSON)
            aspirantesJSON(aspirantes, fichero);
        if (generarSer)
            aspirantesSer(aspirantes);
        AbstractRepartos repartos;
        if (generarReparto)
            repartos = new Repartos(aspirantes, plazas, especialidad);
        else repartos = new Distribucion(aspirantes,plazas,especialidad);
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

    private static List<String> repartosCSV(Path rutaArchivo, String nombreArchivo, AbstractRepartos repartos) throws IOException {
        String fileRepartoCSV = rutaArchivo +"/"+ nombreArchivo.replaceAll("\\.txt$", ".csv");
        if (hojaResumen)
            return repartos.saveCSVHojaResumen(fileRepartoCSV);
        else return repartos.saveCSV(fileRepartoCSV);
    }

    private static void repartosJSON(Path rutaArchivo, String nombreArchivo, AbstractRepartos repartos) throws IOException {
        //String fileRepartoJSON = rutaArchivo +"/Reparto_"+ nombreArchivo.replaceAll("\\.txt$", ".json");
        // modificado para Manuel
        String fileRepartoJSON = rutaArchivo +"/"+ nombreArchivo.trim().substring(0,6)+".json";
        repartos.saveJSON(fileRepartoJSON);
    }
    private static void repartosSer(AbstractRepartos repartos) throws IOException {
        repartos.writeSer(FICHEROS_REPARTOS_SER);
    }
    public static void repartosExcel(Path rutaArchivo, String nombreArchivo,List<String> archivosCSV) throws IOException{
        String fileRepartoExcel;
        if (generarReparto)
            fileRepartoExcel = rutaArchivo +" /Reparto_" + nombreArchivo.replaceAll("\\.txt$", ".xlsx");
        else fileRepartoExcel = rutaArchivo + "/" + nombreArchivo.replaceAll("\\.txt$", ".xlsx");
        Comun.csvToExcel(fileRepartoExcel,archivosCSV,";");
    }
    public static void creaCarpetaFicheros() throws IOException {
        Comun.creaCarpeta("./ficheros");
    }
}