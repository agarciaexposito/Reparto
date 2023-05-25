package modelo.dao;

public class Elije {
    private int id;
    private int idParticipa;
    private int idAutonomia;
    private int orden;
    private int turno;
    private char rechazado;

    // Constructor
    public Elije(int id, int idParticipa, int idAutonomia, int orden, int turno, char rechazado) {
        this.id = id;
        this.idParticipa = idParticipa;
        this.idAutonomia = idAutonomia;
        this.orden = orden;
        this.turno = turno;
        this.rechazado = rechazado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdParticipa() {
        return idParticipa;
    }

    public void setIdParticipa(int idParticipa) {
        this.idParticipa = idParticipa;
    }

    public int getIdAutonomia() {
        return idAutonomia;
    }

    public void setIdAutonomia(int idAutonomia) {
        this.idAutonomia = idAutonomia;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    public char getRechazado() {
        return rechazado;
    }

    public void setRechazado(char rechazado) {
        this.rechazado = rechazado;
    }
}
