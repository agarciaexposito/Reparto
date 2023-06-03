package modelo;

import com.google.gson.Gson;
import helper.Comun;
import helper.Util;
import helper.UtilMinisterio;
import helper.UtilPlazas;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class AspirantesMinisterio implements Serializable {
    @Serial
    private static final long serialVersionUID = -1632909041346606036L;
    private String especialidad;
    protected Map<String, AdjudicacionMinisterio> autonomias = new TreeMap<>();
    public AspirantesMinisterio(String especialidad) {
        this.especialidad = especialidad;
    }

    public void loadTXT(String file) throws IOException {
        Path path = Paths.get(file);
        List<String> lineas = Files.readAllLines(path, StandardCharsets.UTF_8);
        AdjudicacionMinisterio adjudicacionMinisterio = null;
        boolean capturadoEsLibre = false;
        boolean esLibreOld= false;
        boolean capturadoEsEuskera=false;  // cuando se captura una vez ya todos los que hay debajo son de euskera
        boolean esEuskeraOld = false;
        String autonomia="";
        String autonomiaOld="";
        boolean esLaPrimera=true;
        int pos=1;
        for (String linea:lineas) {
            try {
                if (UtilMinisterio.isLineaEspeciadlidad(linea)) {
                    esEuskeraOld=capturadoEsEuskera;
                    capturadoEsEuskera = UtilMinisterio.contineEuskera(linea);
                    if (esLaPrimera)
                        esEuskeraOld=capturadoEsEuskera;
                    continue;
                }
                if (UtilMinisterio.isLineaAutonomia(linea)){
                    String temp=UtilPlazas.getAutonomia(linea);
                    if (!temp.equals("")) {
                        autonomiaOld = autonomia;
                        autonomia = UtilPlazas.getAutonomia(linea);
                        if (esLaPrimera)
                            autonomiaOld = autonomia;
                        continue;
                    }
                }
                if (UtilMinisterio.isLineaTurno(linea)){
                    String [] trozo = UtilMinisterio.troceaLineaTurno(linea);
                    esLibreOld=capturadoEsLibre;
                    capturadoEsLibre=trozo[0].trim().equals("LIBRE");
                    if (esLaPrimera) {
                        esLibreOld = capturadoEsLibre;
                        pos = 1;
                        adjudicacionMinisterio = new AdjudicacionMinisterio(autonomia, Integer.parseInt(trozo[1]), trozo[0].trim().equals("LIBRE"), capturadoEsEuskera);
                        esLaPrimera = false; //
                    } else if (!autonomia.equals(autonomiaOld) || capturadoEsLibre!=esLibreOld || capturadoEsEuskera!=esEuskeraOld) { // si ha cambiado algo
                        autonomias.put(Comun.keyAutonomia(autonomiaOld, esLibreOld, esEuskeraOld), adjudicacionMinisterio); // añadimos la anterior
                        pos = 1;
                        adjudicacionMinisterio = new AdjudicacionMinisterio(autonomia, Integer.parseInt(trozo[1]), trozo[0].trim().equals("LIBRE"), capturadoEsEuskera);
                    }
                    continue;
                }
                if (UtilMinisterio.isLineaDNI(linea)){
                    String[] trozo = UtilMinisterio.troceaLineaAspirante(linea);
                    AspiranteMinisterio aspirante = new AspiranteMinisterio(Integer.parseInt(especialidad),adjudicacionMinisterio.isLibre(),pos++,trozo[0],trozo[1],trozo[2],Float.parseFloat(trozo[3].replace(",", ".")),adjudicacionMinisterio.isEuskera());
                    adjudicacionMinisterio.add(aspirante);
                    continue;
                }
            } catch (Exception e){
                System.out.println(linea);
            }
        }
        if (adjudicacionMinisterio != null) {
            autonomias.put(Comun.keyAutonomia(autonomia,capturadoEsLibre,capturadoEsEuskera), adjudicacionMinisterio); // añadimos la última
        }
    }


    public Map<String, AdjudicacionMinisterio> getAutonomias() {
        return autonomias;
    }

    public static AspirantesMinisterio readSer(String especialidad) throws Exception{
        String file = Comun.findFile(Comun.PATH_RESULT_MINISTERIO,especialidad.trim()+"-.*.ser");
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(file))) {
            Object a = entrada.readObject();
            if (a instanceof AspirantesMinisterio) {
               return (AspirantesMinisterio) a;
            }
            else throw new Exception(String.format("El archivo %s no contiene los aspirantes, bórrelo",file));
        }
    }

    public void writeSer(String file) throws IOException{
        try (ObjectOutputStream salida =new ObjectOutputStream(new FileOutputStream(file))){
            salida.writeObject(this);
            salida.flush();
        }
    }
}
