package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Reparto implements Serializable {
    private String autonomia="";
    private int plazas=0;
    private boolean libre=false;
    private boolean euskera=false;

    private final List<Adjudicacion> adjudicaciones = new ArrayList<>();

    public boolean isLibre() {
        return libre;
    }

    public void setLibre(boolean libre) {
        this.libre = libre;
    }

    public String getAutonomia() {
        return autonomia;
    }

    public void setAutonomia(String autonomia) {
        this.autonomia = autonomia;
    }

    public int getPlazas() {
        return plazas;
    }

    public void setPlazas(int plazas) {
        this.plazas = plazas;
    }


    public boolean isEuskera() {
        return euskera;
    }

    public void setEuskera(boolean euskera) {
        this.euskera = euskera;
    }

    public List<Adjudicacion> getAdjudicaciones() {
        return adjudicaciones;
    }
    public boolean add(Aspirante aspirante,Eleccion eleccion){
        boolean anadido=false;
        if (!isCompleto()) {
            anadido = adjudicaciones.add(new Adjudicacion(adjudicaciones.size() + 1, aspirante, eleccion.getOrden(), eleccion.isLibre()?'L':'D'));
            if (anadido)
                aspirante.setAsignado(true);
        }
        return anadido;
    }

    public boolean forceAdd(Aspirante aspirante,Eleccion eleccion){
       return adjudicaciones.add(new Adjudicacion(adjudicaciones.size() + 1, aspirante, eleccion.getOrden(), eleccion.isLibre()?'L':'D'));
    }

    public boolean isCompleto(){
        return plazas == adjudicaciones.size();
    }

    @Override
    public String toString() {
        StringBuilder cad = new StringBuilder(String.format("Autonomía: %s\n", autonomia));
        cad.append(String.format("Turno: %s\n", (libre) ? "Libre" : "Discapacitado"));
        cad.append(String.format("Plazas %d\n", plazas));
        if (euskera)
            cad.append("Euskera\n");
        for (Adjudicacion adjudicacion: adjudicaciones) {
            cad.append(adjudicacion.toString()).append("\n");
        }
        return cad.toString();
    }

}
