# Microservicios en Java - NTT

## Descripción
Este repositorio contiene una colección de microservicios en Java desarrollados como parte del Bootcamp de NTT. 
Cada servicio se centra en un dominio específico e interactúa con los demás para formar una arquitectura completa de microservicios.

## Microservicios
- **accounts-service**: Gestiona operaciones relacionadas con cuentas.
- **api-gateway**: Punto de entrada para todas las solicitudes de clientes.
- **credits-service**: Maneja operaciones relacionadas con créditos.
- **customers-service**: Gestiona información de clientes.
- **discovery-server**: Registro de servicios para la detección de otros servicios.
- **transactions-service**: Gestiona operaciones de transacciones.

## Requisitos
- Java 11 o superior
- Maven 3.6+
- Docker (opcional, para contenerización)

## Configuración
1. **Clonar el repositorio**:
   ```bash
   git clone https://github.com/DarkCat777/Java_Microservicios_NTT.git
   cd Java_Microservicios_NTT
   ```
2. **Construir el proyecto**:
   ```bash
   mvn clean install
   ```
3. **Ejecutar los servicios**:
   ```bash
   cd [directorio-del-servicio]
   mvn spring-boot:run
   ```

## Contacto
Para preguntas o sugerencias, abre un issue en el repositorio.
