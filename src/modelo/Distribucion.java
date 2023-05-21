package modelo;

public class Distribucion extends AbstractRepartos {
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
                if (!eleccion.isRechazado()) {
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
