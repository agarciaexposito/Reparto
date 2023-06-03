package testing;

import helper.Comun;

import java.io.IOException;

public class CargaMinisterio590019 {
    public static void main(String[] args) {
        try {
            Comun.cargaEspecialidadMinsterio("./ministerio/590019-tecnologia.txt","590019");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
