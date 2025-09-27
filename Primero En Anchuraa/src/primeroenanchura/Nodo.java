package primeroenanchura;

import java.util.ArrayList;
import java.util.List;

public class Nodo implements Comparable<Nodo> { 
    String estado;
    Nodo padre;
    int profundidad;
    int costo;
    int heuristica; 

    static final String ESTADO_OBJETIVO = " 12345678"; 

    public Nodo(String estado, Nodo padre) {
        this.estado = estado;
        this.padre = padre;
        
        if (padre == null) {
            this.costo = 0;
            this.profundidad = 0;
        } else {
            this.costo = padre.costo + 1;
            this.profundidad = padre.profundidad + 1;
        }
        
        this.heuristica = calcularHeuristicaMisplacedTiles(); 
    }

    // calcular la heuristica del número de fichas mal colocadas
    private int calcularHeuristicaMisplacedTiles() {
        int malColocadas = 0;
        for (int i = 0; i < estado.length(); i++) {
            char fichaActual = estado.charAt(i);
            char fichaObjetivo = ESTADO_OBJETIVO.charAt(i);
            
            // si no es el espacio y la posicion no coincide con el objetivo
            if (fichaActual != ' ' && fichaActual != fichaObjetivo) {
                malColocadas++;
            }
        }
        return malColocadas;
    }


    @Override
    public int compareTo(Nodo otro) {
        int f_this = this.costo + this.heuristica;
        int f_otro = otro.costo + otro.heuristica;
        
        return Integer.compare(f_this, f_otro); 
    }

    public List<String> obtenerSucesores() {

        List<String> successors = new ArrayList<String>();

        switch (estado.indexOf(" ")) {
            case 0: {
                successors.add(estado.replace(estado.charAt(0), '*').replace(estado.charAt(1), estado.charAt(0)).replace('*', estado.charAt(1)));
                successors.add(estado.replace(estado.charAt(0), '*').replace(estado.charAt(3), estado.charAt(0)).replace('*', estado.charAt(3)));
                break;
            }
            case 1: {
                successors.add(estado.replace(estado.charAt(1), '*').replace(estado.charAt(0), estado.charAt(1)).replace('*', estado.charAt(0)));
                successors.add(estado.replace(estado.charAt(1), '*').replace(estado.charAt(2), estado.charAt(1)).replace('*', estado.charAt(2)));
                successors.add(estado.replace(estado.charAt(1), '*').replace(estado.charAt(4), estado.charAt(1)).replace('*', estado.charAt(4)));
                break;
            }
            case 2: {

                successors.add(estado.replace(estado.charAt(2), '*').replace(estado.charAt(1), estado.charAt(2)).replace('*', estado.charAt(1)));
                successors.add(estado.replace(estado.charAt(2), '*').replace(estado.charAt(5), estado.charAt(2)).replace('*', estado.charAt(5)));
                break;
            }
            case 3: {
                successors.add(estado.replace(estado.charAt(3), '*').replace(estado.charAt(0), estado.charAt(3)).replace('*', estado.charAt(0)));
                successors.add(estado.replace(estado.charAt(3), '*').replace(estado.charAt(4), estado.charAt(3)).replace('*', estado.charAt(4)));
                successors.add(estado.replace(estado.charAt(3), '*').replace(estado.charAt(6), estado.charAt(3)).replace('*', estado.charAt(6)));
                break;
            }
            case 4: {
                successors.add(estado.replace(estado.charAt(4), '*').replace(estado.charAt(1), estado.charAt(4)).replace('*', estado.charAt(1)));
                successors.add(estado.replace(estado.charAt(4), '*').replace(estado.charAt(3), estado.charAt(4)).replace('*', estado.charAt(3)));
                successors.add(estado.replace(estado.charAt(4), '*').replace(estado.charAt(5), estado.charAt(4)).replace('*', estado.charAt(5)));
                successors.add(estado.replace(estado.charAt(4), '*').replace(estado.charAt(7), estado.charAt(4)).replace('*', estado.charAt(7)));
                break;
            }
            case 5: {
                successors.add(estado.replace(estado.charAt(5), '*').replace(estado.charAt(2), estado.charAt(5)).replace('*', estado.charAt(2)));
                successors.add(estado.replace(estado.charAt(5), '*').replace(estado.charAt(4), estado.charAt(5)).replace('*', estado.charAt(4)));
                successors.add(estado.replace(estado.charAt(5), '*').replace(estado.charAt(8), estado.charAt(5)).replace('*', estado.charAt(8)));
                break;
            }
            case 6: {
                successors.add(estado.replace(estado.charAt(6), '*').replace(estado.charAt(3), estado.charAt(6)).replace('*', estado.charAt(3)));
                successors.add(estado.replace(estado.charAt(6), '*').replace(estado.charAt(7), estado.charAt(6)).replace('*', estado.charAt(7)));
                break;

            }
            case 7: {
                successors.add(estado.replace(estado.charAt(7), '*').replace(estado.charAt(4), estado.charAt(7)).replace('*', estado.charAt(4)));
                successors.add(estado.replace(estado.charAt(7), '*').replace(estado.charAt(6), estado.charAt(7)).replace('*', estado.charAt(6)));
                successors.add(estado.replace(estado.charAt(7), '*').replace(estado.charAt(8), estado.charAt(7)).replace('*', estado.charAt(8)));
                break;
            }
            case 8: {
                successors.add(estado.replace(estado.charAt(8), '*').replace(estado.charAt(5), estado.charAt(8)).replace('*', estado.charAt(5)));
                successors.add(estado.replace(estado.charAt(8), '*').replace(estado.charAt(7), estado.charAt(8)).replace('*', estado.charAt(7)));
                break;
            }
        }
        return successors;
    }
}
