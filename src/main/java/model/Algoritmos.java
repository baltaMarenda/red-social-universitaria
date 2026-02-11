package model;

import java.util.*;

public class Algoritmos {
   public static List<Arista> prim(Grafo grafo) {


       List<Arista> arbolMinimo = new ArrayList<>();
       Set<User> visitados = new HashSet<>();

       PriorityQueue<Arista> cola = new PriorityQueue<>(
               Comparator.comparingInt(Arista::getPeso));

       //Candidatos
       Set<User> usuarios = grafo.getUsuarios();

       if (usuarios.isEmpty()){
           return arbolMinimo;
       }
       //Empieza desde cualquier nodo
       User inicial = usuarios.iterator().next();
       visitados.add(inicial);

       //Se agrega sus aristas a la cola
       cola.addAll(grafo.getAdyacentes(inicial));

       //Muentras no se haya trabajado con todos los nodos
       while (!cola.isEmpty() && visitados.size() < usuarios.size()){
           //Arista de menor peso
           Arista aristaMin = cola.poll();

           User destino = aristaMin.getDestino();


           if (!visitados.contains(destino)){
               //Se agrega la arista de menor peso al arbol
               arbolMinimo.add(aristaMin);
               //Marcamos el nodo como visitado
               visitados.add(destino);

               //Agregamos nuevas aristas candidatas
               for (Arista a: grafo.getAdyacentes(destino)){
                   if(!visitados.contains(a.getDestino())){
                       cola.add(a);
                   }
               }
           }
       }

       //Validacion por si el grafo no es conexo
       if (visitados.size() != usuarios.size()){
           throw new IllegalStateException("El grafo no es conexo, no es posible construir el arbol");
       }

       return arbolMinimo;
   }

    public static int costoTotal(List<Arista> aristas) {
        int total = 0;
        for (Arista a : aristas) {
            total += a.getPeso();
        }
        return total;
    }

}
