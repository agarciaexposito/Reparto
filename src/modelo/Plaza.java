package modelo;

import java.io.Serial;
import java.io.Serializable;

public class Plaza implements Serializable {
    @Serial
    private static final long serialVersionUID = 2921508854173464819L;
    private String autonomia="";
    private int especialidad=500000;
    private String descripcion="";
    private boolean libre=false;
    private boolean euskera=false;

    private int numero=0;

    public String getAutonomia() {
        return autonomia;
    }

    public void setAutonomia(String autonomia) {
        this.autonomia = autonomia;
    }

    public int getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(int especialidad) {
        this.especialidad = especialidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isLibre() {
        return libre;
    }

    public void setLibre(boolean libre) {
        this.libre = libre;
    }

    public boolean isEuskera() {
        return euskera;
    }

    public void setEuskera(boolean euskera) {
        this.euskera = euskera;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return String.format("%s %d %s %s %d %s",autonomia,especialidad,autonomia,(libre)?"L":"D",numero,(euskera)?"EUSKERA":"");
    }
}
