package primeroenanchura;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Arbol {
    Nodo raiz;

    public Arbol(Nodo raiz) {
        this.raiz = raiz;
    }

    public Nodo realizarBusquedaEnAnchura(String objetivo){
        long tiempoInicio = System.nanoTime();
        long nodosGenerados = 0;
        
        Queue<Nodo> cola = new LinkedList<>();
        HashSet<String> visitados = new HashSet<>();
        cola.add(raiz);
        visitados.add(raiz.estado);
        boolean encontrado = false;
        Nodo actual = null;
        
        while(!encontrado && !cola.isEmpty()){
            actual = cola.poll();
            System.out.println("Procesando => "+actual.estado);
            
            if(actual.estado.equals(objetivo)){
                encontrado = true;
            }else{
                List<String> sucesores = actual.obtenerSucesores();
                for(String sucesor: sucesores){
                    if(visitados.contains(sucesor))
                        continue;
                    
                    Nodo nuevoNodo = new Nodo(sucesor, actual);
                    nodosGenerados++; 
                    
                    System.out.println("Agergando a cola => "+sucesor);
                    cola.add(nuevoNodo);
                    visitados.add(sucesor);
                }
            }
        }
        
        long tiempoFin = System.nanoTime();
        double tiempoTotalMS = (tiempoFin - tiempoInicio) / 1_000_000.0;
        
        System.out.println("\n--- Busqueda en Anchura ---");
        if (actual != null && actual.estado.equals(objetivo)) {
            System.out.println("Solucion Encontrada en " + actual.profundidad + " pasos.");
        } else {
            System.out.println("No se encontro solucion.");
        }
        System.out.println("Nodos Generados: " + (nodosGenerados + 1)); 
        System.out.println("Tiempo Total: " + String.format("%.3f", tiempoTotalMS) + " milisegundos.");
        System.out.println("---------------------------------");
        
        return actual;
    }

    public Nodo realizarBusquedaEnProfundidad(String objetivo){
        long tiempoInicio = System.nanoTime();
        long nodosGenerados = 0;
        
        Deque<Nodo> pila = new LinkedList<>(); 
        HashSet<String> visitados = new HashSet<>(); 
        
        pila.push(raiz); 
        visitados.add(raiz.estado);
        boolean encontrado = false;
        Nodo actual = null;
        
        while(!encontrado && !pila.isEmpty()){
            actual = pila.pop(); 
            
            System.out.println("Procesando => "+actual.estado);
            
            if(actual.estado.equals(objetivo)){
                encontrado = true;
            }else{
                List<String> sucesores = actual.obtenerSucesores();
                
                for(String sucesor: sucesores){
                    if(visitados.contains(sucesor))
                        continue;
                    
                    Nodo nuevoNodo = new Nodo(sucesor, actual);
                    nodosGenerados++; 
                    
                    System.out.println("Agergando a pila => "+sucesor);
                    pila.push(nuevoNodo); 
                    visitados.add(sucesor);
                }
            }
        }
        
        long tiempoFin = System.nanoTime();
        double tiempoTotalMS = (tiempoFin - tiempoInicio) / 1_000_000.0;
        
        System.out.println("\n--- Busqueda en Profundidad ---");
        if (actual != null && actual.estado.equals(objetivo)) {
            System.out.println("Solucion Encontrada en " + actual.profundidad + " pasos.");
        } else {
            System.out.println("No se encontro solucion.");
        }
        System.out.println("Nodos Generados: " + (nodosGenerados + 1)); 
        System.out.println("Tiempo Total: " + String.format("%.3f", tiempoTotalMS) + " milisegundos.");
        System.out.println("---------------------------------");

        return actual;
    }

    public Nodo realizarBusquedaPorCostoUniforme(String objetivo){
        long tiempoInicio = System.nanoTime();
        long nodosGenerados = 0;
        
        PriorityQueue<Nodo> colaPrioridad = new PriorityQueue<>(); 
        HashSet<String> visitados = new HashSet<>(); 
        
        colaPrioridad.add(raiz); 
        visitados.add(raiz.estado);
        boolean encontrado = false;
        Nodo actual = null;
        
        while(!encontrado && !colaPrioridad.isEmpty()){
            actual = colaPrioridad.poll(); 
            
            System.out.println("Procesando (UCS) => "+actual.estado + " | Costo: " + actual.costo);
            
            if(actual.estado.equals(objetivo)){
                encontrado = true;
            }else{
                List<String> sucesores = actual.obtenerSucesores();
                
                for(String sucesor: sucesores){
                    if(visitados.contains(sucesor))
                        continue;
                    
                    Nodo nuevoNodo = new Nodo(sucesor, actual);
                    nodosGenerados++; 
                    
                    System.out.println("Agregando a cola de prioridad => "+sucesor + " | Costo: " + nuevoNodo.costo);
                    colaPrioridad.add(nuevoNodo);
                    visitados.add(sucesor);
                }
            }
        }
        
        long tiempoFin = System.nanoTime();
        double tiempoTotalMS = (tiempoFin - tiempoInicio) / 1_000_000.0;

        System.out.println("\n--- Costo Uniforme (UCS) ---");
        if (actual != null && actual.estado.equals(objetivo)) {
            System.out.println("Solucion Encontrada con costo " + actual.costo + ".");
        } else {
            System.out.println("No se encontro solucion.");
        }
        System.out.println("Nodos Generados: " + (nodosGenerados + 1)); 
        System.out.println("Tiempo Total: " + String.format("%.3f", tiempoTotalMS) + " milisegundos.");
        System.out.println("---------------------------------");

        return actual;
    }

    public Nodo realizarBusquedaEnProfundidadLimitada(String objetivo, int limite){
        long tiempoInicio = System.nanoTime();
        long nodosGenerados = 0;

        Deque<Nodo> pila = new LinkedList<>(); 
        HashSet<String> visitados = new HashSet<>(); 
        
        pila.push(raiz);
        visitados.add(raiz.estado);
        boolean encontrado = false;
        Nodo actual = null;
        
        System.out.println("--- INICIANDO DLS con Limite = " + limite + " ---");
        
        while(!encontrado && !pila.isEmpty()){
            actual = pila.pop(); 
            
            System.out.println("Procesando (DLS) => "+actual.estado + " | Profundidad: " + actual.profundidad);
            
            if(actual.estado.equals(objetivo)){
                encontrado = true;
                System.out.println("Solucion Encontrada");
            }
            else if(actual.profundidad < limite){
                
                List<String> sucesores = actual.obtenerSucesores();
                
                for(String sucesor: sucesores){
                    if(visitados.contains(sucesor))
                        continue;
                    
                    Nodo nuevoNodo = new Nodo(sucesor, actual);
                    nodosGenerados++; 
                    
                    System.out.println("Agregando a pila => "+sucesor + " | profundidad: " + nuevoNodo.profundidad);
                    pila.push(nuevoNodo);
                    visitados.add(sucesor);
                }
            } else {
                System.out.println("Nodo en limite (" + actual.profundidad + "). No expandido.");
            }
        }
        
        long tiempoFin = System.nanoTime();
        double tiempoTotalMS = (tiempoFin - tiempoInicio) / 1_000_000.0;

        System.out.println("\n--- Busqueda Limitada (DLS - Limite: " + limite + ") ---");
        if (actual != null && actual.estado.equals(objetivo)) {
            System.out.println("Solucion encontrada en " + actual.profundidad + " pasos.");
        } else {
            System.out.println("No se encontro solucion dentro del limite.");
        }
        System.out.println("Nodos generados: " + (nodosGenerados + 1)); 
        System.out.println("Tiempo total: " + String.format("%.3f", tiempoTotalMS) + " milisegundos.");
        System.out.println("---------------------------------");
        
        return actual;
    }

    public Nodo realizarBusquedaAEstrella(String objetivo){
        long tiempoInicio = System.nanoTime();
        long nodosGenerados = 0;
        
        PriorityQueue<Nodo> colaPrioridad = new PriorityQueue<>(); 
        HashSet<String> expandidos = new HashSet<>(); 
        
        colaPrioridad.add(raiz); 
        boolean encontrado = false;
        Nodo actual = null;
        
        System.out.println("--- INICIANDO BUSQUEDA A* (Heuristica) ---");
        
        while(!encontrado && !colaPrioridad.isEmpty()){
            actual = colaPrioridad.poll(); 
            
            if (expandidos.contains(actual.estado)) {
                continue; 
            }
            expandidos.add(actual.estado);
            
            int f_actual = actual.costo + actual.heuristica;
            System.out.println("Procesando (A*) => "+actual.estado + " | g(n): " + actual.costo + " | h(n): " + actual.heuristica + " | f(n): " + f_actual);
            
            if(actual.estado.equals(objetivo)){
                encontrado = true;
                System.out.println("A*: Solucion Encontrada");
            }
            else{
                List<String> sucesores = actual.obtenerSucesores();
                
                for(String sucesor: sucesores){
                    Nodo nuevoNodo = new Nodo(sucesor, actual);
                    
                    if(!expandidos.contains(sucesor)){
                        nodosGenerados++; 
                        int f_sucesor = nuevoNodo.costo + nuevoNodo.heuristica;
                        System.out.println("Agregando a cola prioridad => "+sucesor + " | f(n): " + f_sucesor);
                        colaPrioridad.add(nuevoNodo);
                    }
                }
            }
        }
        
        long tiempoFin = System.nanoTime();
        double tiempoTotalMS = (tiempoFin - tiempoInicio) / 1_000_000.0;

        System.out.println("\n--- Búsqueda A*  ---");
        if (actual != null && actual.estado.equals(objetivo)) {
             System.out.println("Solucion Encontrada con costo " + actual.costo + ".");
        } else {
            System.out.println("La busqueda termino sin encontrar el objetivo.");
        }
        System.out.println("Nodos generados: " + (nodosGenerados + 1)); 
        System.out.println("Tiempo total: " + String.format("%.3f", tiempoTotalMS) + " milisegundos.");
        System.out.println("---------------------------------");
        
        return actual;
    }
}