package modelo;

import java.io.Serial;
import java.io.Serializable;

public class Distribucion extends AbstractRepartos implements Serializable {
    @Serial
    private static final long serialVersionUID = 6325128202505063014L;

    public Distribucion(Aspirantes aspirantes, Plazas plazas, String especialidad) {
        super(aspirantes, plazas, especialidad);
    }

    @Override
    public void ejecuta() {
        aspirantes.ordenacionDeMayorAMenor();
        repartoDePlazas();
    }

    private void repartoDePlazas() {
        for (Aspirante aspirante : aspirantes.getAspirantes()) {
            for (Eleccion eleccion : aspirante.getElecciones()) { // esto esta en el orden natural de lectura que coincide con el orden de elección
                if (!eleccion.idDescartado(aspirante.getNotaNacional())) {
                    Reparto reparto;
                    if (!eleccion.isLibre()) { // si fuese discapacitado
                        if (aspirante.isEuskera())
                            reparto = autonomias.get(eleccion.getAutonomia() + "_D_K");
                        else reparto = autonomias.get(eleccion.getAutonomia() + "_D");

                        if (reparto != null)
                            reparto.forceAdd(aspirante, eleccion);
                    }
                    if (aspirante.isEuskera())
                        reparto = autonomias.get(eleccion.getAutonomia() + "_L_K");
                    else reparto = autonomias.get(eleccion.getAutonomia() + "_L");
                    if (reparto != null)
                        reparto.forceAdd(aspirante, eleccion);
                }
            }
        }
    }
}
