package helper;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class Comun {
    private Comun() {
    }
    public static final String REGEX_LINEA_VACIA="^\\s*$";
    public static final String REGEX_LINEA_PAGINA=".*Página.*";

    public static final String NAVARRA_D_K ="Navarra_D_K";
    public static final String NAVARRA_L_K ="Navarra_L_K";
    public static final String[] AUTONOMIA={
            "Andalucía","Aragón","Asturias","Illes Balears",
            "Cantabria","Castilla-La Mancha","Castilla y León","Ceuta",
            "Extremadura","La Rioja","Madrid","Melilla","Murcia","Navarra","Valencia"};
    public static boolean isDouble(String valor){
        return valor.matches("^\\d+(\\d*|(.)\\d+)$");
    }
    public static boolean isVacia(String linea){
        return linea.matches(REGEX_LINEA_VACIA) || linea.matches(REGEX_LINEA_PAGINA) ;
    }

    public static void csvToExcel(String nombreArchivoExcel, List<String> archivosCSV, String separador) throws IOException {
        XSSFWorkbook libroExcel = new XSSFWorkbook();
        for (String archivoCSV : archivosCSV) {
            XSSFSheet hoja = libroExcel.createSheet(nombreArchivoSinExt(archivoCSV));
            try (FileInputStream archivoEntrada = new FileInputStream(archivoCSV)) {
                String contenido = new String(archivoEntrada.readAllBytes());
                String[] filas = contenido.split("\\r?\\n");

                for (int i = 0; i < filas.length; i++) {
                    String[] columnas = filas[i].split(separador);
                    Row fila = hoja.createRow(i);
                    for (int j = 0; j < columnas.length; j++) {
                        Cell celda = fila.createCell(j);
                        celda.setCellValue(columnas[j]);
                    }
                }
            }
        }

        try (FileOutputStream archivoSalida = new FileOutputStream(nombreArchivoExcel)) {
            libroExcel.write(archivoSalida);
        }
    }

    public static void pdfToTxt(String in,String out) throws IOException {
        try (PDDocument document = PDDocument.load(new File(in))) {
            // Crear un objeto PDFTextStripper para extraer el texto
            PDFTextStripper stripper = new PDFTextStripper();
            // establecer orden por posición
            stripper.setSortByPosition(true);
            //stripper.setShouldSeparateByBeads(true);
            //stripper.setWordSeparator(" ");
            // Obtener el texto del documento
            String texto = stripper.getText(document);
            // Imprimir el texto
            Files.deleteIfExists(Paths.get(out));
            Files.write(Paths.get(out), texto.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
        }
    }

    public static String nombreArchivo(String file){
        return Paths.get(file).getFileName().toString();
    }

    public static String nombreArchivoSinExt(String file){
        String nombre=nombreArchivo(file);
        return nombre.substring(0, nombre.lastIndexOf("."));
    }
    public static String extension(String file) {
        String filename = nombreArchivo(file);
        return filename.substring(filename.lastIndexOf(".") + 1);

    }
    public static String rutaArchivo(String file){
        return Paths.get(file).getParent().toString();
    }


    public static void descargarPDFs(String url,String carpeta) throws IOException{
        /*
        De esta forma, se indicará a la máquina virtual de Java que utilice
        el almacén de certificados raíz del sistema operativo Windows para validar los certificados SSL
         y que no utilice ningún almacén específico. Esto puede ser útil en algunos casos en los que el certificado SSL
          del servidor remoto no es válido o no se encuentra en el almacén de certificados utilizado por la máquina virtual de Java.
Ten en cuenta que deshabilitar la validación del certificado SSL puede dejar tu aplicación vulnerable a ataques de tipo "Man-in-the-middle",
por lo que deberías utilizar esta solución con precaución y solo en entornos de pruebas o si estás seguro de que el servidor remoto es seguro.
         */
        System.setProperty("javax.net.ssl.trustStoreType", "Windows-ROOT");
        System.setProperty("javax.net.ssl.trustStore", "none");
        // Realizar una solicitud GET a la página web y obtener el contenido HTML
        Document doc = Jsoup.connect(url).get();
        // Obtener todos los enlaces en la página
        Elements links = doc.select("a[href]");

        for (Element link : links) {
            // Obtener la URL del enlace
            String fileUrl = link.absUrl("href");

            // Si la URL es un enlace a un archivo, descargarlo
            if (fileUrl.endsWith(".pdf")) { //|| fileUrl.endsWith(".docx") || fileUrl.endsWith(".doc")) {
                // Obtener el nombre del archivo desde la URL
                String fileName = fileUrl.substring(fileUrl.lastIndexOf('/') + 1);

                // Crear una conexión a la URL del archivo y descargarlo
                URL website = new URL(fileUrl);
                ReadableByteChannel rbc = Channels.newChannel(website.openStream());
                creaCarpeta(carpeta);
                FileOutputStream fos = new FileOutputStream(carpeta + fileName);
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                fos.close();
                rbc.close();
            }
        }
    }
    public static void creaCarpeta(String folder) throws IOException {
        Path carpeta = Paths.get(folder);
        if (!Files.exists(carpeta))
            Files.createDirectories(carpeta);
    }

    public static Set<String> troceaTXTEnEspecialidades(String file) throws IOException {
        Set<String> ficheros = new HashSet<>();
        String rutaArchivo=rutaArchivo(file);
        Path path = Paths.get(file);
        List<String> lineas = Files.readAllLines(path, StandardCharsets.UTF_8);
        String especialidad = "";
        Path pathWrite=null;
        for (String linea:lineas) {
            /*if (Util.isLineaEuskera(linea)) {
                String[] trozo = Util.troceaLineaEspecialidadEuskera(linea);
                if (!trozo[0].trim().equals(especialidad)) {
                    especialidad = trozo[0].trim();
                    String nombreArchivo=rutaArchivo + "/" + especialidad + "-E-" + repasaNombreFichero(trozo[1].trim()) + ".txt";
                    pathWrite = creaTxt(linea, nombreArchivo);
                    ficheros.add(pathWrite.toString());
                    continue;
                }
            }*/
            if (Util.isLineaEspecialidad(linea)) {
                String[] trozo;
                if (Util.isLineaEuskera(linea))
                    trozo=Util.troceaLineaEspecialidadEuskera(linea);
                else trozo= Util.troceaLineaEspecialidad(linea);
                if (trozo == null) {
                    System.out.println("sin trocear " + linea + " Fichero: " + file+" ultima espe; "+especialidad+" ficheros: "+ficheros );
                    continue;
                }
                if (!trozo[0].trim().equals(especialidad)) {
                    especialidad = trozo[0].trim();
                    String nombreArchivo=rutaArchivo + "/" + especialidad + "-" + repasaNombreFichero(trozo[1].trim()) + ".txt";
                    pathWrite = creaTxt(linea, nombreArchivo);
                    ficheros.add(pathWrite.toString());
                    continue;
                }
            }
            if (pathWrite!=null)
                Files.writeString(pathWrite, linea+"\n", StandardOpenOption.APPEND);
        }
        return ficheros;
    }

    private static Path creaTxt( String linea, String nombreArchivo) throws IOException {
        Path pathWrite;
        pathWrite = Path.of(nombreArchivo);
        if (Files.exists(pathWrite))
            Files.delete(pathWrite);
        Files.writeString(pathWrite, linea+"\n", StandardOpenOption.CREATE);
        return pathWrite;
    }

    public static String repasaNombreFichero(String cadena){
        String cadenaMinisculas = cadena.toLowerCase();
        String cadenaSinTildes = cadenaMinisculas
                .replaceAll("[áäâà]", "a")
                .replaceAll("[éëêè]", "e")
                .replaceAll("[íïîì]", "i")
                .replaceAll("[óöôò]", "o")
                .replaceAll("[úüûù]", "u")
                .replaceAll(" ","-")
                .replaceAll("[ñç/:,]","");
        return cadenaSinTildes;
    }
}
