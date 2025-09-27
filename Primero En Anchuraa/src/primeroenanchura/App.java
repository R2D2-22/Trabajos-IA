package primeroenanchura;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

public class App {

    static final String ESTADO_INICIAL_DEFAULT = "7245 6831"; 
    static final String ESTADO_FINAL_DEFAULT = " 12345678";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

        while (opcion != 0) {
            mostrarMenu();
            try {
                opcion = scanner.nextInt();
                scanner.nextLine();

                if (opcion >= 1 && opcion <= 5) {
                    ejecutarBusqueda(opcion, scanner);
                } else if (opcion == 0) {
                    System.out.println("Saliendo del programa. ¡Adiós!");
                } else {
                    System.out.println("Opción no válida. Ingrese un número del 0 al 5.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada no válida. Ingrese un número.");
                scanner.nextLine();
                opcion = -1;
            }
        }
        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n==============================================");
        System.out.println("  SISTEMA DE BÚSQUEDA DE ALGORITMOS (8-Puzzle)");
        System.out.println("==============================================");
        System.out.println("Seleccione el algoritmo a ejecutar:");
        System.out.println("1. Búsqueda en Anchura");
        System.out.println("2. Búsqueda en Profundidad");
        System.out.println("3. Búsqueda Limitada");
        System.out.println("4. Costo Uniforme");
        System.out.println("5. Búsqueda A* (A-star)");
        System.out.println("0. Salir");
        System.out.print("Opción: ");
    }

    private static void ejecutarBusqueda(int opcion, Scanner scanner) {
        String estadoInicial = ESTADO_INICIAL_DEFAULT;
        String estadoFinal = ESTADO_FINAL_DEFAULT;
        int limite = 0;
        
        System.out.println("\n[Configuración de Estados]");
        System.out.print("Usar estado inicial predeterminado (" + ESTADO_INICIAL_DEFAULT + ")? (s/n): ");
        if (!scanner.nextLine().trim().equalsIgnoreCase("s")) {
            System.out.print("Ingrese el estado inicial (ej. 1234 5678): ");
            estadoInicial = scanner.nextLine().trim();
        }

        System.out.print("Usar estado final predeterminado (" + ESTADO_FINAL_DEFAULT + ")? (s/n): ");
        if (!scanner.nextLine().trim().equalsIgnoreCase("s")) {
            System.out.print("Ingrese el estado final (ej.  12345678): ");
            estadoFinal = scanner.nextLine().trim();
        }

        if (opcion == 3) {
            System.out.print("Ingrese el límite de profundidad (entero, ej. 20): ");
            try {
                limite = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Límite no válido. Usando límite por defecto (10).");
                limite = 10;
                scanner.nextLine();
            }
        }

        Nodo resultado = null;
        Arbol arbol = new Arbol(new Nodo(estadoInicial, null));

        try {
            switch (opcion) {
                case 1: 
                    resultado = arbol.realizarBusquedaEnAnchura(estadoFinal);
                    break;
                case 2: 
                    resultado = arbol.realizarBusquedaEnProfundidad(estadoFinal);
                    break;
                case 3: 
                    resultado = arbol.realizarBusquedaEnProfundidadLimitada(estadoFinal, limite);
                    break;
                case 4: 
                    resultado = arbol.realizarBusquedaPorCostoUniforme(estadoFinal);
                    break;
                case 5: 
                    resultado = arbol.realizarBusquedaAEstrella(estadoFinal);
                    break;
            }
        } catch (StackOverflowError e) {
            System.out.println("\n¡Error!");
        }
        
        if (resultado != null && resultado.estado.equals(estadoFinal)) {
            System.out.print("\n¿Desea ver los pasos detallados de la solución? (s/n): ");
            if (scanner.nextLine().trim().equalsIgnoreCase("s")) {
                mostrarPasos(resultado);
            }
        }
    }

    public static void mostrarPasos(Nodo nodoFinal) {
        List<String> ruta = new ArrayList<>();
        Nodo actual = nodoFinal;
        
        while (actual != null) {
            ruta.add(actual.estado);
            actual = actual.padre;
        }

        Collections.reverse(ruta);

        System.out.println("\n--- Secuencia de Pasos ---");
        System.out.println("Total de pasos: " + (ruta.size() - 1));
        int paso = 1;
        for (String estado : ruta) {
            System.out.println("--- Paso " + (paso++) + " ---");
            imprimirFormato3x3(estado);
        }
    }

    public static void imprimirFormato3x3(String estado) {
        String s = estado.replaceAll(" ", "_"); 
        System.out.println("| " + s.charAt(0) + " " + s.charAt(1) + " " + s.charAt(2) + " |");
        System.out.println("| " + s.charAt(3) + " " + s.charAt(4) + " " + s.charAt(5) + " |");
        System.out.println("| " + s.charAt(6) + " " + s.charAt(7) + " " + s.charAt(8) + " |");
    }
}