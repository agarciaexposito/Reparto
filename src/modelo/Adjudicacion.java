package modelo;

public class Adjudicacion {
    private int pos;

    private int numEleccion;
    private Aspirante adjudicadoA;

    public Adjudicacion(int pos, Aspirante adjudicadoA,int numEleccion) {
        this.pos = pos;
        this.adjudicadoA = adjudicadoA;
        this.numEleccion = numEleccion;
    }


    @Override
    public String toString() {
        return String.format(" %d. %s",pos,adjudicadoA);
    }

    public static String cabeceraCSV(){
        return  "Posición, Num. Elección, " + Aspirante.cabeceraCSV();
    }
    public String toCSV() {
        return String.format("%d, %d, %s", pos, numEleccion, adjudicadoA.toCSV());
    }
}
