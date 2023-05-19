 package modelo;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

 public class Repartos {
    private final Aspirantes aspirantes;



    private final Map<String,Reparto> autonomias = new TreeMap<>();


    public Repartos(Aspirantes aspirantes,Plazas plazas, String especialid) {
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
    public void ejecuta(){ //no trabajo con las plazas de euskera ÑÑÑÑ
        aspirantes.ordenarPorNotaNacional();
        reparoDePlazas();
        //repartePlazasDeDiscapacitados();
        //repartePlazasTurnoLibre();
        // Ultimo repaso a los discapacitados
        repartePlazasDiscapacitadosSinCubrir();
    }

     private void reparoDePlazas() {
         for (Aspirante aspirante:aspirantes.getAspirantes()){
             if (!aspirante.isAsignado()) {
                 for (Eleccion eleccion : aspirante.getElecciones()) { // esto esta en el orden natural de lectura que coincide con el orden de elección
                     if (!eleccion.isRechazado()) {
                         Reparto reparto;
                         if (!eleccion.isLibre()) { // si fuese discapacitado
                             reparto = autonomias.get(eleccion.getAutonomia() + "_D");
                             if (reparto != null && reparto.add(aspirante, eleccion))
                                 break;
                         }
                         reparto = autonomias.get(eleccion.getAutonomia() + "_L");
                         if (reparto != null && reparto.add(aspirante, eleccion))
                             break;
                     }
                 }
             }
         }
     }

     private void repartePlazasDeDiscapacitados() {
         for (Aspirante aspirante:aspirantes.getAspirantes()){
             if (!aspirante.isAsignado()) {
                 for (Eleccion eleccion : aspirante.getElecciones()) {
                     if (!eleccion.isRechazado()) {
                         if (!eleccion.isLibre()) { // si fuese discapacitado
                             Reparto reparto = autonomias.get(eleccion.getAutonomia() + "_D");
                             if (reparto != null && reparto.add(aspirante, eleccion))
                                 break;
                             reparto = autonomias.get(eleccion.getAutonomia() + "_L");
                             if (reparto != null && reparto.add(aspirante, eleccion))
                                 break;
                         }
                     }
                 }
             }
         }
     }

     private void repartePlazasTurnoLibre() {
         for (Aspirante aspirante:aspirantes.getAspirantes()){
             if (!aspirante.isAsignado()) {
                 for (Eleccion eleccion : aspirante.getElecciones()) {
                     if (!eleccion.isRechazado()) {
                         Reparto reparto = autonomias.get(eleccion.getAutonomia() + "_L");
                         if (reparto != null && reparto.add(aspirante, eleccion))
                             break;
                         //hay que comprobar si quedan en turno D alguna plaza libre
                         reparto = autonomias.get(eleccion.getAutonomia() + "_D");
                         if (reparto != null && reparto.add(aspirante, eleccion))
                             break;
                     }
                 }
             }
         }
     }


     private void repartePlazasDiscapacitadosSinCubrir() {
        for (String autonomia: autonomias.keySet()){
            Reparto reparto = autonomias.get(autonomia);
            if (reparto!=null && !reparto.isCompleto()) {
                if (reparto.isLibre())
                    System.out.printf("La autonomía %s tiene plazas en turno libre sin cubrir\n",autonomia); // Pendiente de implementar la de Euskera ÑÑÑÑ
                else System.out.printf("la autonomía %s tiene plazas de discapacitado sin cubrir\n",autonomia); //seguramente no ocurra, pediente de implementar ÑÑÑ
            }
        }
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
}
