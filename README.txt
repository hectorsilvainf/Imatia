Para la realización del ejercicio técnico se han utilizadoo la siguiente tecnología:

Sistema Operativo Windows.

Entorno de desarrollo:
	-Eclipse
	-JDK
	-Apache Maven
	-Mysql
	-Apache Tomcat

Framewors para Eclipse:
	-Apache Tapestry
	-Hibernate
	-Spring

- Para Maven se ha creado un plugin que permite ejecutar la aplicación en el servidor
Web Jetty (mvn jetty:run) configurado en el puerto 9090

-En cuanto a la configuración de la BD se han creado dos BD, una para pruebas llamada "imatiatest"
y otra para caso real llamada "imatia" en ambos casos el usuario y la constraseña son "imatia"

Se ha creado un plugin automático a partir de maven para rellenar la base de datos del caso real con 2 usuarios 
y 12 tags para así poder observar la utilización de bloques para la visualización de las tareas.




