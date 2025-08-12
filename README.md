
<h1>
Documento de definición de arquitectura
</h1>
<h3>1- Pautas para la entrega del trabajo práctico final</h3>

a. Proyecto completo de Netbeans comprimido en .zip (incluye Thymeleaf, WebClient,
dos microservicios y el Actuator).

b. Archivos compilados .jar y .war para ejecutar.

c. Proyecto Postman con los servicios configurados para probar funcionamiento del
BFF.

d) Se debe crear el diagrama de arquitectura de la solución marcada en el punto 2).

e) Las peticiones tienen que ser bajo dominio https (archivo correspondiente debe estar en
cada proyecto)

f) Los esquemas de la bdd deben ser: ms1 para el ms con alguna información ya sea
account u otro y ms_security para el ms de seguridad.

g) La solución debe ser gestionada con Maven.

h) El producto debe ser entregado para un entorno productivo.

i) La API debe ser del tipo RESTful estándar OPEN API OAS2 o 3.

j) Deberá usar la especificación JPA en Hibernate para la integración con la capa de datos
desde la solución SpringBoot (por rendimiento también se permite utilización de JDBC
para algún método específico).

k) Se deberá usar la versión de Java 17, SpringBoot 3.0.2 o 3.0.3 y Spring Framework 6.0.5.
<h3>2- Diagrama de arquitectura de la solución</h3>
<img width="1433" height="733" alt="image" src="https://github.com/user-attachments/assets/f7079590-ce33-46fb-9d5a-5b724c6f8a37" />

<h3>Descripción de flujo:</h3>

El proceso inicia cuando una solicitud de inicio de sesión es enviada desde el frontend hacia
el BFF (Backend For Frontend). El BFF canaliza esta solicitud hacia el microservicio de
seguridad, el cual se encarga de validar la información de inicio de sesión y emitir un token
de acceso junto con el rol correspondiente. Este token con su respectivo rol es almacenado
en la caché del BFF para su posterior uso.

Una vez completado este flujo, el frontend tiene la capacidad de enviar solicitudes
adicionales hacia los recursos del microservicio deseado, incluyendo el token obtenido
como parte de la autenticación.

Es responsabilidad del BFF verificar la validez del token, caducidad, así también
asegurarse de que el rol asociado al mismo sea válido.

