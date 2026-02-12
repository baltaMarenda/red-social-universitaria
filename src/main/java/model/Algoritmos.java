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
    //---- dijkstra

    public static Map<User, Integer> dijkstra(Grafo grafo, User origen) {
        // Mapa para guardar la distancia mínima desde el origen a cada usuario
        Map<User, Integer> distancias = new HashMap<>();
        // Priorizamos los usuarios según la distancia acumulada más corta
        PriorityQueue<NodoDistancia> cola = new PriorityQueue<>(
                Comparator.comparingInt(NodoDistancia::getDistancia));

        // Inicializamos todas las distancias como "infinito"
        for (User u : grafo.getUsuarios()) {
            distancias.put(u, Integer.MAX_VALUE);
        }

        // La distancia al origen es 0
        distancias.put(origen, 0);
        cola.add(new NodoDistancia(origen, 0));

        while (!cola.isEmpty()) {
            NodoDistancia actual = cola.poll();
            User uActual = actual.getUser();

            // Si ya encontramos un camino más corto, ignoramos este
            if (actual.getDistancia() > distancias.get(uActual)) continue;

            // Revisamos los adyacentes
            for (Arista arista : grafo.getAdyacentes(uActual)) {
                User vecino = arista.getDestino();
                int nuevaDistancia = distancias.get(uActual) + arista.getPeso();

                // Si el nuevo camino es más corto, actualizamos
                if (nuevaDistancia < distancias.get(vecino)) {
                    distancias.put(vecino, nuevaDistancia);
                    cola.add(new NodoDistancia(vecino, nuevaDistancia));
                }
            }
        }
        return distancias;
    }

    // Clase interna auxiliar para la PriorityQueue
    private static class NodoDistancia {
        private User user;
        private int distancia;

        public NodoDistancia(User user, int distancia) {
            this.user = user;
            this.distancia = distancia;
        }

        public User getUser() { return user; }
        public int getDistancia() { return distancia; }
    }


    public static boolean esConexo(Grafo grafo) {
        Set<User> usuarios = grafo.getUsuarios();


        if (usuarios.isEmpty()) {
            return false;
        }

        // Recorrido BFS desde un usuario cualquiera
        Set<User> visitados = new HashSet<>();
        Queue<User> cola = new LinkedList<>();

        User inicial = usuarios.iterator().next();
        visitados.add(inicial);
        cola.add(inicial);

        while (!cola.isEmpty()) {
            User actual = cola.poll();
            for (Arista arista : grafo.getAdyacentes(actual)) {
                User vecino = arista.getDestino();
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    cola.add(vecino);
                }
            }
        }

        // El grafo es conexo si todos los usuarios fueron alcanzados
        return visitados.size() == usuarios.size();
    }

    
}
