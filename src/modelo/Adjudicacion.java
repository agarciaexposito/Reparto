package modelo;

public class Adjudicacion {
    private int pos=0;
    private Aspirante adjudicadoA=null;

    public Adjudicacion(int pos, Aspirante adjudicadoA) {
        this.pos = pos;
        this.adjudicadoA = adjudicadoA;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public Aspirante getAdjudicadoA() {
        return adjudicadoA;
    }

    public void setAdjudicadoA(Aspirante adjudicadoA) {
        this.adjudicadoA = adjudicadoA;
    }

    @Override
    public String toString() {
        return String.format(" %d. %s",pos,adjudicadoA);
    }
}
