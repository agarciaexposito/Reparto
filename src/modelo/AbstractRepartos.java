package modelo;

import com.google.gson.Gson;
import helper.Comun;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public abstract class AbstractRepartos implements Serializable{

    @Serial
    private static final long serialVersionUID = -2637206352538027355L;
    protected String especialidad;
    protected Aspirantes aspirantes;
    protected Map<String,Reparto> autonomias = new TreeMap<>();
    public AbstractRepartos(Aspirantes aspirantes,Plazas plazas, String especialid) {
        this.especialidad = especialid;
        this.aspirantes = aspirantes;
        int especialidad=Integer.parseInt(especialid);
        plazas.ordenarPorEspecialidad();
        for (Plaza plaza:plazas.getPlazas()){
            if (plaza.getEspecialidad()==especialidad) {
                Reparto reparto = new Reparto();
                reparto.setLibre(plaza.isLibre());
                reparto.setAutonomia(plaza.getAutonomia());
                reparto.setPlazas(plaza.getNumero());
                reparto.setEuskera(plaza.isEuskera());
                this.autonomias.put(Comun.keyAutonomia(plaza.getAutonomia(),plaza.isLibre(),plaza.isEuskera()), reparto);
            }
        }
    }
    public abstract void ejecuta();

    public Map<String, Reparto> getAutonomias() {
        return autonomias;
    }

    @Override
    public String toString() {
        StringBuilder cad= new StringBuilder();
        for (String autonomia: autonomias.keySet()) {
            Reparto reparto = autonomias.get(autonomia);
            cad.append(reparto.toString());
        }
        return cad.toString();
    }

    public void saveJSON(String file) throws IOException {
        Writer writer = Files.newBufferedWriter(Paths.get(file));
        // convert books object to JSON file
        new Gson().toJson(autonomias, writer);
        // close writer
        writer.close();
    }
    public List<String> saveCSVHojaResumen(String file) throws IOException {
        List<String> ficherosGenerados= new ArrayList<>();
        int total49=0;
        int total21=0;
        int totalPlazas=0;
        Path ficheroResumen= crearResumenCSV(file);
        ficherosGenerados.add(ficheroResumen.toString());
        for (String autonomia:autonomias.keySet()) {
            int cont49=0;
            int cont21=0;
            Reparto reparto = autonomias.get(autonomia);
            int contPlazas=reparto.getPlazas();
            Path fichero=crearArchivoCSV(file,reparto);
            for (Adjudicacion adjudicacion:reparto.getAdjudicaciones()) {
                if (adjudicacion.getNumEleccion()==1){
                    if (adjudicacion.isNota11MayorIgual4_9())
                        cont49++;
                    if (adjudicacion.isNota11MayorIgual2_1())
                        cont21++;
                }
                String lineaCSV = adjudicacion.toCSV()+"\n";
                Files.write(fichero, lineaCSV.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
            }
            total49+=cont49;
            total21+=cont21;
            totalPlazas+=contPlazas;
            ficherosGenerados.add(fichero.toString());
            String lineaResumen = String.format("%s; %d; %d; %d\n",autonomia,contPlazas,cont49,cont21);
            Files.write(ficheroResumen, lineaResumen.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        }

        String nombreArchivo = ficheroResumen.getFileName().toString();
        String soloNombreArchivo = nombreArchivo.substring(0, nombreArchivo.lastIndexOf("."));
        String lineaResumen = String.format("%s; %d; %d; %d\n",soloNombreArchivo,totalPlazas,total49,total21);
        Files.write(ficheroResumen, lineaResumen.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        return ficherosGenerados;
    }

    public List<String> saveCSV(String file) throws IOException {
        List<String> ficherosGenerados= new ArrayList<>();
        for (String autonomia:autonomias.keySet()) {
            Reparto reparto = autonomias.get(autonomia);
            Path fichero=crearArchivoCSV(file,reparto);
            for (Adjudicacion adjudicacion:reparto.getAdjudicaciones()) {
                String lineaCSV = adjudicacion.toCSV()+"\n";
                Files.write(fichero, lineaCSV.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
            }
            ficherosGenerados.add(fichero.toString());
        }
        return ficherosGenerados;
    }
    public static Path crearArchivoCSV(String file, Reparto reparto) throws IOException {
        String cabecera=Adjudicacion.cabeceraCSV()+"\n";
        Path rutaArchivo = Paths.get(file).getParent();
        String nombreArchivo = Paths.get(file).getFileName().toString();
        String soloNombreArchivo = nombreArchivo.substring(0, nombreArchivo.lastIndexOf("."));
        nombreArchivo = String.format("%s-%s%s (%d).csv",reparto.getAutonomia(),reparto.isLibre()?"L":"D",reparto.isEuskera()?"-K":"",reparto.getPlazas());
        Path carpeta=Paths.get(rutaArchivo+"/"+soloNombreArchivo+"/");
        if (!Files.exists(carpeta))
            Files.createDirectories(carpeta);
        Path fichero=Paths.get(rutaArchivo+"/"+soloNombreArchivo+"/"+nombreArchivo);
        Files.deleteIfExists(fichero);
        Files.write(fichero, cabecera.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
        return fichero;
    }

    public static Path crearResumenCSV(String file) throws IOException {
        String cabecera=String.format("%s; %s; %s; %s\n","AUTONOMÍA", "D.A. Sexta","D.A. Octava","Directiva 1999/70/CE");
        Path rutaArchivo = Paths.get(file).getParent();
        String nombreArchivo = Paths.get(file).getFileName().toString();
        String soloNombreArchivo = nombreArchivo.substring(0, nombreArchivo.lastIndexOf("."));
        nombreArchivo = soloNombreArchivo+".csv";
        Path carpeta=Paths.get(rutaArchivo+"/"+soloNombreArchivo+"/");
        if (!Files.exists(carpeta))
            Files.createDirectories(carpeta);
        Path fichero=Paths.get(rutaArchivo+"/"+soloNombreArchivo+"/"+nombreArchivo);
        Files.deleteIfExists(fichero);
        Files.write(fichero, cabecera.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
        return fichero;
    }
    public static AbstractRepartos readSer(String especialidad) throws Exception{
		String file = Comun.PATH_RESULT +"/"+especialidad.trim()+"-adj_prov.ser";
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(file))) {
            Object r = entrada.readObject();
            if (r instanceof AbstractRepartos) {
                return (AbstractRepartos) r;
            }
            else throw new Exception(String.format("El archivo %s no contiene los repartos, bórrelo",file));
        }
    }
    public void writeSer(String file) throws IOException{
        try (ObjectOutputStream salida =new ObjectOutputStream(new FileOutputStream(file))){
            salida.writeObject(this);
            salida.flush();
        }
    }
}
