Para la realizaci�n del ejercicio t�cnico se han utilizadoo la siguiente tecnolog�a:

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

- Para Maven se ha creado un plugin que permite ejecutar la aplicaci�n en el servidor
Web Jetty (mvn jetty:run) configurado en el puerto 9090

-En cuanto a la configuraci�n de la BD se han creado dos BD, una para pruebas llamada "imatiatest"
y otra para caso real llamada "imatia" en ambos casos el usuario y la constrase�a son "imatia"

Se ha creado un plugin autom�tico a partir de maven para rellenar la base de datos del caso real con 2 usuarios 
y 12 tags para as� poder observar la utilizaci�n de bloques para la visualizaci�n de las tareas.




