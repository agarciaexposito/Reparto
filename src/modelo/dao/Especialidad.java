package modelo.dao;

public class Especialidad {
    private int id;
    private String nombre;
    private int cuerpo;
    private int especialidad;

    // Constructor
    public Especialidad(int id, String nombre, int cuerpo, int especialidad) {
        this.id = id;
        this.nombre = nombre;
        this.cuerpo = cuerpo;
        this.especialidad = especialidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(int cuerpo) {
        this.cuerpo = cuerpo;
    }

    public int getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(int especialidad) {
        this.especialidad = especialidad;
    }

    // Getters y setters (opcional)
    // ...
}
