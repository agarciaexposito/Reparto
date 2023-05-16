 package modelo;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

 public class Repartos {
    private Aspirantes aspirantes;
    private Plazas plazas;
    private int especialidad;

    private Map<String,Reparto> autonomias = new TreeMap<>();


    public Repartos(Aspirantes aspirantes,Plazas plazas, String especialidad) {
        this.aspirantes = aspirantes;
        this.plazas=plazas;
        this.especialidad=Integer.parseInt(especialidad);
        plazas.ordenarPorEspecialidad();
        for (Plaza plaza:plazas.getPlazas()){
            if (plaza.getEspecialidad()==this.especialidad) {
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
        for (Aspirante aspirante:aspirantes.getAspirantes()){
            for (Eleccion eleccion:aspirante.getElecciones()) {
                if (!eleccion.isRechazado()){
                    Reparto reparto=autonomias.get(eleccion.getAutonomia()+"_L");
                    if (reparto!=null && reparto.add(aspirante))
                        break;
                    else if (!eleccion.isLibre()) { // si fuese discapacitado
                        reparto = autonomias.get(eleccion.getAutonomia() + "_D");
                        if (reparto != null && reparto.add(aspirante))
                            break;
                    }
                }
            }
        }
        // Ultimo repaso a los discapacitados
        repartePlazasDiscapacitadosSinCubrir();

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
        String cad="";
        for (String autonomia: autonomias.keySet()) {
            Reparto reparto = autonomias.get(autonomia);
            cad+=reparto.toString();
        }
        return cad;
    }

    public void saveJSON(String file) throws IOException {
        Writer writer = Files.newBufferedWriter(Paths.get(file));
        // convert books object to JSON file
        new Gson().toJson(autonomias, writer);
        // close writer
        writer.close();
    }
}
