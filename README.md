# Reparto
Aplicación que realiza el reparto de plazas del concurso de méritos, en principio es válido para cualquier especialidad.
# Como usarlo:
Hay dos formas de usarlo:
## La primera por parámetros:
1. Descargar de la [web del ministerio](https://www.educacionyfp.gob.es/contenidos/profesorado/no-universitarios/oposiciones-y-ofertas-trabajo/convocatoria-estabilizacion.html) el PDF de la correspondiente especialidad.
2. Convertir PDF a TXT, he usado la [web de Convertio](https://convertio.co/es/pdf-txt/) (En la última versión si el fichero es el pdf lo convierte a txt, ahorrando este paso)
3. La ejecución de la app necesita de la carpeta data el archivo [plazas.txt](https://github.com/agarciaexposito/Reparto/blob/master/data/plazas.txt) o el archivo [plazas.ser](https://github.com/agarciaexposito/Reparto/blob/master/data/plazas.ser)
4. La aplicación captura la información de los argumentos, donde el primero es la ruta al archivo TXT del paso 1 y el segundo el codigo de especialidad para dicho TXT.
5. La salida se colocará en la carpeta donde este el archivo TXT, pero ahora con nombre Reparto_<NOMBRE_TXT>.xlsx
6. También se pueden generar además en formato JSON pero para eso hay que cambiar generarJSON=false por true; 
6. He utilizado la web [JSON online](https://jsononline.net/json-to-xml) para convertir el JSON a XML
## La segunda por contenido de la carpeta descargas:
1. Poner en la carpeta descargas los ficheros de la [web del ministerio](https://www.educacionyfp.gob.es/contenidos/profesorado/no-universitarios/oposiciones-y-ofertas-trabajo/convocatoria-estabilizacion.html) se puede descargar todos usando el programa testing DescargarPDFs
2. La ejecución de la app necesita de la carpeta data el archivo [plazas.txt](https://github.com/agarciaexposito/Reparto/blob/master/data/plazas.txt) o el archivo [plazas.ser](https://github.com/agarciaexposito/Reparto/blob/master/data/plazas.ser)
# Se ha incluido la posibilidad de realizar una ordenación por especialiad, comunidad y turno. modificar en el Main el atributo generarReparto=false

