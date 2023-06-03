package modelo;

import java.io.Serial;
import java.io.Serializable;

public class AspiranteMinisterio implements Serializable {
    @Serial
    private static final long serialVersionUID = 1070412540609894375L;
    private int especialidad;
    private boolean libre;
    private int pos;
    private String dni;
    private String comunOrigen;
    private String nombre;
    private float nota;
    private boolean euskera;
    public int getEspecialidad() {
        return especialidad;
    }

    public boolean isLibre() {
        return libre;
    }

    public int getPos() {
        return pos;
    }

    public String getDni() {
        return dni;
    }

    public String getComunOrigen() {
        return comunOrigen;
    }

    public String getNombre() {
        return nombre;
    }

    public float getNota() {
        return nota;
    }

    public boolean isEuskera() {
        return euskera;
    }

    @Override
    public String toString() {
        return "Pos=" + pos +
                ", DNI='" + dni + '\'' +
                ", Origen='" + comunOrigen + '\'' +
                ", nombre='" + nombre + '\'' +
                ", nota=" + nota +
                ", euskera=" + euskera +
                '}';
    }
    public AspiranteMinisterio(int especialidad, boolean libre, int pos, String dni,String comunOrigen, String nombre, float nota, boolean euskera) {
        this.especialidad = especialidad;
        this.libre = libre;
        this.pos = pos;
        this.dni = dni;
        this.comunOrigen = comunOrigen;
        this.nombre = nombre;
        this.nota = nota;
        this.euskera = euskera;
    }
}
