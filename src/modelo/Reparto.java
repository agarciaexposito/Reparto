package modelo;

import java.util.ArrayList;
import java.util.List;

public class Reparto {
    private String autonomia="";
    private int plazas=0;
    private boolean libre=false;
    private boolean euskera=false;

    private List<Adjudicacion> adjudicaciones = new ArrayList<>();

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

    public boolean add(Aspirante aspirante){
        boolean anadido=false;
        if (!isCompleto())
            anadido=adjudicaciones.add(new Adjudicacion(adjudicaciones.size()+1,aspirante));
        return anadido;
    }

    public boolean isCompleto(){
        return plazas== adjudicaciones.size();
    }

    @Override
    public String toString() {
        String cad = String.format("Autonomía: %s\n",autonomia);
        cad+=String.format("Turno: %s\n",(libre)?"Libre":"Discapacitado");
        cad+=String.format("Plazas %d\n",plazas);
        if (euskera)
            cad+="Euskera\n";
        for (Adjudicacion adjudicacion: adjudicaciones) {
            cad += adjudicacion.toString()+"\n";
        }
        return cad;
    }
}
