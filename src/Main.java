
import helper.Comun;
import modelo.*;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import static helper.Comun.*;

public class Main {


    private static final Scanner sc = new Scanner(System.in);
    private static final int MIN_OP_MENU=-3;
    private static final int MAX_OP_MENU=5;

    public static void main(String[] args) {
        switch (args.length) {
            case 0 -> cargaMenu();
            case 1 -> {
                Path directoryPath = Paths.get(args[0].trim());
                cargaDirectorio(directoryPath);
            }
            default -> {
                int cont = 0;
                while (cont < args.length) {
                    String fichero = args[cont++];
                    cargaFichero(fichero);
                }
            }
        }
    }
    private static int leeOpcion() {
        String opcion = null;
        try {
            opcion = sc.nextLine();
            int  valor=Integer.parseInt(opcion);
            if (valor<MIN_OP_MENU || valor>MAX_OP_MENU) {
                return -1;
            }
            return valor;
        } catch (Exception e) {
            return -1;
        }
    }

    public static int leeValor(){
        String s = sc.nextLine();
        if (s.matches("[0-9]+"))
            return Integer.parseInt(s);
        else {
            System.out.printf("Se espera un valor numérico (%s), vuelva a introducirlo\n",s);
            return leeValor();
        }
    }

    private static double leeCantidad() {
        String s = sc.nextLine();
        if (Comun.isDouble(s))
            return Double.parseDouble(s);
        else {
            System.out.println("Error en formato del número, tiene que ser 99.99: ");
            return leeCantidad();
        }
    }

    private static void menu() {
        System.out.printf("-3. %s generar JSONs%n",generarJSON?"DESACTIVAR":"ACTIVAR");
        System.out.printf("-2. %s generar SERs%n",generarSer?"DESACTIVAR":"ACTIVAR");
        System.out.printf("-1. %s generar EXCELs%n",generarExcels?"DESACTIVAR":"ACTIVAR");
        System.out.println("1: Adjudicación provisional");
        System.out.println("2: Ordenación");
        System.out.println("3. Cargar PDFs adjud. prov. (./ministerio)");
        System.out.println("4. Comparar adjud. mía con la del ministerio (una especialidad)");
        System.out.println("5. Comparar adjud. mía con la del ministerio (todas->./result vs ./result/ministerio)");
        System.out.println("0. Salir");
        System.out.print("Opción a realizar: ");
    }

    public static void cargaMenu() {
        int operacion;
        do {
            menu();
            operacion = leeOpcion();
            ejecuta(operacion);
        } while (operacion!=0);
    }

    private static void ejecuta(int operacion) {
        switch (operacion) {
            case -3 -> generarJSON = !generarJSON;
            case -2 -> generarSer = !generarSer;
            case -1 -> generarExcels = !generarExcels;
            case 0 -> {
                System.out.println("Hasta pronto, que tenga un buen día!! .");
                System.exit(0);
            }
            case 1, 2 -> {
                generarAdjudicacion = (operacion == 1);
                System.out.print("Carpeta con solicitudes (PDFs del ministerio día 11 Mayo): ");
                Path directoryPath = Paths.get(sc.nextLine().trim());
                cargaDirectorio(directoryPath);
            }
            case 3 -> cargarTodasLasAdjudDirectorioMinisterio();
            case 4 -> {
                System.out.print("Introduzca código de especialidad: ");
                int especialidad = leeValor();
                cotejarDatosMinisterio(especialidad);
            }
            case 5 -> {
                List<Integer> especialidades = generarListaEspecialidades(Paths.get(PATH_RESULT_MINISTERIO));
                int cont=1;
                for (int especialidad:especialidades) {
                    System.out.printf("%d. Generando diferencias para especialidad: %d\n",cont++,especialidad);
                    cotejarDatosMinisterio(especialidad);
                }
            }
            default -> msgOpcionIncorrecta();
        }
    }

    private static void msgOpcionIncorrecta() {
        System.out.printf("Rango de valores válidos (%d-%d)\n",MIN_OP_MENU,MAX_OP_MENU);
    }
}