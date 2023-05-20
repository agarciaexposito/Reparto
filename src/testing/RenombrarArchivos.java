package testing;

import java.io.File;

public class RenombrarArchivos {

    public static void main(String[] args) {
        String directorio = "D:\\Nextcloud\\2023\\IES\\Baremación\\programa\\Reparto\\ficheros";  // Ruta al directorio de los archivos
        File carpeta = new File(directorio);

        File[] archivos = carpeta.listFiles();
        if (archivos != null) {
            for (File archivo : archivos) {
                if (archivo.isFile() && archivo.getName().startsWith("Reparto") && archivo.getName().endsWith(".xlsx")) {
                    String nuevoNombre = "Reparto_" + archivo.getName().substring(7);
                    File nuevoArchivo = new File(directorio + File.separator + nuevoNombre);
                    archivo.renameTo(nuevoArchivo);
                }
            }
        }
    }
}