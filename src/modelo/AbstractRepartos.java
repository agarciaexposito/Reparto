package modelo;

import com.google.gson.Gson;

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

public abstract class AbstractRepartos {
    protected Aspirantes aspirantes;
    protected Map<String,Reparto> autonomias = new TreeMap<>();
    public AbstractRepartos(Aspirantes aspirantes,Plazas plazas, String especialid) {
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
                this.autonomias.put(plaza.getAutonomia()+(plaza.isLibre()?"_L":"_D")+(plaza.isEuskera()?"_K":""), reparto);
            }
        }
    }
    public abstract void ejecuta();

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
    public void readSer(String file) throws Exception{
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(file))) {
            Object r = entrada.readObject();
            if (r instanceof AbstractRepartos) {
                this.autonomias = ((AbstractRepartos) r).autonomias;
                this.aspirantes = ((AbstractRepartos) r).aspirantes;
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
