# Reparto
Aplicación que realiza el reparto de plazas del concurso de méritos, en principio es válido para cualquier especialidad.
# Como usarlo:
1. Descargar de la [web del ministerio](https://www.educacionyfp.gob.es/contenidos/profesorado/no-universitarios/oposiciones-y-ofertas-trabajo/convocatoria-estabilizacion.html) el PDF de la correspondiente especialidad.
2. Convertir PDF a TXT, he usado la [web de Convertio](https://convertio.co/es/pdf-txt/)
3. La ejecución de la app necesita de la carpeta ficheros y del archivo [plazas.txt](https://github.com/agarciaexposito/Reparto/blob/master/ficheros/plazas.txt)
4. La aplicación captura la información de los argumentos, donde el primero es la ruta al archivo TXT del paso 1 y el segundo el codigo de especialidad para dicho TXT.
5. La salida se colocará en la carpeta donde este el archivo TXT, pero ahora con nombre Reparto_<NOMBRE_TXT>.json
6. He utilizado la web [JSON online](https://jsononline.net/json-to-xml) para convertir el JSON a XML
# No contempla:
- El reparto de plazas de Navarra que tengan como requisito el Eukera 
- Plazas de discapacitados sin cubrir, es decir, NO realiza el reparto de estas con aspirantes del turno libre.
