
import java.util.Scanner;
public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        Arbol arbol = new Arbol();
        int buscar;
        for(int i=0; i<5; i++){
            double valor = Math.random() * 10;
            int valorInt = (int) valor;
            arbol.insertar(valorInt);
            System.out.println(valorInt);
        }
        System.out.println("Arbol en orden (inOrden):");
        arbol.imprimirArbol();
       
        System.out.println("Ingrese un nodo a buscar: ");
        buscar = scanner.nextInt();
        
        if (arbol.buscarNodo(buscar) != null) {
            System.out.println("El nodo fue encontrado");
        }
        else{
            System.out.println("El nodo no fue encontrado");
        }

    }
}
