
import java.util.*;

public class Dijkstra {

    static class Nodo implements Comparable<Nodo> {
        public String nombre;
        public int distancia;

        public Nodo(String nombre, int distancia) {
            this.nombre = nombre;
            this.distancia = distancia;
        }

        @Override
        public int compareTo(Nodo otro) {
            return Integer.compare(this.distancia, otro.distancia);
        }
    }

    public static Map<String, Integer> encontrarCaminosMasCortos(Map<String, List<Nodo>> grafo, String nodoInicial) {
        Map<String, Integer> distancias = new HashMap<>();
        for (String nodo : grafo.keySet()) {
            distancias.put(nodo, Integer.MAX_VALUE);
        }
        distancias.put(nodoInicial, 0);

        PriorityQueue<Nodo> colaPrioridad = new PriorityQueue<>();
        colaPrioridad.add(new Nodo(nodoInicial, 0));

        while (!colaPrioridad.isEmpty()) {
            Nodo nodoActual = colaPrioridad.poll();
            String nombreActual = nodoActual.nombre;
            int distanciaActual = nodoActual.distancia;

            if (distanciaActual > distancias.get(nombreActual)) {
                continue;
            }

            for (Nodo vecino : grafo.get(nombreActual)) {
                int nuevaDistancia = distanciaActual + vecino.distancia;

                if (nuevaDistancia < distancias.get(vecino.nombre)) {
                    distancias.put(vecino.nombre, nuevaDistancia);
                    colaPrioridad.add(new Nodo(vecino.nombre, nuevaDistancia));
                }
            }
        }
        return distancias;
    }

    public static void main(String[] args) {
        Map<String, List<Nodo>> grafo = new HashMap<>();
        grafo.put("A", Arrays.asList(new Nodo("B", 1), new Nodo("C", 4)));
        grafo.put("B", Arrays.asList(new Nodo("A", 1), new Nodo("C", 2), new Nodo("D", 5)));
        grafo.put("C", Arrays.asList(new Nodo("A", 4), new Nodo("B", 2), new Nodo("D", 1)));
        grafo.put("D", Arrays.asList(new Nodo("B", 5), new Nodo("C", 1), new Nodo("E", 3)));
        grafo.put("E", Arrays.asList(new Nodo("D", 3)));

        String nodoInicial = "A";
        Map<String, Integer> caminosMasCortos = encontrarCaminosMasCortos(grafo, nodoInicial);

        System.out.println("Distancias mas cortas desde el nodo '" + nodoInicial + "':");
        for (Map.Entry<String, Integer> entrada : caminosMasCortos.entrySet()) {
            System.out.println("  A '" + entrada.getKey() + "': " + entrada.getValue());
        }
    }
}