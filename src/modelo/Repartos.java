package modelo;

import com.google.gson.Gson;
import helper.Comun;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

 public class Repartos extends AbstractRepartos implements Serializable {
    private Aspirantes aspirantes;
    private Map<String,Reparto> autonomias = new TreeMap<>();


    public Repartos(Aspirantes aspirantes,Plazas plazas, String especialid) {
        super(aspirantes,plazas,especialid);
    }
    public boolean isCompleto(){
        for (String autonomia: autonomias.keySet()){
            if (!autonomias.get(autonomia).isCompleto())
                return false;
        }
        return true;
    }

     public boolean isEuskeraCompleto(){
         Reparto reparto = null;
         reparto = autonomias.get(Comun.NAVARRA_D_K);
         if (reparto!=null && !reparto.isCompleto())
             return false;
         reparto = autonomias.get(Comun.NAVARRA_L_K);
         if (reparto!=null && !reparto.isCompleto())
             return false;

         return true;
     }
    public void ejecuta(){ //no trabajo con las plazas de euskera ÑÑÑÑ
        aspirantes.ordenacionDeMayorAMenor();
        repartoDePlazasEuskera();
        repartoDePlazas();
        // Ultimo repaso a los discapacitados
        repartePlazasDiscapacitadosSinCubrir();
    }

    private void repartoDePlazasEuskera(){
        for (Aspirante aspirante:aspirantes.getAspirantes()){
            if (isEuskeraCompleto())
                break;
            if (!aspirante.isAsignado() && aspirante.isEuskera()) {
                // en este caso solo puede darse una unica autonomía: Navarra
                if (aspirante.getElecciones().size()>1)
                    System.out.printf("Euskera: %s\n",aspirante);
                for (Eleccion eleccion : aspirante.getElecciones()) { // esto esta en el orden natural de lectura que coincide con el orden de elección
                    if (!eleccion.isRechazado()) {
                        Reparto reparto;
                        if (!eleccion.isLibre()) { // si fuese discapacitado
                            reparto = autonomias.get(eleccion.getAutonomia() + "_D_K");
                            if (reparto != null && reparto.add(aspirante, eleccion))
                                break;
                        }
                        reparto = autonomias.get(eleccion.getAutonomia() + "_L_K");
                        if (reparto != null && reparto.add(aspirante, eleccion))
                            break;
                    }
                }
            }
        }
    }
     private void repartoDePlazas() {
         for (Aspirante aspirante:aspirantes.getAspirantes()){
             if (isCompleto())
                 break;
             if (!aspirante.isAsignado() && !aspirante.isEuskera()) {
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

}
