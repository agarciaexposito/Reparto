package modelo;


import com.google.gson.Gson;
import helper.Comun;
import helper.UtilPlazas;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Plazas {
    private boolean ordenado = false;
    private List<Plaza> plazas= new ArrayList<>();
    public boolean add(Plaza plaza){
        ordenado = false;
        return plazas.add(plaza);
    }

    public List<Plaza> getPlazas() {
        return plazas;
    }


    @Override
    public String toString() {
        ordenarPorEspecialidad();
        String cad="";
        for (Plaza plaza: plazas) {
            cad += plaza.toString();
        }
        return cad;
    }

    public void ordenarPorEspecialidad() {
        if (!ordenado) {
            Collections.sort(plazas, new OrdenarPlazasPorEspecialidad());
            ordenado=true;
        }
    }

    public void loadTXT(String file) throws IOException {
        Path path = Paths.get(file);
        List<String> lineas = Files.readAllLines(path, StandardCharsets.UTF_8);
        Plaza plaza;
        String autonomia="";
        int especialidad=0;
        String descripcion="";
        for (String linea:lineas) {
            try {
                if (Comun.isVacia(linea)) {
                    continue;
                }
                if (UtilPlazas.isLineaAutonomia(linea)){
                    autonomia = UtilPlazas.getAutonomia(linea);
                    if (autonomia.equals(""))
                        System.err.println(linea);
                    else {
                        continue;
                    }
                }
                if (UtilPlazas.isLineaEspecialidadNavarra(linea)){
                    plaza = new Plaza();
                    plaza.setAutonomia(autonomia);
                    String[] trozos=UtilPlazas.troceaLineasEspecialidadNavarra(linea);
                    especialidad=Integer.parseInt(trozos[0]);
                    plaza.setEspecialidad(especialidad);
                    descripcion=trozos[1];
                    plaza.setDescripcion(descripcion);
                    plaza.setEuskera(Integer.parseInt(trozos[2])==1);
                    plaza.setLibre(Integer.parseInt(trozos[3])==1);
                    plaza.setNumero(Integer.parseInt(trozos[4]));
                    plazas.add(plaza);
                    continue;
                }
                if (UtilPlazas.isLineaEspecialidadSinNombreNavarra(linea)){
                    plaza = new Plaza();
                    plaza.setAutonomia(autonomia);
                    plaza.setEspecialidad(especialidad);
                    plaza.setDescripcion(descripcion);
                    String[] trozos=UtilPlazas.troceaLineasEspecialidadSinNombreNavarra(linea);
                    plaza.setEuskera(Integer.parseInt(trozos[0])==1);
                    plaza.setLibre(Integer.parseInt(trozos[1])==1);
                    plaza.setNumero(Integer.parseInt(trozos[2]));
                    plazas.add(plaza);
                    continue;
                }
                if (UtilPlazas.isLineaEspecialidad(linea)){
                    plaza = new Plaza();
                    plaza.setAutonomia(autonomia);
                    String[] trozos=UtilPlazas.troceaLineasEspecialidad(linea);
                    especialidad=Integer.parseInt(trozos[0]);
                    plaza.setEspecialidad(especialidad);
                    descripcion=trozos[1];
                    plaza.setDescripcion(descripcion);
                    plaza.setEuskera(false);
                    plaza.setLibre(Integer.parseInt(trozos[2])==1);
                    plaza.setNumero(Integer.parseInt(trozos[3]));
                    plazas.add(plaza);
                    continue;
                }
                if (UtilPlazas.isLineaEspecialidadSinNombre(linea)){
                    plaza = new Plaza();
                    plaza.setAutonomia(autonomia);
                    plaza.setEspecialidad(especialidad);
                    plaza.setDescripcion(descripcion);
                    String[] trozos=UtilPlazas.troceaLineasEspecialidadSinNombre(linea);
                    plaza.setLibre(Integer.parseInt(trozos[0])==1);
                    plaza.setNumero(Integer.parseInt(trozos[1]));
                    plazas.add(plaza);
                    continue;
                }

                System.out.println(linea);


            } catch (Exception e){
                System.out.println(linea);
            }
        }
        ordenado = false;
    }

    public void readSer(String file) throws Exception{
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(file))) {
            Object p = entrada.readObject();
            if (p instanceof List) {
                this.plazas=((List<Plaza>) p);
                ordenado = true; //se guardo ordenado
            }
            else throw new Exception(String.format("El archivo %s no contiene una lista de plazas, bórrelo",file));
        }
    }

    public void writeSer(String file) throws IOException{
        ordenarPorEspecialidad();
        try (ObjectOutputStream salida =new ObjectOutputStream(new FileOutputStream(file))){
            salida.writeObject(plazas);
            salida.flush();
        }
    }
    public void saveJSON(String file) throws IOException {
        ordenarPorEspecialidad();
        // create a writer
        Writer writer = Files.newBufferedWriter(Paths.get(file));
        // convert books object to JSON file
        new Gson().toJson(plazas, writer);
        // close writer
        writer.close();
    }


}
