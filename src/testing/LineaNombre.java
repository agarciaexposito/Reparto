package testing;


import helper.Util;

import java.util.regex.Matcher;
        import java.util.regex.Pattern;

public class LineaNombre {
    public static void main(String[] args) {
        String linea = "****7260S         CAMPOS GERPE, ANTONIO MANUEL";

        String patronCodigo = "\\*{4}[0-9A-Z]+";
        String patronNombre = "\\s[A-ZÑÇÁÉÍÓÚ][A-ZÑÇÁÉÍÓÚ\\s,]+";

        Pattern patronCodigoPattern = Pattern.compile(Util.REGEX_DNI);
        Pattern patronNombrePattern = Pattern.compile(Util.REGEX_NOMBRE);

        Matcher matcherCodigo = patronCodigoPattern.matcher(linea);
        Matcher matcherNombre = patronNombrePattern.matcher(linea);

        if (matcherCodigo.find() && matcherNombre.find()) {
            String codigo = matcherCodigo.group();
            String nombre = matcherNombre.group().trim();
            System.out.println("Código: " + codigo);
            System.out.println("Nombre: " + nombre);
        } else {
            System.out.println("No se encontraron coincidencias.");
        }
    }
}
