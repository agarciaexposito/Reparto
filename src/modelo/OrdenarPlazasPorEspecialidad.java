package modelo;

import java.util.Comparator;

public class OrdenarPlazasPorEspecialidad implements Comparator<Plaza> {

    @Override
    public int compare(Plaza o1, Plaza o2) {
        return o1.getEspecialidad()-o2.getEspecialidad();
    }
}
