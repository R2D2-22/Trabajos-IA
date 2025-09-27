   public class Arbol {
    Nodo raiz;
    public Arbol(){
        this.raiz = null;
    }

    public boolean vacio(){
        return raiz == null;
    }
    public void insertar(int valor){
        this.raiz = insertar(this.raiz, valor);
        
    }
    private Nodo insertar(Nodo actual, int valor){
        if (actual == null) {
            return new Nodo(valor);
        }
        if (valor < actual.getValor()) {
            actual.setHijoIzquierdo(insertar(actual.getHijoIzquierdo(), valor));
        }
        else{
            actual.setHijoDerecho(insertar(actual.getHijoDerecho(), valor));
        }
        return actual;
    }

    public Nodo buscarNodo(int valor){
        return buscarNodo(this.raiz, valor);
    }

    private Nodo buscarNodo(Nodo actual, int valor){
        if (actual == null) {
             return null;
        }
        if (valor == actual.getValor()) {
            return actual;
        }

        if (valor < actual.getValor()) {
            return buscarNodo(actual.getHijoIzquierdo(), valor);
        }
        else{
            return buscarNodo(actual.getHijoDerecho(), valor); 
        }
    }

    public void imprimirArbol(){
        imprimirArbolInOrden(this.raiz);
    }

    private void imprimirArbolInOrden(Nodo actual){
        if (actual != null) {
            imprimirArbolInOrden(actual.getHijoIzquierdo());
            System.out.println(actual.getValor() + " ");
            imprimirArbolInOrden(actual.getHijoDerecho());
        }

    }
}
