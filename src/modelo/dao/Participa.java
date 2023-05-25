package modelo.dao;

public class Participa {
    private int id;
    private int idAspirante;
    private int idEspecialidad;
    private double notaNacional;
    private double nota1;
    private double nota11;
    private double nota12;
    private double nota13;
    private double nota14;
    private double nota2;
    private double nota21;
    private double nota22;
    private double nota221;
    private double nota222;
    private double nota223;
    private double nota23;
    private double nota231;
    private double nota232;
    private double nota24;
    private double nota241;
    private double nota242;
    private double nota243;
    private double nota244;
    private double nota245;
    private double nota25;
    private double nota3;
    private double nota31;
    private double nota32;
    private int anos;
    private int meses;
    private int dias;
    private char euskera;
    private char asignado;

    // Constructor
    public Participa(int id, int idAspirante, int idEspecialidad, double notaNacional, double nota1, double nota11, double nota12, double nota13, double nota14, double nota2, double nota21, double nota22, double nota221, double nota222, double nota223, double nota23, double nota231, double nota232, double nota24, double nota241, double nota242, double nota243, double nota244, double nota245, double nota25, double nota3, double nota31, double nota32, int anos, int meses, int dias, char euskera, char asignado) {
        this.id = id;
        this.idAspirante = idAspirante;
        this.idEspecialidad = idEspecialidad;
        this.notaNacional = notaNacional;
        this.nota1 = nota1;
        this.nota11 = nota11;
        this.nota12 = nota12;
        this.nota13 = nota13;
        this.nota14 = nota14;
        this.nota2 = nota2;
        this.nota21 = nota21;
        this.nota22 = nota22;
        this.nota221 = nota221;
        this.nota222 = nota222;
        this.nota223 = nota223;
        this.nota23 = nota23;
        this.nota231 = nota231;
        this.nota232 = nota232;
        this.nota24 = nota24;
        this.nota241 = nota241;
        this.nota242 = nota242;
        this.nota243 = nota243;
        this.nota244 = nota244;
        this.nota245 = nota245;
        this.nota25 = nota25;
        this.nota3 = nota3;
        this.nota31 = nota31;
        this.nota32 = nota32;
        this.anos = anos;
        this.meses = meses;
        this.dias = dias;
        this.euskera = euskera;
        this.asignado = asignado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAspirante() {
        return idAspirante;
    }

    public void setIdAspirante(int idAspirante) {
        this.idAspirante = idAspirante;
    }

    public int getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(int idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    public double getNotaNacional() {
        return notaNacional;
    }

    public void setNotaNacional(double notaNacional) {
        this.notaNacional = notaNacional;
    }

    public double getNota1() {
        return nota1;
    }

    public void setNota1(double nota1) {
        this.nota1 = nota1;
    }

    public double getNota11() {
        return nota11;
    }

    public void setNota11(double nota11) {
        this.nota11 = nota11;
    }

    public double getNota12() {
        return nota12;
    }

    public void setNota12(double nota12) {
        this.nota12 = nota12;
    }

    public double getNota13() {
        return nota13;
    }

    public void setNota13(double nota13) {
        this.nota13 = nota13;
    }

    public double getNota14() {
        return nota14;
    }

    public void setNota14(double nota14) {
        this.nota14 = nota14;
    }

    public double getNota2() {
        return nota2;
    }

    public void setNota2(double nota2) {
        this.nota2 = nota2;
    }

    public double getNota21() {
        return nota21;
    }

    public void setNota21(double nota21) {
        this.nota21 = nota21;
    }

    public double getNota22() {
        return nota22;
    }

    public void setNota22(double nota22) {
        this.nota22 = nota22;
    }

    public double getNota221() {
        return nota221;
    }

    public void setNota221(double nota221) {
        this.nota221 = nota221;
    }

    public double getNota222() {
        return nota222;
    }

    public void setNota222(double nota222) {
        this.nota222 = nota222;
    }

    public double getNota223() {
        return nota223;
    }

    public void setNota223(double nota223) {
        this.nota223 = nota223;
    }

    public double getNota23() {
        return nota23;
    }

    public void setNota23(double nota23) {
        this.nota23 = nota23;
    }

    public double getNota231() {
        return nota231;
    }

    public void setNota231(double nota231) {
        this.nota231 = nota231;
    }

    public double getNota232() {
        return nota232;
    }

    public void setNota232(double nota232) {
        this.nota232 = nota232;
    }

    public double getNota24() {
        return nota24;
    }

    public void setNota24(double nota24) {
        this.nota24 = nota24;
    }

    public double getNota241() {
        return nota241;
    }

    public void setNota241(double nota241) {
        this.nota241 = nota241;
    }

    public double getNota242() {
        return nota242;
    }

    public void setNota242(double nota242) {
        this.nota242 = nota242;
    }

    public double getNota243() {
        return nota243;
    }

    public void setNota243(double nota243) {
        this.nota243 = nota243;
    }

    public double getNota244() {
        return nota244;
    }

    public void setNota244(double nota244) {
        this.nota244 = nota244;
    }

    public double getNota245() {
        return nota245;
    }

    public void setNota245(double nota245) {
        this.nota245 = nota245;
    }

    public double getNota25() {
        return nota25;
    }

    public void setNota25(double nota25) {
        this.nota25 = nota25;
    }

    public double getNota3() {
        return nota3;
    }

    public void setNota3(double nota3) {
        this.nota3 = nota3;
    }

    public double getNota31() {
        return nota31;
    }

    public void setNota31(double nota31) {
        this.nota31 = nota31;
    }

    public double getNota32() {
        return nota32;
    }

    public void setNota32(double nota32) {
        this.nota32 = nota32;
    }

    public int getAnos() {
        return anos;
    }

    public void setAnos(int anos) {
        this.anos = anos;
    }

    public int getMeses() {
        return meses;
    }

    public void setMeses(int meses) {
        this.meses = meses;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public char getEuskera() {
        return euskera;
    }

    public void setEuskera(char euskera) {
        this.euskera = euskera;
    }

    public char getAsignado() {
        return asignado;
    }

    public void setAsignado(char asignado) {
        this.asignado = asignado;
    }

    // Getters y setters (opcional)
    // ...
}
