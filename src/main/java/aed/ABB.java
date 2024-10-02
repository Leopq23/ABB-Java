package aed;

import java.util.*;

// Todos los tipos de datos "Comparables" tienen el método compareTo()
// elem1.compareTo(elem2) devuelve un entero. Si es mayor a 0, entonces elem1 > elem2
public class ABB<T extends Comparable<T>> implements Conjunto<T> {
    
    private Nodo _raiz;
    private int _cardinal;

    private class Nodo {
        T valor;
        Nodo izq;
        Nodo der;
    
        Nodo(T v){
            this.valor = v;
            this.izq = null;
            this.der = null;
        }
    }

    public ABB() {
        _raiz = null;
        _cardinal = 0;
    }

    public int cardinal() {
       return _cardinal;
    }

    public T minimo(){
        if (_raiz == null) {
            return null;
        }

        Nodo nodo = _raiz;
        while (nodo.izq != null) {
            nodo = nodo.izq;
        }

        return nodo.valor;
    }

    public T maximo(){
        if (_raiz == null) {
            return null;
        }

        Nodo nodo = _raiz;
        while (nodo.der != null) {
            nodo = nodo.der;
        }

        return nodo.valor;
    }

    public void insertar(T elem){
        if (_raiz == null) {
            _raiz = new Nodo(elem);
            _cardinal++;
        } else {
            insertarRecursivo(_raiz, elem);
        }
    }

    private void insertarRecursivo(Nodo nodo, T elem) {
        if (elem.compareTo(nodo.valor) < 0) {
            if (nodo.izq == null) {
                nodo.izq = new Nodo(elem);
                _cardinal++;
            } else {
                insertarRecursivo(nodo.izq, elem);
            }
        } else if (elem.compareTo(nodo.valor) > 0) {
            if (nodo.der == null) {
                nodo.der = new Nodo(elem);
                _cardinal++;
            } else {
                insertarRecursivo(nodo.der, elem);
            }
        }
    }

    public boolean pertenece(T elem){
        return perteneceRecursivo(_raiz, elem);
    }

    private boolean perteneceRecursivo(Nodo nodo, T elem) {
        if (nodo == null) {
            return false;
        }

        if (elem.compareTo(nodo.valor) == 0) {
            return true;
        } else if (elem.compareTo(nodo.valor) < 0) {
            return perteneceRecursivo(nodo.izq, elem);
        } else {
            return perteneceRecursivo(nodo.der, elem);
        }
    }

    public void eliminar(T elem){
        if (pertenece(elem)) {
            _raiz = eliminarRecursivo(_raiz, elem);
            _cardinal--;
        }
    }

    private Nodo eliminarRecursivo(Nodo nodo, T elem) {
        if (nodo == null) {
            return nodo;
        }

        if (elem.compareTo(nodo.valor) < 0) {
            nodo.izq = eliminarRecursivo(nodo.izq, elem);
        } else if (elem.compareTo(nodo.valor) > 0) {
            nodo.der = eliminarRecursivo(nodo.der, elem);
        } else {
            if (nodo.izq == null) {
                return nodo.der;
            } else if (nodo.der == null) {
                return nodo.izq;
            }

            nodo.valor = minValor(nodo.der);
            nodo.der = eliminarRecursivo(nodo.der, nodo.valor);
        }

        return nodo;
    }

    private T minValor(Nodo nodo) {
        T minv = nodo.valor;
        while (nodo.izq != null) {
            minv = nodo.izq.valor;
            nodo = nodo.izq;
        }
        return minv;
    }

    public String toString(){
        StringBuilder result = new StringBuilder();
        result.append("{");
        imprimirEnOrden(_raiz, result);
        result.append("}");
        return result.toString();
    }

    private void imprimirEnOrden(Nodo nodo, StringBuilder result) {
        if (nodo != null) {
            imprimirEnOrden(nodo.izq, result);
            if (result.length() > 1) {
                result.append(",");
            }
            result.append(nodo.valor);
            imprimirEnOrden(nodo.der, result);
        }
    }

    private class ABB_Iterador implements Iterador<T> {
        private Nodo _actual;
        private ABB<T> subarbol;
        
        public ABB_Iterador() {
            _actual = _raiz;
            subarbol = new ABB<>();
            subarbol._raiz = _raiz;
            subarbol._cardinal = _cardinal;
        }

        public boolean haySiguiente() {            
            return subarbol != null ;
        }
    
        public T siguiente() {
            if (!haySiguiente()) {
                return null;
            }
            T valor = subarbol.minimo();
            subarbol.eliminar(valor);
            return valor;
        }

    }


    public Iterador<T> iterador() {
        return new ABB_Iterador();
    }

}
