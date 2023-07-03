# Especifica la imagen base
FROM tomcat:latest

# Copia el archivo WAR a la carpeta del contenedor renombrandolo
COPY target/nodo-tracking-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/nodo-tracking.war

# Comando a ejecutar cuando se inicie el contenedor
CMD ["catalina.sh", "run"]