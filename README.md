<h1>Aplicación de mini E-commerce con microservicios con Spring</h1>
<p>App de mini e-commerce como seguimiento de curso de aprendizaje sobre microservicios con Spring 4</p>

<h3>Módulos del sistema</h3>
<ul>
  <li>inventory-service</li>
  <li>order-service</li>
  <li>product-service</li>
  <li>api-gateway</li>
  <li>discovery-service</li>
  <li>notification-service</li>
</ul>

<h3>Introducción</h3>
<p>
  Se plantea como primeros pasos la creación de los 3 microservicios de producto, ordenes e inventario, cada uno cuenta con la estructura de MVC, en donde además se 
  manejan excepciones globales y espcificas para los casos mas importantes, manejo de validaciones con SpringValidation, lombok, dto.
</p>

<h3>Configuraciones</h3>
<p>
  Las configuraciones se manejan desde una aplicación centralizadas enlazada a una cuenta de github, se especifican las conexiones a las bases de datos en los archivos de 
  configuración en un proyecto llamado config-data; se incluyen las dependencias necesarias para manejar los micorservicios: netflix-eureka-client, spring-boot-actuator,
  loadbalancer, spring-cloud-server-webflux.
</p>
