package modelo;

import java.io.Serializable;

public class Eleccion implements Serializable, Cloneable {
    private int orden=0;
    private float nota=0;
    private String autonomia="";
    private boolean libre=false;
    private boolean rechazado=false;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }

    public String getAutonomia() {
        return autonomia;
    }

    public void setAutonomia(String autonomia) {
        this.autonomia = autonomia;
    }

    public boolean isLibre() {
        return libre;
    }

    public void setLibre(boolean libre) {
        this.libre = libre;
    }

    public boolean isRechazado() {
        return rechazado;
    }

    public void setRechazado(boolean rechazado) {
        this.rechazado = rechazado;
    }

    @Override
    public String toString() {
        return String.format("%s%d %s %f %s",rechazado?"* ":"",orden,autonomia,nota,libre?"L":"D");
    }

    public boolean idDescartado(float notaNac){
        return rechazado || (notaNac!=nota);
    }
}
