package modelo;

import com.google.gson.Gson;
import helper.Comun;
import helper.Util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Aspirantes implements Serializable {
    private boolean ordenado = false;
    private List<Aspirante> aspirantes=new ArrayList<>();

    public List<Aspirante> getAspirantes() {
        return aspirantes;
    }
    public void loadTXT(String file) throws IOException {
        Path path = Paths.get(file);
        List<String> lineas = Files.readAllLines(path, StandardCharsets.UTF_8);
        Aspirante aspirante = null;
        boolean capturarElecciones = false;
        boolean capturaLineaDespuesTotal=false;
        boolean capturadoEuskera=false;  // cuando se captura una vez ya todos los que hay debajo son de euskera
        for (String linea:lineas) {
            try {
                if (Util.isLineaEuskera(linea)){
                    capturadoEuskera=true;
                    continue;
                }
                if (Comun.isVacia(linea)) {
                    if (aspirante != null)
                        aspirantes.add(aspirante);
                    aspirante = null;
                    capturaLineaDespuesTotal=false;
                    capturarElecciones = false;
                    continue;
                }
                if (Util.isNombre(linea)) {//2º cambio en el orden para hacer funcioanr pdfBOX
                    if (aspirante != null)      // 1º añadido para hacer funcionar PDFBOX
                        aspirantes.add(aspirante);
                    aspirante = new Aspirante();
                    aspirante.setEuskera(capturadoEuskera);
                    String[] trozo = Util.troceaLineaNombre(linea);
                    aspirante.setDni(trozo[0]);
                    aspirante.setNombre(trozo[1]);
                    capturaLineaDespuesTotal=false;
                    capturarElecciones = false;
                    continue;
                }
                if (capturarElecciones) {
                    String[] elecciones = Util.capturaEleccciones(linea);
                    addElecciones(aspirante, elecciones);
                    capturaLineaDespuesTotal=false;
                    continue;
                }
                if (Util.isLineaAntesDeNotasAutonomicas(linea)) {
                    capturarElecciones = true;
                    capturaLineaDespuesTotal = false;
                    continue;
                }

                if (Util.isLineaAntiguedad(linea)){
                    String[] antiguedad=Util.troceaLineaAntigueada(linea);
                    if (antiguedad!=null) {
                        aspirante.setAnos(Integer.parseInt(antiguedad[0]));
                        aspirante.setMeses(Integer.parseInt(antiguedad[1]));
                        aspirante.setDias(Integer.parseInt(antiguedad[2]));
                    }
                    else System.out.println(linea);
                    capturaLineaDespuesTotal=false;
                    capturarElecciones=false;
                    continue;
                }
                if (capturaLineaDespuesTotal){
                    try {
                        if (aspirante.getNota1()==0)
                            aspirante.setNota1(Util.troceaLineaSinTotalNota(linea,"1"));

                        if (aspirante.getNota11()==0)
                            aspirante.setNota11(Util.troceaLineaSinTotalNota(linea,"1\\.1"));
                        if (aspirante.getNota12()==0)
                            aspirante.setNota12(Util.troceaLineaSinTotalNota(linea,"1\\.2"));
                        if (aspirante.getNota13()==0)
                            aspirante.setNota13(Util.troceaLineaSinTotalNota(linea,"1\\.3"));
                        if (aspirante.getNota14()==0)
                            aspirante.setNota14(Util.troceaLineaSinTotalNota(linea,"1\\.4"));

                        if (aspirante.getNota2()==0)
                            aspirante.setNota2(Util.troceaLineaSinTotalNota(linea,"2"));

                        if (aspirante.getNota21()==0)
                            aspirante.setNota21(Util.troceaLineaSinTotalNota(linea,"2\\.1"));
                        if (aspirante.getNota22()==0)
                            aspirante.setNota22(Util.troceaLineaSinTotalNota(linea,"2\\.2"));

                        if (aspirante.getNota221()==0)
                            aspirante.setNota221(Util.troceaLineaSinTotalNota(linea,"2\\.2\\.1"));
                        if (aspirante.getNota222()==0)
                            aspirante.setNota222(Util.troceaLineaSinTotalNota(linea,"2\\.2\\.2"));
                        if (aspirante.getNota223()==0)
                            aspirante.setNota223(Util.troceaLineaSinTotalNota(linea,"2\\.2\\.3"));

                        if (aspirante.getNota23()==0)

                            aspirante.setNota23(Util.troceaLineaSinTotalNota(linea,"2\\.3"));
                        if (aspirante.getNota231()==0)
                            aspirante.setNota231(Util.troceaLineaSinTotalNota(linea,"2\\.3\\.1"));
                        if (aspirante.getNota232()==0)
                            aspirante.setNota232(Util.troceaLineaSinTotalNota(linea,"2\\.3\\.2"));

                        if (aspirante.getNota24()==0)
                            aspirante.setNota24(Util.troceaLineaSinTotalNota(linea,"2\\.4"));

                        if (aspirante.getNota241()==0)
                            aspirante.setNota241(Util.troceaLineaSinTotalNota(linea,"2\\.4\\.1"));
                        if (aspirante.getNota242()==0)
                            aspirante.setNota242(Util.troceaLineaSinTotalNota(linea,"2\\.4\\.2"));
                        if (aspirante.getNota243()==0)
                            aspirante.setNota243(Util.troceaLineaSinTotalNota(linea,"2\\.4\\.3"));
                        if (aspirante.getNota244()==0)
                            aspirante.setNota244(Util.troceaLineaSinTotalNota(linea,"2\\.4\\.4"));
                        if (aspirante.getNota245()==0)
                            aspirante.setNota245(Util.troceaLineaSinTotalNota(linea,"2\\.4\\.4"));

                        if (aspirante.getNota25()==0)
                            aspirante.setNota25(Util.troceaLineaSinTotalNota(linea,"2\\.5"));

                        if (aspirante.getNota3()==0)
                            aspirante.setNota3(Util.troceaLineaSinTotalNota(linea,"3"));

                        if (aspirante.getNota31()==0)
                            aspirante.setNota31(Util.troceaLineaSinTotalNota(linea,"3\\.1"));
                        if (aspirante.getNota32()==0)
                            aspirante.setNota32(Util.troceaLineaSinTotalNota(linea,"3\\.2"));
                        capturarElecciones=false;
                        continue;
                    } catch (Exception e) {
                        System.out.println(linea);
                        System.err.println(e.getMessage());
                    }
                }
                if (Util.isNotaNacionalBaremo(linea)) {
                    try {
                        aspirante.setNotaNacional(Util.troceaLineaNotaNacionalBaremo(linea));
                        aspirante.setNota1(Util.troceaLineaNota(linea,"1"));
                        aspirante.setNota11(Util.troceaLineaNota(linea,"1\\.1"));
                        aspirante.setNota12(Util.troceaLineaNota(linea,"1\\.2"));
                        aspirante.setNota13(Util.troceaLineaNota(linea,"1\\.3"));
                        aspirante.setNota14(Util.troceaLineaNota(linea,"1\\.4"));
                        aspirante.setNota2(Util.troceaLineaNota(linea,"2"));
                        aspirante.setNota21(Util.troceaLineaNota(linea,"2\\.1"));
                        aspirante.setNota22(Util.troceaLineaNota(linea,"2\\.2"));
                        aspirante.setNota221(Util.troceaLineaNota(linea,"2\\.2\\.1"));
                        aspirante.setNota222(Util.troceaLineaNota(linea,"2\\.2\\.2"));
                        aspirante.setNota223(Util.troceaLineaNota(linea,"2\\.2\\.3"));
                        aspirante.setNota23(Util.troceaLineaNota(linea,"2\\.3"));
                        aspirante.setNota231(Util.troceaLineaNota(linea,"2\\.3\\.1"));
                        aspirante.setNota232(Util.troceaLineaNota(linea,"2\\.3\\.2"));
                        aspirante.setNota24(Util.troceaLineaNota(linea,"2\\.4"));
                        aspirante.setNota241(Util.troceaLineaNota(linea,"2\\.4\\.1"));
                        aspirante.setNota242(Util.troceaLineaNota(linea,"2\\.4\\.2"));
                        aspirante.setNota243(Util.troceaLineaNota(linea,"2\\.4\\.3"));
                        aspirante.setNota244(Util.troceaLineaNota(linea,"2\\.4\\.4"));
                        aspirante.setNota245(Util.troceaLineaNota(linea,"2\\.4\\.4"));
                        aspirante.setNota25(Util.troceaLineaNota(linea,"2\\.5"));
                        aspirante.setNota3(Util.troceaLineaNota(linea,"3"));
                        aspirante.setNota31(Util.troceaLineaNota(linea,"3\\.1"));
                        aspirante.setNota32(Util.troceaLineaNota(linea,"3\\.2"));
                        capturaLineaDespuesTotal=true;
                        capturarElecciones=false;
                        continue;
                    } catch (Exception e) {
                        System.out.println(linea);
                        System.err.println(e.getMessage());
                    }
                }
                //System.out.println(linea);
            } catch (Exception e){
                System.out.println(linea);
            }
        }
        if (aspirante!=null) {
            aspirantes.add(aspirante);
        }
        ordenado = false;
    }

    private void addElecciones(Aspirante aspirante, String[] elecciones) {
        int cont = 1;
        Eleccion eleccion=null;
        try {
            while (cont <= elecciones.length) {
                String valor = elecciones[cont - 1];
                if (cont % 2 != 0) {
                    eleccion = new Eleccion();
                    aspirante.add(eleccion);
                    eleccion.setOrden(Integer.parseInt(valor.trim()));
                } else {
                    if (valor.contains("*")) {
                        eleccion.setRechazado(true);
                        valor = valor.replaceAll("\\*", "");
                    } else eleccion.setRechazado(false);
                    for (String autonomia : Comun.AUTONOMIA) {
                        if (valor.toUpperCase().contains(autonomia.toUpperCase())) {
                            eleccion.setAutonomia(autonomia);
                            valor = valor.toUpperCase().replaceAll(autonomia.toUpperCase(), "");
                            break;
                        }
                    }
                    String[] trozos = valor.trim().split("\\s+");
                    eleccion.setLibre((trozos[0].equals("L")));
                    trozos[1] = trozos[1].trim().replace(",", ".");
                    eleccion.setNota(Float.parseFloat(trozos[1].trim()));
                }
                cont++;
            }
        } catch (Exception e) {
            System.out.println(aspirante);
            System.err.println(e.getMessage());
        }

    }
    public void ordenacionDeMayorAMenor() {
        if (!ordenado) {
            Collections.sort(aspirantes, new OrdenaAspirantesPorNotaMayorMenor());
            ordenado = true;
        }
    }
    public void readSer(String file) throws Exception{
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(file))) {
            Object a = entrada.readObject();
            if (a instanceof Aspirantes) {
                this.ordenado = ((Aspirantes) a).ordenado;
                this.aspirantes = ((Aspirantes) a).aspirantes;
            }
            else throw new Exception(String.format("El archivo %s no contiene los aspirantes, bórrelo",file));
        }
    }
    public void saveJSON(String file) throws IOException {
        ordenacionDeMayorAMenor();
        // create a writer
        Writer writer = Files.newBufferedWriter(Paths.get(file));
        // convert books object to JSON file
        new Gson().toJson(aspirantes, writer);
        // close writer
        writer.close();
    }

    public void writeSer(String file) throws IOException{
        ordenacionDeMayorAMenor();
        try (ObjectOutputStream salida =new ObjectOutputStream(new FileOutputStream(file))){
            salida.writeObject(this);
            salida.flush();
        }
    }
}
