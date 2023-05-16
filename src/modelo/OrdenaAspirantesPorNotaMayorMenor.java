package modelo;

import java.util.Comparator;

public class OrdenaAspirantesPorNotaMayorMenor implements Comparator<Aspirante> {

    @Override
    public int compare(Aspirante o1, Aspirante o2) {
            int resultado= Float.compare(o2.getNotaNacional(), o1.getNotaNacional());
            //Notas de primer nivel
            if (resultado==0)
                resultado= Float.compare(o2.getNota1(), o1.getNota1());
            if (resultado==0)
                resultado= Float.compare(o2.getNota2(), o1.getNota2());
            if (resultado==0)
                resultado= Float.compare(o2.getNota3(), o1.getNota3());
            //Notas de segundo nivel
            if (resultado==0)
                resultado= Float.compare(o2.getNota11(), o1.getNota11());
            if (resultado==0)
                resultado= Float.compare(o2.getNota12(), o1.getNota12());
            if (resultado==0)
                resultado= Float.compare(o2.getNota13(), o1.getNota13());
            if (resultado==0)
                resultado= Float.compare(o2.getNota14(), o1.getNota14());

            if (resultado==0)
                resultado= Float.compare(o2.getNota21(), o1.getNota21());
            if (resultado==0)
                resultado= Float.compare(o2.getNota22(), o1.getNota22());
            if (resultado==0)
                resultado= Float.compare(o2.getNota23(), o1.getNota23());
            if (resultado==0)
                resultado= Float.compare(o2.getNota24(), o1.getNota24());
            if (resultado==0)
                resultado= Float.compare(o2.getNota25(), o1.getNota25());

            if (resultado==0)
                resultado= Float.compare(o2.getNota31(), o1.getNota31());
            if (resultado==0)
                resultado= Float.compare(o2.getNota32(), o1.getNota32());

            // notas de tercer nivel
            if (resultado==0)
                resultado= Float.compare(o2.getNota221(), o1.getNota221());
            if (resultado==0)
                resultado= Float.compare(o2.getNota222(), o1.getNota222());
            if (resultado==0)
                resultado= Float.compare(o2.getNota223(), o1.getNota223());

            if (resultado==0)
                resultado= Float.compare(o2.getNota231(), o1.getNota231());
            if (resultado==0)
                resultado= Float.compare(o2.getNota232(), o1.getNota232());

            if (resultado==0)
                resultado= Float.compare(o2.getNota241(), o1.getNota241());
            if (resultado==0)
                resultado= Float.compare(o2.getNota242(), o1.getNota242());
            if (resultado==0)
                resultado= Float.compare(o2.getNota243(), o1.getNota243());
            if (resultado==0)
                resultado= Float.compare(o2.getNota244(), o1.getNota244());
            if (resultado==0)
                resultado= Float.compare(o2.getNota245(), o1.getNota245());

            if (resultado==0)
                resultado= Integer.compare(o2.getAnos(), o1.getAnos());
            if (resultado==0)
                resultado= Integer.compare(o2.getMeses(), o1.getMeses());
            if (resultado==0)
                resultado= Integer.compare(o2.getDias(), o1.getDias());
        return resultado;
    }
}