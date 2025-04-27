# Teatro Moro - Sistema de Venta de Entradas

**Descripción:**  
Sistema en Java para gestionar la venta de entradas del Teatro Moro. Permite seleccionar localidad, aplicar descuentos por edad, asignar asientos numerados y generar boletas.

## Funciones principales:

- Venta de entradas para las siguientes zonas:
  - VIP
  - Platea Alta
  - Platea Baja
  - Palcos
- Aplicación de descuentos automáticos:
  - **10%** de descuento para estudiantes (menores de 18 años).
  - **15%** de descuento para personas de tercera edad (60 años o más).
- Asignación automática de **números de asiento** por localidad:
  - Ejemplo: `VP-01`, `PA-02`, `PB-03`, `PC-04`
- Generación de boletas detalladas que incluyen:
  - Ubicación
  - Tipo de cliente
  - Cantidad de entradas
  - Precio unitario
  - Descuento aplicado (porcentaje)
  - Total pagado
  - Asientos asignados
- Resumen de todas las ventas realizadas.
- Cálculo de total de ingresos y cantidad de entradas vendidas.

## Requisitos:

- Java 17 o superior.

## Cómo usar:

### Desde el código fuente:

1. Abrir el archivo `TeatroMoroV5.java` en un IDE de tu preferencia (IntelliJ, Eclipse, VS Code, etc.).
2. Ejecutar la clase principal `TeatroMoroV5`.
3. Usar el menú interactivo para:
   - Vender entradas.
   - Visualizar resumen de ventas.
   - Imprimir boletas (por número o última venta).
   - Consultar ingresos totales.

### Desde el archivo `.jar`:

1. Dentro de la carpeta `dist/` encontrarás un archivo `TeatroMoroV5.jar` ya generado.
2. Para ejecutarlo, abre una terminal y escribe:

```bash
java -jar dist/TeatroMoroV5.jar
```

3. El sistema iniciará directamente sin necesidad de compilar.

## Notas:

- El aforo inicial es de 300 entradas (distribuido en 4 zonas).
- Cada compra genera su boleta con número de asiento y descuento aplicado automáticamente.

