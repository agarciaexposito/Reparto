package modelo;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AdjudicacionMinisterio implements Serializable {
    @Serial
    private static final long serialVersionUID = 799624107826345233L;
    private String autonomia="";
    private int plazas=0;
    private boolean libre=false;
    private boolean euskera=false;
    private final List<AspiranteMinisterio> aspirantesMinisterio = new ArrayList<>();

    public AdjudicacionMinisterio(String autonomia, int plazas, boolean libre, boolean euskera) {
        this.autonomia = autonomia;
        this.plazas = plazas;
        this.libre = libre;
        this.euskera = euskera;
    }

    public boolean add(AspiranteMinisterio aspirante) {
        return aspirantesMinisterio.add(aspirante);
    }

    public List<AspiranteMinisterio> getAspirantesMinisterio() {
        return new ArrayList<>(aspirantesMinisterio);
    }

    public String getAutonomia() {
        return autonomia;
    }

    public int getPlazas() {
        return plazas;
    }

    public void setPlazas(int plazas) {
        this.plazas = plazas;
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


}
