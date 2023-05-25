package modelo;

import java.io.Serializable;

public class Adjudicacion implements Serializable {
    private final int pos;

    private final int numEleccion;
    private final char turno;
    private final Aspirante adjudicadoA;

    public Adjudicacion(int pos, Aspirante adjudicadoA,int numEleccion,char turno) {
        this.pos = pos;
        this.adjudicadoA = adjudicadoA;
        this.numEleccion = numEleccion;
        this.turno = turno;
    }

    public int getNumEleccion() {
        return numEleccion;
    }
    public boolean isNota11MayorIgual4_9(){
        return adjudicadoA.getNota11()>=4.9;
    }
    public boolean isNota11MayorIgual2_1(){
        return adjudicadoA.getNota11()>=2.1;
    }
    @Override
    public String toString() {
        return String.format(" %d. %s",pos,adjudicadoA);
    }

    public static String cabeceraCSV(){
        //return  "Posición, Num. Elección, " + Aspirante.cabeceraCSV()+"Elección por turno";
        return  "Posición; Num. Elección; " + Aspirante.cabeceraCSV()+"Elección por turno";
    }
    public String toCSV() {
        return String.format("%d; %d; %s; %c", pos, numEleccion, adjudicadoA.toCSV(),turno);
        //return String.format("%d, %d, %s, %c", pos, numEleccion, adjudicadoA.toCSV(),turno);
    }
}
