package teatromorov5;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class TeatroMoroV5 {
    public static String nombreTeatro = "Teatro Moro";
    public static int entradasVendidas = 0;
    public static double totalRecaudado = 0.0;

    public static double[] precios = {30000.0, 18000.0, 15000.0, 13000.0};
    public static DecimalFormat df = new DecimalFormat("#,###");

    public static int aforoMaximo = 300;
    public static String[] localidades = {"VIP", "Platea Alta", "Platea Baja", "Palcos"};
    public static int aforoVip = aforoMaximo / 4;
    public static int aforoPlateaBaja = aforoMaximo / 4;
    public static int aforoPlateaAlta = aforoMaximo / 4;
    public static int aforoPalcos = aforoMaximo / 4;

    public static int[] aforos = {aforoVip, aforoPlateaBaja, aforoPlateaAlta, aforoPalcos};
    public static int[] aforosDisponibles = {aforoVip, aforoPlateaBaja, aforoPlateaAlta, aforoPalcos};

    public static int[] entradasVendidasPorLocalidad = {0, 0, 0, 0};
    public static double[] totalRecaudadoPorLocalidad = {0.0, 0.0, 0.0, 0.0};

    public static int vipCounter = 1;
    public static int plateaAltaCounter = 1;
    public static int plateaBajaCounter = 1;
    public static int palcosCounter = 1;

    public static ArrayList<Entrada> ventas = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        System.out.println("==========================");
        System.out.println("Bienvenido al " + nombreTeatro);
        System.out.println("===========================");

        do {
            menuPrincipal();
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> ventaEntradas(scanner);
                case 2 -> visualizarResumenVentas();
                case 3 -> generarBoletas(scanner);
                case 4 -> calcularIngresosTotales();
                case 5 -> System.out.println("Gracias por visitar el " + nombreTeatro + ". ¡Hasta luego!");
                default -> System.out.println("Opción no válida. Por favor, seleccione una opción del menú.");
            }
        } while (opcion != 5);
        scanner.close();
    }

    public static class Entrada {
        String zona;
        String tipoCliente;
        int cantidad;
        double precioUnitario;
        double descuentoAplicado;
        double totalPagar;
        ArrayList<String> codigosAsientos;

        public Entrada(String zona, String tipoCliente, int cantidad, double precioUnitario, double descuentoAplicado, double totalPagar, ArrayList<String> codigosAsientos) {
            this.zona = zona;
            this.tipoCliente = tipoCliente;
            this.cantidad = cantidad;
            this.precioUnitario = precioUnitario;
            this.descuentoAplicado = descuentoAplicado;
            this.totalPagar = totalPagar;
            this.codigosAsientos = codigosAsientos;
        }
    }

    public static void menuPrincipal() {
        System.out.println("\n===========================");
        System.out.println("Menú Principal");
        System.out.println("===========================");
        System.out.println("1. Venta de entradas");
        System.out.println("2. Visualizar resumen de ventas");
        System.out.println("3. Imprimir boleta de compra");
        System.out.println("4. Calcular ingresos totales");
        System.out.println("5. Salir");
        System.out.println("===========================");
    }

    public static void ventaEntradas(Scanner scanner) {
        String continuar;
        do {
            System.out.println("Seleccione la localidad:");
            for (int i = 0; i < localidades.length; i++) {
                System.out.println((i + 1) + ". " + localidades[i] + " (Precio: $" + df.format(precios[i]) + ")");
            }
            System.out.print("Ingrese el número de la localidad: ");
            int opcionZona = scanner.nextInt();
            scanner.nextLine();

            if (opcionZona < 1 || opcionZona > localidades.length) {
                System.out.println("Localidad inválida.");
                return;
            }

            int index = opcionZona - 1;

            System.out.print("¿Cuántas entradas desea comprar?: ");
            int cantidad = scanner.nextInt();
            scanner.nextLine();

            if (cantidad <= 0 || cantidad > aforosDisponibles[index]) {
                System.out.println("Cantidad no disponible.");
                return;
            }

            System.out.print("Ingrese su edad: ");
            int edadCliente = scanner.nextInt();
            scanner.nextLine();

            String tipoCliente = "General";
            double descuento = 0.0;

            if (edadCliente < 18) {
                tipoCliente = "Estudiante";
                descuento = 0.10;
            } else if (edadCliente >= 60) {
                tipoCliente = "Tercera edad";
                descuento = 0.15;
            }

            double precioBase = precios[index];
            double descuentoAplicado = precioBase * descuento;
            double precioFinal = precioBase - descuentoAplicado;
            double totalPagar = precioFinal * cantidad;

            ArrayList<String> codigosAsientos = new ArrayList<>();
            for (int i = 0; i < cantidad; i++) {
                codigosAsientos.add(generarCodigoAsiento(localidades[index]));
            }

            ventas.add(new Entrada(localidades[index], tipoCliente, cantidad, precioBase, descuentoAplicado, totalPagar, codigosAsientos));

            entradasVendidas += cantidad;
            totalRecaudado += totalPagar;
            entradasVendidasPorLocalidad[index] += cantidad;
            totalRecaudadoPorLocalidad[index] += totalPagar;
            aforosDisponibles[index] -= cantidad;

            System.out.println("Venta realizada con éxito.");
            imprimirUltimaBoleta();

            System.out.print("¿Desea realizar otra compra? (s/n): ");
            continuar = scanner.nextLine();

        } while (continuar.equalsIgnoreCase("s"));
    }

    public static String generarCodigoAsiento(String zona) {
        return switch (zona) {
            case "VIP" -> "VP-" + String.format("%02d", vipCounter++);
            case "Platea Alta" -> "PA-" + String.format("%02d", plateaAltaCounter++);
            case "Platea Baja" -> "PB-" + String.format("%02d", plateaBajaCounter++);
            case "Palcos" -> "PC-" + String.format("%02d", palcosCounter++);
            default -> "XX-00";
        };
    }

    public static void visualizarResumenVentas() {
        if (ventas.isEmpty()) {
            System.out.println("No se han registrado ventas aún.");
            return;
        }

        System.out.println("\nResumen de Ventas:");
        for (int i = 0; i < ventas.size(); i++) {
            Entrada e = ventas.get(i);
            System.out.println("Boleta N°" + (i + 1) + " | Zona: " + e.zona + ", Cliente: " + e.tipoCliente + ", Cantidad: " + e.cantidad + ", Total pagado: $" + df.format(e.totalPagar));
        }
    }

    public static void generarBoletas(Scanner scanner) {
        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas para generar boletas.");
            return;
        }

        System.out.println("\n¿Desea:");
        System.out.println("1. Imprimir boleta por número de boleta");
        System.out.println("2. Imprimir boleta de la última venta");
        System.out.print("Seleccione una opción: ");
        int opcion = scanner.nextInt();
        scanner.nextLine();

        if (opcion == 1) {
            System.out.print("Ingrese el número de boleta que desea imprimir: ");
            int num = scanner.nextInt();
            scanner.nextLine();

            if (num <= 0 || num > ventas.size()) {
                System.out.println("Número de boleta inválido.");
                return;
            }

            Entrada e = ventas.get(num - 1);
            imprimirBoleta(e, num);

        } else if (opcion == 2) {
            Entrada e = ventas.get(ventas.size() - 1);
            imprimirBoleta(e, ventas.size());
        } else {
            System.out.println("Opción inválida.");
        }
    }

    public static void imprimirUltimaBoleta() {
        if (ventas.isEmpty()) return;
        Entrada e = ventas.get(ventas.size() - 1);
        imprimirBoleta(e, ventas.size());
    }

    public static void imprimirBoleta(Entrada e, int numero) {
        System.out.println("\n============= Teatro Moro =============");
        System.out.println("\n--- Boleta N° " + numero + " ---");
        System.out.println("==========================================");
        System.out.println("Ubicación: " + e.zona);
        System.out.println("Tipo de Cliente: " + e.tipoCliente);
        System.out.println("Cantidad de entradas: " + e.cantidad);
        System.out.println("Precio unitario: $" + df.format(e.precioUnitario));
        double porcentajeDescuento = (e.descuentoAplicado / e.precioUnitario) * 100;
        System.out.println("Descuento aplicado: " + (int) porcentajeDescuento + "%");
        System.out.println("Total pagado: $" + df.format(e.totalPagar));
        System.out.println("Asientos:");
        for (String asiento : e.codigosAsientos) {
            System.out.println("- " + asiento);
        }
        System.out.println("==========================================");
        System.out.println("¡Gracias por su compra!");
        System.out.println("==========================================");
    }

    public static void calcularIngresosTotales() {
        System.out.println("Entradas vendidas: " + entradasVendidas);
        System.out.println("Total recaudado: $" + df.format(totalRecaudado));
    }
}
