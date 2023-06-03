package helper;

import modelo.*;
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
import java.nio.file.*;

import java.nio.file.attribute.BasicFileAttributes;
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
    public static final String PATH_DATA = "./data";
    public static final String PATH_RESULT= "./result";
    public static final String PATH_MINISTERIO = "./ministerio";
    public static final String FICHEROS_PLAZAS_SER = "./data/plazas.ser";
    public static final String FICHEROS_PLAZAS_TXT = "./data/plazas.txt";
    public static final String PATH_RESULT_MINISTERIO = "./result/ministerio";
    public static final String PATH_RESULT_COTEJO = "./result/diferencias";
    public static int numFile=0;

    // archivos JSON para una posible publicación web
    public static boolean generarJSON=false;

    // para evitar multiples cargas de lecturas de fichesro PDFs, una vez generados correctamente, solo hay que ir cargandolos
    public static boolean generarSer = false;

    public static boolean generarAdjudicacion =true;  // con falso hago una ordenación de todos y con true genero una adjucación provisional
    public static boolean generarExcels = false;
    private static final boolean hojaResumen = true; // para mostrar una hoja resumen con la vulnearción de las disposiciones tanto la D.A. Sexta, D.A. Octava como Directiva 1999/70/CE

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
    public static String findFile(String folderPath,String regexFile) throws IOException {
        Path folder = Paths.get(folderPath);
        if (!Files.isDirectory(folder)) {
            throw new IOException(String.format("La ruta %s no es una carpeta válida.",folderPath));
        }
        DirectoryStream.Filter<Path> filter = new DirectoryStream.Filter<Path>() {
            @Override
            public boolean accept(Path path) throws IOException {
                return Files.isRegularFile(path) && path.getFileName().toString().matches(regexFile);
            }
        };

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(folder, filter)) {
            for (Path path : stream) {
                return path.toString();
            }
        }
        throw new IOException(String.format("En la ruta %s no hay archivos que cumplan %s.",folderPath,regexFile));
    }

    public static void cotejarDatosMinisterio(int especialidad) {
        try {
            Path carpeta=Paths.get(PATH_RESULT_COTEJO+"/");
            if (!Files.exists(carpeta))
                Files.createDirectories(carpeta);
            Path fichero=Paths.get(PATH_RESULT_COTEJO+"/"+especialidad+".txt");
            Files.deleteIfExists(fichero);
            String titulo=String.format("ESPECIALIDAD: %s - DIFERENCIAS ENTRE LA ADJUDICACIÓN PROVISIONAL DEL  MINISTERIO Y LOS EXCELs\n\n",especialidad);
            Files.write(fichero, titulo.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
            AbstractRepartos repartos = Repartos.readSer(especialidad + "");
            AspirantesMinisterio aspirantesMinisterio = AspirantesMinisterio.readSer(especialidad + "");
            boolean hayDiferencias=false;
            for (String autonomia : aspirantesMinisterio.getAutonomias().keySet()) {
                List<Adjudicacion> adjudicaciones = repartos.getAutonomias().get(autonomia).getAdjudicaciones();
                int pos = 0;
                AdjudicacionMinisterio adjudicacionMinisterio = aspirantesMinisterio.getAutonomias().get(autonomia);
                for (AspiranteMinisterio aspiranteMinisterio : adjudicacionMinisterio.getAspirantesMinisterio()) {
                    Aspirante miAspirante = adjudicaciones.get(pos++).getAdjudicadoA();
                    if (!aspiranteMinisterio.getDni().equals(miAspirante.getDni())) {
                        hayDiferencias=true;
                        String sb = String.format("Especialidad: %s Autonomía: %s Turno: %c Euskera %s\n",
                                especialidad, autonomia, (aspiranteMinisterio.isLibre() ? 'L' : 'D'), (aspiranteMinisterio.isEuskera() ? "SI" : "NO")) +
                                String.format("Aspirante Ministerio: %s\n", aspiranteMinisterio) +
                                String.format("Mi aspirante: Pos=%d, %s\n\n", pos, miAspirante.infoCotejo());
                        Files.write(fichero, sb.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
                        break;
                    }
                }
            }
            if (!hayDiferencias)
                Files.deleteIfExists(fichero);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void cargarTodasLasAdjudDirectorioMinisterio() {
        try {
            Path directoryPath = Paths.get(Comun.PATH_MINISTERIO);
            Files.walkFileTree(directoryPath, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    if (file.getFileName().toString().toLowerCase().endsWith(".pdf")) {
                        String fichero = file.getFileName().toString();
                        cargaAdjFicheroMinisterio(directoryPath + "/" + fichero);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void cargaAdjFicheroMinisterio(String file) {
        try {
            System.out.printf("%d$%s\n",numFile++,file);
            if (Comun.extension(file).equalsIgnoreCase("pdf")){
                String fileOut=file.toLowerCase().replaceAll("\\.pdf$", "-Temp.txt");
                Comun.pdfToTxt(file,fileOut);
                file = fileOut;
            }
            Set<String> ficheros=Comun.troceaTXTEnEspecialidades(file);
            for (String fichero:ficheros) {
                String especialidad=Comun.nombreArchivoSinExt(fichero).trim().substring(0,6);
                cargaEspecialidadMinsterio(fichero, especialidad);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            //System.exit(-1);
        }
    }

    public static void cargaEspecialidadMinsterio(String fichero, String especialidad) throws IOException {
        Path file = Paths.get(fichero);
        String nombreArchivo = file.getFileName().toString();
        AspirantesMinisterio aspirantes=new AspirantesMinisterio(especialidad);
        aspirantes.loadTXT(fichero);
        if (generarSer)
            aspirantesMinisterioSer(aspirantes, nombreArchivo);
    }
    public static List<Integer> generarListaEspecialidades(Path directoryPath) {
        List<Integer> especialidades=new ArrayList<>();
        try {
            Files.walkFileTree(directoryPath, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                if (file.getFileName().toString().toLowerCase().endsWith(".ser")) {
                    String fichero = file.getFileName().toString();
                    especialidades.add(Integer.parseInt(fichero.substring(0,6)));
                }
                return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return especialidades;
    }

    public static void cargaDirectorio(Path directoryPath) {
        try {
            Files.walkFileTree(directoryPath, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    if (file.getFileName().toString().toLowerCase().endsWith(".pdf")) {
                        String fichero = file.getFileName().toString();
                        cargaFichero(directoryPath + "/" + fichero);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void cargaFichero(String file) {
        try {
            System.out.printf("%d$%s\n",numFile++,file);
            if (Comun.extension(file).equalsIgnoreCase("pdf")){
                String fileOut=file.toLowerCase().replaceAll("\\.pdf$", "-Temp.txt");
                Comun.pdfToTxt(file,fileOut);
                file = fileOut;
            }
            Set<String> ficheros=Comun.troceaTXTEnEspecialidades(file);
            for (String fichero:ficheros) {
                String especialidad=Comun.nombreArchivoSinExt(fichero).trim().substring(0,6);
                cargaEspecialidad(fichero, especialidad);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            //System.exit(-1);
        }
    }

    private static void cargaEspecialidad(String fichero, String especialidad) throws IOException {
        Path file = Paths.get(fichero);
        Path rutaArchivo = file.getParent();
        String nombreArchivo = file.getFileName().toString();
        Plazas plazas = getPlazas();
        Aspirantes aspirantes=new Aspirantes(especialidad);
        aspirantes.loadTXT(fichero);
        if (generarJSON)
            aspirantesJSON(aspirantes, fichero);
        if (generarSer)
            aspirantesSer(aspirantes, nombreArchivo);
        AbstractRepartos repartos;
        if (generarAdjudicacion)
            repartos = new Repartos(aspirantes, plazas, especialidad);
        else repartos = new Distribucion(aspirantes,plazas,especialidad);
        repartos.ejecuta();
        List<String> ficherosGeneradosCSV=repartosCSV(rutaArchivo, nombreArchivo, repartos);
        if (generarExcels)
            repartosExcel(rutaArchivo, nombreArchivo,ficherosGeneradosCSV);
        if (generarJSON)
            repartosJSON(rutaArchivo, nombreArchivo, repartos);
        if (generarSer)
            repartosSer(nombreArchivo, repartos);
    }

    private static void aspirantesSer(Aspirantes aspirantes, String fichero) throws IOException {
        creaCarpetaData();
        String fileSER = Comun.PATH_DATA +"/"+ fichero.replaceAll("\\.txt$", ".ser");
        //String fileSER = PATH_SER +"/"+ fichero.trim().substring(0,6)+".ser";
        aspirantes.writeSer(fileSER);
    }
    private static void aspirantesMinisterioSer(AspirantesMinisterio aspirantes, String fichero) throws IOException {
        creaCarpetaDataMin();
        String fileSER = Comun.PATH_RESULT_MINISTERIO +"/"+ fichero.replaceAll("\\.txt$", ".ser");
        //String fileSER = PATH_SER +"/"+ fichero.trim().substring(0,6)+".ser";
        aspirantes.writeSer(fileSER);
    }



    private static Plazas getPlazas() {
        Plazas plazas=new Plazas();
        try {
            if (Files.exists(Paths.get(Comun.FICHEROS_PLAZAS_SER)))
                plazas.readSer(Comun.FICHEROS_PLAZAS_SER);
            else {
                plazas.loadTXT(Comun.FICHEROS_PLAZAS_TXT);
                creaCarpetaData();
                plazas.writeSer(Comun.FICHEROS_PLAZAS_SER);
            }
            creaCarpetaFicheros();
            if (generarJSON)
                plazas.saveJSON("./ficheros/plazas.json");

        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
        return plazas;
    }

    private static void aspirantesJSON(Aspirantes aspirantes, String fichero) throws IOException {
        String fileJSON = fichero.replaceAll("\\.txt$", ".json");
        aspirantes.saveJSON(fileJSON);
    }

    private static List<String> repartosCSV(Path rutaArchivo, String nombreArchivo, AbstractRepartos repartos) throws IOException {
        String fileRepartoCSV = rutaArchivo +"/"+ nombreArchivo.replaceAll("\\.txt$", ".csv");
        if (hojaResumen)
            return repartos.saveCSVHojaResumen(fileRepartoCSV);
        else return repartos.saveCSV(fileRepartoCSV);
    }

    private static void repartosJSON(Path rutaArchivo, String nombreArchivo, AbstractRepartos repartos) throws IOException {
        //String fileRepartoJSON = rutaArchivo +"/AdjProv_"+ nombreArchivo.replaceAll("\\.txt$", ".json");
        // modificado para Manuel
        String fileRepartoJSON = rutaArchivo +"/"+ nombreArchivo.trim().substring(0,6)+".json";
        repartos.saveJSON(fileRepartoJSON);
    }
    private static void repartosSer(String nombreArchivo, AbstractRepartos repartos) throws IOException {
        creaCarpetaResult();
        String fileRepartoSER;
        if (generarAdjudicacion)
            fileRepartoSER = Comun.PATH_RESULT +"/"+ nombreArchivo.trim().substring(0,6)+"-adj_prov.ser";
        else fileRepartoSER = Comun.PATH_RESULT +"/"+ nombreArchivo.trim().substring(0,6)+"-ordenacion.ser";
        repartos.writeSer(fileRepartoSER);
    }
    public static void repartosExcel(Path rutaArchivo, String nombreArchivo,List<String> archivosCSV) throws IOException{
        String fileRepartoExcel;
        if (generarAdjudicacion)
            fileRepartoExcel = rutaArchivo +"/AdjProv_" + nombreArchivo.replaceAll("\\.txt$", ".xlsx");
        else fileRepartoExcel = rutaArchivo + "/" + nombreArchivo.replaceAll("\\.txt$", ".xlsx");
        Comun.csvToExcel(fileRepartoExcel,archivosCSV,";");
    }
    public static void creaCarpetaFicheros() throws IOException {
        Comun.creaCarpeta("./ficheros");
    }
    public static void creaCarpetaData() throws IOException {
        Comun.creaCarpeta(Comun.PATH_DATA);
    }
    public static void creaCarpetaResult() throws IOException {
        Comun.creaCarpeta(Comun.PATH_RESULT);
    }
    private static void creaCarpetaDataMin() throws IOException {
        Comun.creaCarpeta(Comun.PATH_RESULT_MINISTERIO);
    }

    public static String keyAutonomia(String autonomia, boolean esLibre, boolean esEuskera){
        return autonomia+(esLibre?"_L":"_D")+(esEuskera?"_K":"");
    }
}
