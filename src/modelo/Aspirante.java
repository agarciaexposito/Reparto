package modelo;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Aspirante implements Serializable, Cloneable {
    @Serial
    private static final long serialVersionUID = -5105395931741959845L;
    private String dni="";
    private String nombre="";
    private float notaNacional=0;
    private float nota1=0;
    private float nota11=0;
    private float nota12=0;
    private float nota13=0;
    private float nota14=0;
    private float nota2=0;

    private float nota21=0;
    private float nota22=0;
    private float nota221=0;
    private float nota222=0;
    private float nota223=0;
    private float nota23=0;
    private float nota231=0;
    private float nota232=0;
    private float nota24=0;
    private float nota241=0;
    private float nota242=0;
    private float nota243=0;
    private float nota244=0;
    private float nota245=0;
    private float nota25=0;
    private float nota3=0;
    private float nota31=0;
    private float nota32=0;
    private int anos=0;
    private int meses=0;
    private int dias=0;
    private List<Eleccion> elecciones = new ArrayList<>();

    private boolean euskera =false;
    private boolean asignado=false;

    public String getDni() {
        return dni;
    }


    @Override
    public Object clone() throws CloneNotSupportedException {
        Aspirante aspirante = (Aspirante) super.clone();
        aspirante.elecciones = new ArrayList<>();
        for (Eleccion eleccion:elecciones) {
            aspirante.elecciones.add((Eleccion) eleccion.clone());
        }
        return aspirante;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getNotaNacional() {
        return notaNacional;
    }

    public void setNotaNacional(float notaNacional) {
        this.notaNacional = notaNacional;
    }

    public float getNota1() {
        return nota1;
    }

    public void setNota1(float nota1) {
        this.nota1 = nota1;
    }

    public float getNota11() {
        return nota11;
    }

    public void setNota11(float nota11) {
        this.nota11 = nota11;
    }

    public float getNota12() {
        return nota12;
    }

    public void setNota12(float nota12) {
        this.nota12 = nota12;
    }

    public float getNota13() {
        return nota13;
    }

    public void setNota13(float nota13) {
        this.nota13 = nota13;
    }

    public float getNota14() {
        return nota14;
    }

    public void setNota14(float nota14) {
        this.nota14 = nota14;
    }

    public float getNota2() {
        return nota2;
    }
    public void setNota2(float nota2) {
        this.nota2 = nota2;
    }
    public float getNota21() {
        return nota21;
    }

    public void setNota21(float nota21) {
        this.nota21 = nota21;
    }

    public float getNota22() {
        return nota22;
    }

    public void setNota22(float nota22) {
        this.nota22 = nota22;
    }

    public float getNota221() {
        return nota221;
    }

    public void setNota221(float nota221) {
        this.nota221 = nota221;
    }

    public float getNota222() {
        return nota222;
    }

    public void setNota222(float nota222) {
        this.nota222 = nota222;
    }

    public float getNota223() {
        return nota223;
    }

    public void setNota223(float nota223) {
        this.nota223 = nota223;
    }

    public float getNota23() {
        return nota23;
    }

    public void setNota23(float nota23) {
        this.nota23 = nota23;
    }

    public float getNota231() {
        return nota231;
    }

    public void setNota231(float nota231) {
        this.nota231 = nota231;
    }

    public float getNota232() {
        return nota232;
    }

    public void setNota232(float nota232) {
        this.nota232 = nota232;
    }

    public float getNota24() {
        return nota24;
    }

    public void setNota24(float nota24) {
        this.nota24 = nota24;
    }

    public float getNota241() {
        return nota241;
    }

    public void setNota241(float nota241) {
        this.nota241 = nota241;
    }

    public float getNota242() {
        return nota242;
    }

    public void setNota242(float nota242) {
        this.nota242 = nota242;
    }

    public float getNota243() {
        return nota243;
    }

    public void setNota243(float nota243) {
        this.nota243 = nota243;
    }

    public float getNota244() {
        return nota244;
    }

    public void setNota244(float nota244) {
        this.nota244 = nota244;
    }

    public float getNota245() {
        return nota245;
    }

    public void setNota245(float nota245) {
        this.nota245 = nota245;
    }

    public float getNota25() {
        return nota25;
    }

    public void setNota25(float nota25) {
        this.nota25 = nota25;
    }



    public float getNota3() {
        return nota3;
    }

    public void setNota3(float nota3) {
        this.nota3 = nota3;
    }

    public float getNota31() {
        return nota31;
    }

    public void setNota31(float nota31) {
        this.nota31 = nota31;
    }

    public float getNota32() {
        return nota32;
    }

    public void setNota32(float nota32) {
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

    public boolean isAsignado() {
        return asignado;
    }

    public void setAsignado(boolean asignado) {
        this.asignado = asignado;
    }

    public boolean isEuskera() {
        return euskera;
    }

    public void setEuskera(boolean euskera) {
        this.euskera = euskera;
    }

    public List<Eleccion> getElecciones() {
        return new ArrayList<>(elecciones); // por qué hice una copia preventiva??
        // ahora tengo que comentarla para poder hacer el reparto de los discapacitados pues tengo que borrarlo
        //return elecciones;
    }

    public void add(Eleccion eleccion){
        elecciones.add(eleccion);
    }
    public String info() {
        StringBuilder cad = new StringBuilder(String.format("%f %s %s\n", notaNacional, dni, nombre));
        return cad.toString();
    }
    @Override
    public String toString() {
        StringBuilder cad = new StringBuilder(String.format("%f %s %s 1: %f 2: %f 3: %f\n", notaNacional, dni, nombre, nota1, nota2, nota3));
        cad.append(String.format("1.1: %f 1.2: %f 1.3: %f 1.4 %f\n", nota11, nota12, nota13, nota14));
        cad.append(String.format("2.1: %f 2.2: %f 2.3: %f 2.4 %f 2.5 %f\n", nota21, nota22, nota23, nota24, nota25));
        cad.append(String.format("3.1: %f 3.2: %f\n", nota31, nota32));
        cad.append(String.format("Años %d meses: %d días: %d\n", anos, meses, dias));
        cad.append(String.format("Concursa por Euskera: %s\n", euskera ?"SI":"no"));
        cad.append(String.format("Obtiene plaza: %s\n", asignado?"SI":"no"));
        for (Eleccion eleccion:elecciones){
            cad.append(eleccion.toString()).append("\n");
        }
        return cad.toString();
    }

    public static String cabeceraCSV(){
        return "Nota; DNI; NOMBRE; AÑOS; MESES; DÍAS; " +
                "Nota 1; 1.1; 1.2; 1.3; 1.4; " +
                "Nota 2; 2.1; 2.2; 2.2.1; 2.2.2; 2.2.3; " +
                " 2.3; 2.3.1; 2.3.2; " +
                " 2.4; 2.4.1; 2.4.2; 2.4.3; 2.4.4; 2.4.5; " +
                " 2.5; " +
                "Nota 3; 3.1; 3.2; EUSKERA";
        /*return "Nota, DNI, NOMBRE, AÑOS, MESES, DÍAS, " +
                "Nota 1, 1.1, 1.2, 1.3, 1.4, " +
                "Nota 2, 2.1, 2.2, 2.2.1, 2.2.2, 2.2.3, " +
                " 2.3, 2.3.1, 2.3.2, " +
                " 2.4, 2.4.1, 2.4.2, 2.4.3, 2.4.4, 2.4.5, " +
                " 2.5, " +
                "Nota 3, 3.1, 3.2, EUSKERA";*/
    }
    public String toCSV(){
        return String.format("%.4f; %s; %s; %d; %d; %d; " +
                        "%.4f; %.4f; %.4f; %.4f; %.4f; " +
                        "%.4f; %.4f; %.4f; %.4f; %.4f; %.4f; " +
                        "%.4f; %.4f; %.4f; "+
                        "%.4f; %.4f; %.4f; %.4f; %.4f; %.4f; " +
                        "%.4f; %.4f; %.4f; %.4f; %s",
                notaNacional,dni,nombre,anos,meses,dias,
                nota1,nota11,nota12,nota13,nota14,
                nota2,nota21,nota22,nota221,nota222,nota223,
                nota23, nota231,nota232,
                nota24, nota241, nota242, nota243, nota244, nota245,
                nota25, nota3,nota31,nota32, euskera?"SI":"no");
        /*return String.format("%s, %s, %s, %d, %d, %d, " +
                        "%s, %s, %s, %s, %s, " +
                        "%s, %s, %s, %s, %s, %s, " +
                        "%s, %s, %s, "+
                        "%s, %s, %s, %s, %s, %s, " +
                        "%s, %s, %s, %s, %s",
                notaConPunto(notaNacional),dni,nombre.replace(',','.'),anos,meses,dias,
                notaConPunto(nota1),notaConPunto(nota11),notaConPunto(nota12),notaConPunto(nota13),notaConPunto(nota14),
                notaConPunto(nota2),notaConPunto(nota21),notaConPunto(nota22),notaConPunto(nota221),notaConPunto(nota222),notaConPunto(nota223),
                notaConPunto(nota23), notaConPunto(nota231),notaConPunto(nota232),
                notaConPunto(nota24), notaConPunto(nota241), notaConPunto(nota242), notaConPunto(nota243), notaConPunto(nota244), notaConPunto(nota245),
                notaConPunto(nota25), notaConPunto(nota3),notaConPunto(nota31),notaConPunto(nota32), euskera?"SI":"no");*/
    }
    public static String notaConPunto(float nota){
        return String.format("%.4f",nota).replace(',','.');
    }

    public String infoCotejo() {
        return String.format("DNI='%s', origen='Desconocido', nombre='%s', nota=%.4f, euskera=%b",dni,nombre,notaNacional,euskera);
    }
}
